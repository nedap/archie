package com.nedap.archie.creation;

import com.nedap.archie.adlparser.modelconstraints.BMMConstraintImposer;
import com.nedap.archie.aom.ArchetypeSlot;
import com.nedap.archie.aom.CArchetypeRoot;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.OperationalTemplate;
import com.nedap.archie.aom.primitives.CBoolean;
import com.nedap.archie.aom.primitives.CDate;
import com.nedap.archie.aom.primitives.CDateTime;
import com.nedap.archie.aom.primitives.CDuration;
import com.nedap.archie.aom.primitives.CInteger;
import com.nedap.archie.aom.primitives.CReal;
import com.nedap.archie.aom.primitives.CString;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.aom.primitives.CTime;
import com.nedap.archie.aom.profile.AomProfile;
import com.nedap.archie.aom.profile.AomPropertyMapping;
import com.nedap.archie.aom.profile.AomTypeMapping;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import com.nedap.archie.aom.terminology.ValueSet;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.base.Interval;
import com.nedap.archie.base.MultiplicityInterval;
import com.nedap.archie.rminfo.MetaModels;
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmContainerProperty;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmProperty;
import org.openehr.bmm.core.BmmType;
import org.openehr.bmm.persistence.validation.BmmDefinitions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * generates an example structure for any model based on an operational template + a BMM model + the AOP profile
 *
 * Output is a Map<String, Object>, where object is again a Map<String, Object>, or a simple type directly serializable
 * using the jackson object mapper. This can be simply serialized to JSON if desired.
 *
 *
 * This contains a tiny bit of OpenEHR RM specific code, that is to be converted to subclasses for the different RMs
 * BMM + AOP simply does nto contain enough information for this to be truly RM independent
 */
public  class ExampleJsonInstanceGenerator {

    private final String language;
    private final MetaModels models;
    private OperationalTemplate archetype;
    private BmmModel bmm;
    private BMMConstraintImposer constraintImposer;
    private AomProfile aomProfile;

    public ExampleJsonInstanceGenerator(MetaModels models, String language) {
        this.language = language;
        this.models = models;
    }

    public Map<String, Object> generate(OperationalTemplate archetype) {
        this.archetype = archetype;
        models.selectModel(archetype);
        aomProfile = models.getSelectedAomProfile();
        bmm = models.getSelectedBmmModel();
        constraintImposer = new BMMConstraintImposer(bmm);
        return generate(archetype.getDefinition());
    }

    private Map<String, Object> generate(CComplexObject cObject) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("@type", getConcreteTypeName(cObject.getRmTypeName()));

        BmmClass classDefinition = bmm.getClassDefinition(BmmDefinitions.typeNameToClassKey(cObject.getRmTypeName()));

        addAdditionalPropertiesAtBegin(classDefinition, result, cObject);


        for (CAttribute attribute : cObject.getAttributes()) {
            BmmProperty property = AOMUtils.getPropertyAtPath(bmm, cObject.getRmTypeName(), attribute.getRmAttributeName());
            List<Object> children = new ArrayList<>();

            for (CObject child : attribute.getChildren()) {
                MultiplicityInterval multiplicityInterval = child.effectiveOccurrences(models.getSelectedModel()::referenceModelPropMultiplicity);
                int occurrences = Math.min(1, multiplicityInterval.getLower());
                if(multiplicityInterval.has(2) && occurrences <= 1) {
                    occurrences = 1;//TODO: decide whether we should add these structures twice
                }
                for(int i = 0; i <= occurrences; i++){
                    if (child instanceof CComplexObject) {
                        Map<String, Object> next = generate((CComplexObject) child);
                        children.add(next);
                    } else if (child instanceof CPrimitiveObject) {
                        generateCPrimitive(children, (CPrimitiveObject) child);
                    } else if (child instanceof ArchetypeSlot) {
                        Map<String, Object> next = new LinkedHashMap<>();
                        next.put("@type", child.getRmTypeName());
                        //TODO: indicate this is an archetype slot
                        addAdditionalPropertiesAtBegin(classDefinition, next, child);
                        addAdditionalPropertiesAtEnd(classDefinition, next, child);
                        children.add(next);
                    } else {
                        children.add("unsupported constraint: " + child.getClass().getSimpleName());
                    }
                }
            }

            if (property instanceof BmmContainerProperty) {
                result.put(attribute.getRmAttributeName(), children);
            } else if (!children.isEmpty()) {
                result.put(attribute.getRmAttributeName(), children.get(0));
            }
        }

        addRequiredPropertiesFromBmm(result, classDefinition);

        addAdditionalPropertiesAtEnd(classDefinition, result, cObject);
        return result;

    }

    protected String getConcreteTypeName(String rmTypeName) {

        String classKey = BmmDefinitions.typeNameToClassKey(rmTypeName);
        BmmClass classDefinition = bmm.getClassDefinition(classKey);
        if(classDefinition.isAbstract()) {
            List<String> allDescendants = classDefinition.findAllDescendants();
            for(String descendant: allDescendants) {
                BmmClass descendantClassDefinition = bmm.getClassDefinition(descendant);
                if(!descendantClassDefinition.isAbstract()) {
                    return descendantClassDefinition.getTypeName();
                }

            }
        }
        //not abstract or cannot find a non-abstract subclass. Return the original parameters
        return rmTypeName;
    }

    private void addRequiredPropertiesFromBmm(Map<String, Object> result, BmmClass classDefinition) {
        Map<String, BmmProperty> properties = classDefinition.flattenBmmClass().getProperties();
        //add all mandatory properties from the RM
        for (BmmProperty property : properties.values()) {
            if (property.getMandatory() && !result.containsKey(property.getName())) {
                addRequiredProperty(result, property);
            }
        }
    }

    private void addRequiredProperty(Map<String, Object> result, BmmProperty property) {
        BmmType type = property.getType();
        if (property instanceof BmmContainerProperty) {
            List<Map<String, Object>> children = new ArrayList<>();
            result.put(property.getName(), children);
        } else {

            String actualType = type.getTypeName();
            BmmClass classDefinition1 = bmm.getClassDefinition(BmmDefinitions.typeNameToClassKey(actualType));
            if(classDefinition1 != null && classDefinition1.isPrimitiveType()) {
                if (aomProfile.getRmPrimitiveTypeEquivalences().get(type.getTypeName()) != null) {
                    actualType = aomProfile.getRmPrimitiveTypeEquivalences().get(type.getTypeName());
                }
                result.put(property.getName(), generatePrimitiveTypeExample(actualType));
            } else {
                result.put(property.getName(), constructExampleType(actualType));
            }
        }
    }

    private Map<String, Object> constructExampleType(String actualType) {
        Map<String, Object> custom = generateCustomExampleType(actualType);
        if(custom != null) {
            return custom;
        }
        Map<String, Object> result = new LinkedHashMap<>();
        String className = getConcreteTypeName(actualType);
        BmmClass classDefinition = bmm.getClassDefinition(BmmDefinitions.typeNameToClassKey(actualType));
        result.put("@type", className);
        if(classDefinition != null) {
            addRequiredPropertiesFromBmm(result, classDefinition);
        }
        return result;
    }

    private Object generatePrimitiveTypeExample(String typeName) {
        switch(typeName.toLowerCase()) {
            case "string":
                return "string";
            case "real":
                return 42.0d;
            case "integer":
                return 42;
            case "date":
                return "2018-01-01";
            case "date_time":
                return "2018-01-01T12:00:00+00:00";
            case "time":
                return "12:00:00";
            case "duration":
                return "PT10m";
        }
        return "unknown primitive type " + typeName;
    }

    private void generateCPrimitive(List<Object> children, CPrimitiveObject child) {
        //optionally create a custom mapping for the current RM. useful to map to strange objects
        //such as mapping a CTerminologyCode to a DV_CODED_TEXT in OpenEHR ERM
        Object customMapping = generateCustomMapping(child);
        if(customMapping != null) {
            children.add(customMapping);
            return;
        }
        if(child instanceof CString) {
            CString string = (CString) child;
            if (string.getConstraint() != null && !string.getConstraint().isEmpty()) {
                children.add(string.getConstraint().get(0));
            } else {
                children.add("string");
            }
        } else if (child instanceof CBoolean) {
            CBoolean bool = (CBoolean) child;
            if (bool.getConstraint() != null && !bool.getConstraint().isEmpty()) {
                children.add(bool.getConstraint().get(0));
            } else {
                children.add(true);
            }
        } else if (child instanceof CInteger) {
            CInteger integer = (CInteger) child;
            if (integer.getConstraint() != null && !integer.getConstraint().isEmpty()) {
                Interval<Long> longInterval = integer.getConstraint().get(0);
                if(longInterval.isUpperUnbounded() && longInterval.isLowerUnbounded()) {
                    children.add(42);
                } else if(longInterval.isUpperUnbounded()) {
                    children.add(longInterval.getLower() + 1);
                } else if (longInterval.isLowerUnbounded()) {
                    children.add(longInterval.getUpper() - 1);
                } else {
                    if(longInterval.isLowerIncluded()) {
                        children.add(longInterval.getLower());
                    } else {
                        children.add(longInterval.getUpper() + 1);
                    }
                }
            } else {
                children.add(42);
            }
        } else if (child instanceof CReal) {
            CReal real = (CReal) child;
            if (real.getConstraint() != null && !real.getConstraint().isEmpty()) {
                Interval<Double> doubleInterval = real.getConstraint().get(0);
                if(doubleInterval.isUpperUnbounded() && doubleInterval.isLowerUnbounded()) {
                    children.add(42.0d);
                } else if(doubleInterval.isUpperUnbounded()) {
                    children.add(doubleInterval.getLower() + 1.0d);
                } else if (doubleInterval.isLowerUnbounded()) {
                    children.add(doubleInterval.getUpper() - 1.0d);
                } else {
                    if(doubleInterval.isLowerIncluded()) {
                        children.add(doubleInterval.getLower());
                    } else {
                        children.add(doubleInterval.getUpper() + 1.0d);
                    }
                }
            } else {
                children.add(42.0d);
            }
        } else if (child instanceof CTerminologyCode) {
            children.add(generateTerminologyCode( (CTerminologyCode) child));
        } else if (child instanceof CDuration) {
            children.add("PT12m");
        } else if (child instanceof CDate) {
            children.add("2018-01-01");
        } else if (child instanceof CTime) {
          children.add("12:00:00");
        } else if (child instanceof CDateTime) {
            children.add("2018-01-01T12:00:00+00:00");
        } else {
            children.add("TODO: unsupported primitive object constraint " + child.getClass());
        }
    }



    private Object generateTerminologyCode(CTerminologyCode child) {
        //TODO: if child is a subconstaint of a DV_ORDINAL.symbol, manually convert to DV_CODED_TEXT. OpenEHR RM only of course
        if(aomProfile == null) {
            return "cannot convert CTerminologyCode without AOM profile";
        }
        AomTypeMapping termCodeMapping = aomProfile.getAomRmTypeMappings().get("TERMINOLOGY_CODE");
        if(termCodeMapping == null) {
            return "cannot convert a CTerminology code without an AOM profile containing at least a mapping of Terminology code";
        } else {
            Map<String, Object> result = new LinkedHashMap<>();
            String type = termCodeMapping.getTargetClassName();
            result.put("@type", type);
            AomPropertyMapping terminologyIdMapping = termCodeMapping.getPropertyMappings().get("terminology_id");
            AomPropertyMapping codeStringMapping = termCodeMapping.getPropertyMappings().get("code_string");
            String codeString = "term code";
            String terminologyId = "local";
            String termString = "term";
            if(child.getConstraint().isEmpty()) {
                codeString = "term code";
            } else {
                String constraint = child.getConstraint().get(0);
                if(constraint.startsWith("ac")) {
                    ArchetypeTerminology terminology = archetype.getTerminology(child);
                    ValueSet valueSet = terminology.getValueSets().get(constraint);
                    if(valueSet == null) {
                        valueSet = archetype.getTerminology().getValueSets().get(constraint);
                    }
                    if(valueSet == null || valueSet.getMembers().isEmpty()) {
                    } else {
                        codeString = valueSet.getMembers().iterator().next();

                        ArchetypeTerm term = archetype.getTerm(child, codeString, language);
                        if(term != null) {
                            termString = term.getText();
                        }
                    }

                } else if (constraint.startsWith("at")) {
                    codeString = constraint;
                    ArchetypeTerm term = archetype.getTerm(child, constraint, language);
                    if(term != null) {
                        termString = term.getText();
                    }
                } else {
                    codeString = "unknown term code mapping" + constraint;
                }
            }
            if(terminologyIdMapping != null) {
                result.put(terminologyIdMapping.getTargetPropertyName(), terminologyId);
            }
            if(codeStringMapping == null) {
                //erm, right
                return "cannot convert a CTerminology code without an AOM profile containing at least a mapping of Terminology code";
            } else {
                String targetPropertyName = codeStringMapping.getTargetPropertyName();

                result.put(targetPropertyName, codeString);
            }
            return result;
        }


    }


    ///// BEGIN OPENEHR RM SPECIFIC CODE TO BE EXTRACTED /////

    /**
     * Generate a custom JSON mapping if required by the given CPrimitiveObject at the given place in the tree.
     * @param child
     * @return the custom JSON mapping, or null if no custom mapping is required
     */
    private Object generateCustomMapping(CPrimitiveObject child) {
        if(child instanceof CTerminologyCode) {
            CTerminologyCode cTermCode = (CTerminologyCode) child;

            CAttribute parentAttribute = child.getParent();
            CComplexObject parentObject = (CComplexObject) parentAttribute.getParent();
            BmmClass classDefinition = bmm.getClassDefinition(BmmDefinitions.typeNameToClassKey(parentObject.getRmTypeName()));
            if(classDefinition == null) {
                return null;
            }
            BmmProperty property = classDefinition.flattenBmmClass().getProperties().get(parentAttribute.getRmAttributeName());
            if(property == null) {
                return null;
            }
            if(property.getType().getTypeName().equalsIgnoreCase("DV_CODED_TEXT")) {
                Object codePhrase = this.generateTerminologyCode(cTermCode);
                Map<String, Object> dvCodedText = constructExampleType("DV_CODED_TEXT");
                dvCodedText.put("defining_code", codePhrase);
                if(codePhrase instanceof Map) {
                    Map<String, Object> definingCode = (Map<String, Object>) codePhrase;
                    String codeString = (String) definingCode.get("code_string");//TODO: check terminology code to be local?
                    ArchetypeTerm term = archetype.getTerm(child, codeString, language);
                    dvCodedText.put("value", term.getText());
                }

                return dvCodedText;
            } else {
                return null;
            }
        }

        return null;
    }
    /** Add any properties required for this specific RM based on the CObject. For openEHR RM, this should at least
     * set the name if present
     */
    protected void addAdditionalPropertiesAtBegin(BmmClass classDefinition, Map<String, Object> result, CObject cObject) {

        if (classDefinition.getTypeName().equalsIgnoreCase("LOCATABLE") || classDefinition.findAllAncestors().contains("LOCATABLE")) {

            Map<String, Object> name = new LinkedHashMap<>();
            name.put("@type", "DV_TEXT");
            ArchetypeTerm term = archetype.getTerm(cObject, language);
            if (term == null) {
                name.put("value", "missing term in archetype for language " + language);
            } else {
                name.put("value", term.getText());
            }
            result.put("name", name);

            if (!(cObject instanceof CPrimitiveObject)) {
                result.put("archetype_node_id", cObject.getNodeId());
            }
        }

        if(cObject instanceof ArchetypeSlot) {
            Map<String, Object> archetypeDetails = new LinkedHashMap<>();
            archetypeDetails.put("@type", "ARCHETYPED");
            archetypeDetails.put("archetype_id", "openEHR-EHR-" + cObject.getRmTypeName() + ".archetype-slot.v1"); //TODO: add template id
            archetypeDetails.put("rm_version", "1.0.4");
            result.put("archetype_details", archetypeDetails);
        } else if (cObject instanceof CArchetypeRoot) {
            Map<String, Object> archetypeDetails = new LinkedHashMap<>();
            archetypeDetails.put("@type", "ARCHETYPED");
            archetypeDetails.put("archetype_id", ((CArchetypeRoot) cObject).getArchetypeRef()); //TODO: add template id
            archetypeDetails.put("rm_version", "1.0.4");
            result.put("archetype_details", archetypeDetails);
        }
    }
    protected void addAdditionalPropertiesAtEnd(BmmClass classDefinition, Map<String, Object> result, CObject cObject) {
        if(classDefinition.getTypeName().equalsIgnoreCase("DV_CODED_TEXT")) {
            try {
                Map<String, Object> definingCode = (Map<String, Object>) result.get("defining_code");
                String codeString = (String) definingCode.get("code_string");//TODO: check terminology code to be local?
                ArchetypeTerm term = archetype.getTerm(cObject, codeString, language);
                result.put("value", term.getText());
            } catch (Exception e) {
                //if statements would be cleaner, but this should not happen and is a lot less code
                //cannot set this apparently, it will be filled by the BMM required property later
            }
        }
    }

    private Map<String, Object> generateCustomExampleType(String actualType) {
        if(actualType.equalsIgnoreCase("DV_DATE_TIME")) {
            //In BMM, value is a string, and not a date time, so impossible to map automatically
            LinkedHashMap<String, Object> result = new LinkedHashMap<>();
            result.put("@type", "DV_DATE_TIME");
            result.put("value", "2018-01-01T12:00:00+0000");
            return result;
        } else if (actualType.equalsIgnoreCase("DV_DATE")) {
            //In BMM, value is a string, and not a date time, so impossible to map automatically
            LinkedHashMap<String, Object> result = new LinkedHashMap<>();
            result.put("@type", "DV_DATE");
            result.put("value", "2018-01-01");
            return result;
        }  else if (actualType.equalsIgnoreCase("DV_TIME")) {
            //In BMM, value is a string, and not a date time, so impossible to map automatically
            LinkedHashMap<String, Object> result = new LinkedHashMap<>();
            result.put("@type", "DV_TIME");
            result.put("value", "12:00:00");
            return result;
        }  else if (actualType.equalsIgnoreCase("DV_DURATION")) {
            //In BMM, value is a string, and not a date time, so impossible to map automatically
            LinkedHashMap<String, Object> result = new LinkedHashMap<>();
            result.put("@type", "DV_DURATION");
            result.put("value", "PT20m");
            return result;
        }
        return null;
    }

    ///// END OPENEHR RM SPECIFIC CODE TO BE EXTRACTED /////
}
