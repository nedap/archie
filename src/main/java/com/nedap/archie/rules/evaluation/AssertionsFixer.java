package com.nedap.archie.rules.evaluation;

import com.google.common.collect.Lists;
import com.nedap.archie.ArchieLanguageConfiguration;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CAttributeTuple;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveTuple;
import com.nedap.archie.aom.OperationalTemplate;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import com.nedap.archie.base.Interval;
import com.nedap.archie.creation.RMObjectCreator;
import com.nedap.archie.rm.archetyped.Archetyped;
import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.quantity.DvOrdinal;
import com.nedap.archie.rm.support.identification.TerminologyId;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.rminfo.RMAttributeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;
import java.util.Map;

/**
 * Created by pieter.bos on 05/04/2017.
 */
public class AssertionsFixer {

    private static final Logger logger = LoggerFactory.getLogger(AssertionsFixer.class);

    private final RMObjectCreator creator;
    private final RuleEvaluation ruleEvaluation;
    private final EmptyRMObjectConstructor emptyRMObjectConstructor = new EmptyRMObjectConstructor();

    public AssertionsFixer(RuleEvaluation evaluation, RMObjectCreator creator) {
        this.creator = creator;
        this.ruleEvaluation = evaluation;
    }
    
    public void fixAssertions(Archetype archetype, AssertionResult assertionResult) {
        try {
            Map<String, Value> setPathValues = assertionResult.getSetPathValues();
            for(String path:setPathValues.keySet()) {
                Value value = setPathValues.get(path);

                String pathOfParent = stripLastPathSegment(path);
                String lastPathSegment = getLastPathSegment(path);
                List<Object> parents = null;

                parents = ruleEvaluation.getQueryContext().findList(pathOfParent);


                while(parents.isEmpty()) {
                    //there's object missing in the RMObject. Construct it here.
                    constructMissingStructure(archetype, pathOfParent, lastPathSegment, parents);
                    parents = ruleEvaluation.getQueryContext().findList(pathOfParent);
                }

                for(Object parent:parents) {
                    RMAttributeInfo attributeInfo = ArchieRMInfoLookup.getInstance().getAttributeInfo(parent.getClass(), lastPathSegment);
                    if(attributeInfo == null) {
                        throw new IllegalStateException("attribute " + lastPathSegment + " does not exist on type " + parent.getClass());
                    }
                    if(value.getValue() == null) {
                        creator.set(parent, lastPathSegment, Lists.newArrayList(value.getValue()));
                    } else if(attributeInfo.getType().equals(Long.class) && value.getValue().getClass().equals(Double.class)) {
                        Long convertedValue = ((Double) value.getValue()).longValue(); //TODO or should this round?
                        creator.set(parent, lastPathSegment, Lists.newArrayList(convertedValue));
                    } else if(attributeInfo.getType().equals(Double.class) && value.getValue().getClass().equals(Long.class)) {
                        Double convertedValue = ((Long) value.getValue()).doubleValue(); //TODO or should this round?
                        creator.set(parent, lastPathSegment, Lists.newArrayList(convertedValue));
                    } else {
                        creator.set(parent, lastPathSegment, Lists.newArrayList(value.getValue()));
                    }
                    if(parent instanceof CodePhrase) {
                        fixCodePhrase(archetype, pathOfParent, parent);
                    }
                    ruleEvaluation.refreshQueryContext();
                }
            }
        } catch (XPathExpressionException e) {
            logger.error("error fixing assertionResult {}", assertionResult, e);
        }
    }

    private void fixCodePhrase(Archetype archetype, String pathOfParent, Object parent) {
        try {
            //special case: if at-code has been set, we need to do more!
            if (pathOfParent.endsWith("value/defining_code")) {
                fixDvCodedText(archetype, pathOfParent);
            } else if (pathOfParent.endsWith("symbol/defining_code")) {
                fixDvOrdinal(archetype, pathOfParent);
            } else {
            }
        } catch (Exception e) {
            logger.warn("cannot fix codephrase", e);
        }
    }

    private void fixDvOrdinal(Archetype archetype, String pathOfParent) throws XPathExpressionException {
        DvOrdinal ordinal = ruleEvaluation.getQueryContext().find(pathOfParent.replace("/symbol/defining_code", ""));
        CAttribute symbolAttribute = archetype.itemAtPath(pathOfParent.replace("/symbol/defining_code", "/symbol"));//TODO: remove all numeric indices from path!
        if (symbolAttribute != null) {
            CAttributeTuple socParent = (CAttributeTuple) symbolAttribute.getSocParent();
            if (socParent != null) {
                int valueIndex = socParent.getMemberIndex("value");
                int symbolIndex = socParent.getMemberIndex("symbol");
                if (valueIndex != -1 && symbolIndex != -1) {
                    for (CPrimitiveTuple tuple : socParent.getTuples()) {
                        if (tuple.getMembers().get(symbolIndex).getConstraint().get(0).equals(ordinal.getSymbol().getDefiningCode().getCodeString())) {
                            List<Interval> valueConstraint = tuple.getMembers().get(valueIndex).getConstraint();
                            if(valueConstraint.size() == 1) {
                                Interval<Long> interval  = valueConstraint.get(0);
                                if(interval.getLower().equals(interval.getUpper()) && !interval.isLowerUnbounded() && !interval.isUpperUnbounded()) {
                                    ordinal.setValue(interval.getLower());
                                }

                            }
                        }
                    }

                }
            }
        }
    }

    private void fixDvCodedText(Archetype archetype, String pathOfParent) throws XPathExpressionException {
        DvCodedText codedText = ruleEvaluation.getQueryContext().find(pathOfParent.replace("/defining_code", ""));
        Archetyped details = RuleEvaluationUtil.findLastArchetypeDetails(ruleEvaluation, pathOfParent);
        if(details == null) {
            setDefaultTermDefinitionInCodedText(archetype, codedText);
        } else if(archetype instanceof OperationalTemplate) {

            OperationalTemplate template = (OperationalTemplate) archetype;

            String archetypePath = RuleEvaluationUtil.convertRMObjectPathToArchetypePath(pathOfParent);
            setTerminologyFromArchetype(archetype, codedText, archetypePath);

            setValueFromTermDefinition(codedText, details, template);
        } else {
            setDefaultTermDefinitionInCodedText(archetype, codedText);
        }
    }

    private ArchetypeTerm getTermDefinition(OperationalTemplate template, Archetyped details, DvCodedText codedText) {
        ArchetypeTerminology archetypeTerminology = template.getComponentTerminologies().get(details.getArchetypeId().toString());
        if(archetypeTerminology != null) {
            ArchetypeTerm termDefinition = archetypeTerminology.getTermDefinition(ArchieLanguageConfiguration.getMeaningAndDescriptionLanguage(), codedText.getDefiningCode().getCodeString());
            if(termDefinition != null) {
                return termDefinition;
            }
        }
        return template.getTerminology().getTermDefinition(ArchieLanguageConfiguration.getMeaningAndDescriptionLanguage(), codedText.getDefiningCode().getCodeString());
    }

    private void setValueFromTermDefinition(DvCodedText codedText, Archetyped details, OperationalTemplate template) {
        ArchetypeTerm termDefinition = getTermDefinition(template, details, codedText);
        if(termDefinition != null) {
            codedText.setValue(termDefinition.getText());
        }
    }

    private void setDefaultTermDefinitionInCodedText(Archetype archetype, DvCodedText codedText) {
        ArchetypeTerm termDefinition = archetype.getTerminology().getTermDefinition(ArchieLanguageConfiguration.getMeaningAndDescriptionLanguage(), codedText.getDefiningCode().getCodeString());
        if(termDefinition != null) {
            codedText.setValue(termDefinition.getText());
        }
    }

    private void setTerminologyFromArchetype(Archetype archetype, DvCodedText codedText, String s) {
        ArchetypeModelObject archetypeModelObject = archetype.itemAtPath(s);
        if(archetypeModelObject instanceof CAttribute) {
            CAttribute definingCodeConstraint = (CAttribute) archetypeModelObject;
            for(CObject child:definingCodeConstraint.getChildren()) {
                if(child instanceof CTerminologyCode) {
                    if(((CTerminologyCode) child).getConstraint().get(0).startsWith("ac")) {
                        codedText.getDefiningCode().setTerminologyId(new TerminologyId(((CTerminologyCode) child).getConstraint().get(0)));
                    }
                }
            }
        }
    }

    private void constructMissingStructure(Archetype archetype, String pathOfParent, String lastPathSegment, List<Object> parents) throws XPathExpressionException {
        //TODO: this is great but not enough. Fix it by hardcoding support for DV_CODED_TEXT and DV_ORDINAL, here or in the FixableAssertionsChecker.
        String newPathOfParent = pathOfParent;
        String newLastPathSegment = lastPathSegment;
        while (parents.isEmpty()) {
            //lookup parent of parent until found. Create empty RM object. Then repeat original query
            newLastPathSegment = getLastPathSegment(newPathOfParent);
            newPathOfParent = stripLastPathSegment(newPathOfParent);
            parents = ruleEvaluation.getQueryContext().findList(newPathOfParent);
        }
        List<ArchetypeModelObject> constraints;
        if (newPathOfParent.equals("/")) {
            constraints = archetype.itemsAtPath("/" + newLastPathSegment);
        } else {
            constraints = archetype.itemsAtPath(newPathOfParent + "/" + newLastPathSegment);
        }

        if (constraintsHasNoComplexObjects(constraints)) {
            Object object = parents.get(0);

            Object newEmptyObject = null;
            newEmptyObject = constructEmptySimpleObject(newLastPathSegment, object, newEmptyObject);

            creator.addElementToListOrSetSingleValues(object, newLastPathSegment, Lists.newArrayList(newEmptyObject));
            ruleEvaluation.refreshQueryContext();
        } else {
            CObject constraint = getCObjectFromResult(constraints);
            if (constraint != null) {
                Object object = parents.get(0);

                Object newEmptyObject = null;
                if (constraint instanceof CComplexObject) {
                    newEmptyObject = emptyRMObjectConstructor.constructEmptyRMObject(constraint);
                } else {
                    newEmptyObject = constructEmptySimpleObject(newLastPathSegment, object, newEmptyObject);
                }

                int bracketIndex = newLastPathSegment.indexOf('[');
                String attributeName = newLastPathSegment;
                if(bracketIndex > -1) {
                    attributeName = newLastPathSegment.substring(0, bracketIndex);
                }

                creator.addElementToListOrSetSingleValues(object, attributeName, Lists.newArrayList(newEmptyObject));

                ruleEvaluation.refreshQueryContext();
            }
        }
    }

    private CObject getCObjectFromResult(List<? extends ArchetypeModelObject> objects) {
        if(objects.size() != 1) {
            //if there's more than one CObject this represents a user choice and we cannot return a single object and this cannot be automatically fixed
            return null;
        } else {
            ArchetypeModelObject object = objects.get(0);
            if (object instanceof CAttribute) {
                return getCObjectFromResult(((CAttribute) object).getChildren());
            }
            return (CObject) object;
        }
    }

    private Object constructEmptySimpleObject(String newLastPathSegment, Object object, Object newEmptyObject) {
        RMAttributeInfo attributeInfo = ArchieRMInfoLookup.getInstance().getAttributeInfo(object.getClass(), newLastPathSegment);
        try {
            newEmptyObject = attributeInfo.getTypeInCollection().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return newEmptyObject;
    }

    private boolean constraintsHasNoComplexObjects(List<? extends ArchetypeModelObject> constraints) {
        for(ArchetypeModelObject constraint:constraints) {
            if(constraint instanceof CAttribute) {
                if(!constraintsHasNoComplexObjects(((CAttribute) constraint).getChildren())) {
                    return false;
                }
            } else if(constraint instanceof CComplexObject) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return the path with everything except the last path segment, so /items/value becomes /items
     * @param path
     * @return
     */
    private String stripLastPathSegment(String path) {
        int lastIndex = path.lastIndexOf('/');
        if(lastIndex < 0) {
            return path;
        }

        String result = path.substring(0, lastIndex);
        if( result.equals("")) {
            return "/";
        }

        return result;
    }

    /**
     * Return the path with everything except the last path segment, so /items/value becomes /items
     * @param path
     * @return
     */
    private String getLastPathSegment(String path) {
        int lastIndex = path.lastIndexOf('/');
        if(lastIndex < 0) {
            return path;
        }
        return path.substring(lastIndex+1);
    }

}
