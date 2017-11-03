package org.openehr.bmm.persistence.validation;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.openehr.bmm.persistence.*;
import org.openehr.utils.message.MessageCode;
import org.openehr.utils.message.MessageLogger;
import org.openehr.utils.message.UnknownMessageCode;
import org.openehr.utils.validation.AnyValidator;

import java.util.*;

public class BmmSchemaValidator extends AnyValidator {

    private PersistedBmmSchema schema;
    private Map<String, List<String>> ancestorsIndex = new LinkedHashMap<>();
    private Map<String, MessageLogger> schemaErrorTableCache = new LinkedHashMap<>();

    public BmmSchemaValidator() { }

    public BmmSchemaValidator(PersistedBmmSchema schema) {
        this.schema = schema;
    }

    public PersistedBmmSchema getSchema() {
        return schema;
    }

    public void setSchema(PersistedBmmSchema schema) {
        this.schema = schema;
    }

    /**
     * Returns set of error accumulators for other schemas, keyed by schema id
     *
     * @return
     */
    public Map<String, MessageLogger> getSchemaErrorTableCache() {
        if(schemaErrorTableCache == null) {
            schemaErrorTableCache = new LinkedHashMap<>();
        }
        return schemaErrorTableCache;
    }

    /**
     * Sets set of error accumulators for other schemas, keyed by schema id
     *
     * @param schemaErrorTableCache
     */
    public void setSchemaErrorTableCache(Map<String, MessageLogger> schemaErrorTableCache) {
        this.schemaErrorTableCache = schemaErrorTableCache;
    }

    /**
     * main validation prior to generation of BMM_SCHEMA; access to `all_schemas' allows errors to be allocated to
     * correct schema
     */
    @Override
    public void doValidation() {
        Map<String, String> packageClasses = new LinkedHashMap<>();
        List<String> classNames = new ArrayList<>();
        //compute ancestors index:
        //pass 1: add class direct ancestors
        schema.doAllClasses(persistedBmmClass -> {
            List<String> ancestorList = new ArrayList<>();
            ancestorList.addAll(persistedBmmClass.getAncestors());
            ancestorsIndex.put(persistedBmmClass.getName().toUpperCase(), ancestorList);
        });
        //pass 2: add indirect ancestors
        schema.doAllClasses(persistedBmmClass -> {
            List<String> ancestorListCopy = new ArrayList<>();
            List<String> ancestorList = ancestorsIndex.get(persistedBmmClass.getName().toUpperCase());
            ancestorListCopy.addAll(ancestorList);
            ancestorListCopy.forEach(ancestor -> {
                if(ancestorsIndex.containsKey(ancestor) && ancestorsIndex.get(ancestor) != null) {
                    ancestorList.addAll(ancestorsIndex.get(ancestor));
                }
            });
        });
        //Check that RM shema release is valid
        if(!BmmDefinitions.isValidStandardVersion(schema.getRmRelease())) {
            addError(BmmMessageIds.ec_BMM_RMREL, schema.getSchemaId(), schema.getRmRelease());
        }
        //check archetype parent class in list of class names
        if(schema.getArchetypeParentClass() != null && schema.getClassDefinition(schema.getArchetypeParentClass()) ==  null) {
            addError(BmmMessageIds.ec_BMM_ARPAR, schema.getSchemaId(), schema.getArchetypeParentClass());
        }

        //check that all models refer to declared packages
        schema.getArchetypeRmClosurePackages().forEach(closurePackage -> {
            if(!schema.hasCanonicalPackagePath(closurePackage)) {
                addError(BmmMessageIds.ec_BMM_MDLPK, schema.getSchemaId(), closurePackage);
            }
        });


        Map<String, String> packageClassList = new HashMap<>();

        //1. check that no duplicate class names are found in packages
        schema.getCanonicalPackages().forEach((packageName, canonicalPackage) -> {
            canonicalPackage.doRecursiveClasses((persistedBmmPackage, className) -> {
                String classNameStr = className.toLowerCase();
                if(packageClassList.containsKey(classNameStr)) {
                    addError(BmmMessageIds.ec_BMM_CLPKDP, schema.getSchemaId(), className, persistedBmmPackage.getName(), classNameStr);
                } else {
                    packageClassList.put(classNameStr, persistedBmmPackage.getName());
                }
            });
        });

        List<String> classNameList = new ArrayList<>();
        //2. check that every class is in a package
        schema.doAllClasses( persistedBmmClass -> {
            String className = persistedBmmClass.getName().toLowerCase();
            if(!packageClassList.containsKey(className)) {
                addError(BmmMessageIds.ec_BMM_PKGID, schema.getSchemaId(), persistedBmmClass.getName());

            } else if(classNameList.contains(className)) {
                addError(BmmMessageIds.ec_BMM_CLDUP, schema.getSchemaId(), persistedBmmClass.getName());
            } else {
                classNameList.add(className);
            }
        });

        schema.doAllClasses( persistedBmmClass -> {
            validateClass(persistedBmmClass);
        });
    }

    public void validateClass(PersistedBmmClass persistedBmmClass) {
        //check that all ancestors exist
        persistedBmmClass.getAncestors().forEach(ancestorClassName -> {
            if(StringUtils.isEmpty(ancestorClassName)) {
                addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_ANCE, persistedBmmClass.getSourceSchemaId(), persistedBmmClass.getName());
            }
        });
        //check that all generic parameter.conforms_to_type exist exists
        if(!hasErrors()) {
            if(persistedBmmClass.isGeneric()) {
                Map<String, PersistedBmmGenericParameter> genericParameterDefinitions = persistedBmmClass.getGenericParameterDefinitions();
                genericParameterDefinitions.forEach((name, persistedBmmGenericParameter) -> {
                    String conformsToType = persistedBmmGenericParameter.getConformsToType();
                    if(!schema.hasClassOrPrimitiveDefinition(conformsToType)) {
                        addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_GPCT,
                                persistedBmmClass.getSourceSchemaId(),
                                persistedBmmClass.getName(),
                                persistedBmmGenericParameter.getName(),
                                conformsToType);

                    }
                });
            }
        }
        // validate the properties
        persistedBmmClass.getProperties().forEach((name, persistedBmmProperty) -> {
            validateProperty(persistedBmmClass, persistedBmmProperty);
        });
    }

    public void validateProperty(PersistedBmmClass persistedBmmClass, PersistedBmmProperty persistedBmmProperty) {
        //first check if any property replicates a property from a parent class
        persistedBmmClass.getAncestors().forEach(ancestorName -> {
            PersistedBmmClass ancestor = schema.findClassOrPrimitiveDefinition(ancestorName);
            PersistedBmmProperty ancestorProperty = ancestor.getPropertyByName(persistedBmmProperty.getName());
            if(ancestor != null && ancestorProperty != null && !propertyConformsTo(persistedBmmProperty, ancestorProperty)){
                addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_PRNCF, persistedBmmClass.getSourceSchemaId(), persistedBmmClass.getName(), persistedBmmProperty.getName(), ancestorName);
            }
        });

        //For single properties, check if property type is empty or not defined in the schema
        if(persistedBmmProperty instanceof PersistedBmmSingleProperty) {
            PersistedBmmSingleProperty singlePropertyDefinition = (PersistedBmmSingleProperty)persistedBmmProperty;
            PersistedBmmSimpleType attributeTypeDefinition = singlePropertyDefinition.getTypeDefinition();
            if(StringUtils.isEmpty(attributeTypeDefinition.getType()) || !schema.hasClassOrPrimitiveDefinition(attributeTypeDefinition.getType())) {
                addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_SPT,
                        persistedBmmClass.getSourceSchemaId(),
                        persistedBmmClass.getName(),
                        persistedBmmProperty.getName(),
                        attributeTypeDefinition.getType());
            } else {
                //Should this be logged?
            }
        } else

        //For open properties, check if the containing class is a generic class and has a parameter of that type
        if(persistedBmmProperty instanceof PersistedBmmSinglePropertyOpen) {
            PersistedBmmSinglePropertyOpen singlePropertyOpenDefinition = (PersistedBmmSinglePropertyOpen)persistedBmmProperty;
            PersistedBmmOpenType attributeTypeDefinition = singlePropertyOpenDefinition.getTypeDefinition();
            if(!persistedBmmClass.isGeneric() || !persistedBmmClass.getGenericParameterDefinitions().containsKey(attributeTypeDefinition.getType())) {
                addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_SPOT, persistedBmmClass.getSourceSchemaId(), persistedBmmClass.getName(), persistedBmmProperty.getName(), attributeTypeDefinition.getType());
            } else {
                //Should this be logged?
            }
        } else

        if(persistedBmmProperty instanceof PersistedBmmContainerProperty) {
            PersistedBmmContainerProperty containerPropertyDefinition = (PersistedBmmContainerProperty)persistedBmmProperty;
            PersistedBmmContainerType attributeTypeDefinition = containerPropertyDefinition.getTypeDefinition();
            PersistedBmmType attributeTypeReference = attributeTypeDefinition.getTypeReference();
            if(!schema.hasClassOrPrimitiveDefinition(attributeTypeDefinition.getContainerType())) {
                addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_CPCT,
                        persistedBmmClass.getSourceSchemaId(),
                        persistedBmmClass.getName(),
                        persistedBmmProperty.getName(),
                        attributeTypeDefinition.getType());
            } else if(attributeTypeReference != null){
                //Loop through types inside container type
                List<String> typeReferences = attributeTypeReference.flattenedTypeList();
                if(typeReferences != null) {
                    typeReferences.forEach(typeReference -> {
                        if (!schema.hasClassOrPrimitiveDefinition(typeReference)) {
                            if (persistedBmmClass.isGeneric()) {  //it might be a formal parameter, to be matched against those of enclosing class
                                Map<String, PersistedBmmGenericParameter> genericParameters = persistedBmmClass.getGenericParameterDefinitions();
                                if (!genericParameters.containsKey(typeReference)) {
                                    addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_GPGPU,
                                        persistedBmmClass.getSourceSchemaId(),
                                        persistedBmmClass.getName(),
                                        persistedBmmProperty.getName(),
                                        attributeTypeDefinition.getType());
                                } else {
                                    //Should this be logged?
                                }
                            } else {
                                addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_CPTV,
                                        persistedBmmClass.getSourceSchemaId(),
                                        persistedBmmClass.getName(),
                                        persistedBmmProperty.getName(),
                                        attributeTypeDefinition.getType());
                            }
                        }
                    });
                } else {
                    //Should this be logged?
                }
            } else {
                addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_CPT,
                        persistedBmmClass.getSourceSchemaId(),
                        persistedBmmClass.getName(),
                        persistedBmmProperty.getName());
            }
            if(containerPropertyDefinition.getCardinality() == null) {
                addValidityInfo(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_CPTNC,
                        persistedBmmClass.getSourceSchemaId(),
                        persistedBmmClass.getName(),
                        persistedBmmProperty.getName());
            }
        } else if (persistedBmmProperty instanceof PersistedBmmGenericProperty){
            PersistedBmmGenericProperty genericPropertyDefinition = (PersistedBmmGenericProperty)persistedBmmProperty;
            PersistedBmmGenericType attributeTypeDefinition = genericPropertyDefinition.getTypeDefinition();
            if(attributeTypeDefinition != null) {
                if(!schema.hasClassOrPrimitiveDefinition(attributeTypeDefinition.getRootType())) {
                    addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_GPRT, persistedBmmClass.getSourceSchemaId(),
                            persistedBmmClass.getName(),
                            persistedBmmProperty.getName(),
                            attributeTypeDefinition.getRootType());
                }

                for(PersistedBmmType genericParameter:attributeTypeDefinition.getGenericParameterReferences()) {
                    List<String> typeReferences = genericParameter.flattenedTypeList();
                    for(String typeReference:typeReferences) {
                        if(!schema.hasClassOrPrimitiveDefinition(typeReference)) {
                            if (persistedBmmClass.isGeneric()) {  //it might be a formal parameter, to be matched against those of enclosing class
                                Map<String, PersistedBmmGenericParameter> genericParameters = persistedBmmClass.getGenericParameterDefinitions();
                                if (!genericParameters.containsKey(typeReference)) {
                                    addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_GPGPU,
                                            persistedBmmClass.getSourceSchemaId(),
                                            persistedBmmClass.getName(),
                                            persistedBmmProperty.getName(),
                                            attributeTypeDefinition.getRootType());
                                } else {
                                    //Should this be logged?
                                }
                            } else {
                                addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_GPGPT,
                                        persistedBmmClass.getSourceSchemaId(),
                                        persistedBmmClass.getName(),
                                        persistedBmmProperty.getName(),
                                        typeReference);
                            }
                        }
                    }
                }
            } else {
                addValidityError(persistedBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_GPT,
                        persistedBmmClass.getSourceSchemaId(),
                        persistedBmmClass.getName(),
                        persistedBmmProperty.getName());
            }
        }
    }

    /**
     * append an error with key `a_key' and `args' array to the `errors' string to the
     * error list for schema with `a_schema_id'
     *
     * @param sourceSchemaId
     * @param aKey
     * @param arguments
     */
    protected void addValidityError(String sourceSchemaId, MessageCode aKey, String... arguments) {
        if(sourceSchemaId.equals(schema.getSchemaId())) {
            addError(aKey, arguments);
        } else {
            if(!schemaErrorTableCache.containsKey(sourceSchemaId)) {
                schemaErrorTableCache.put(sourceSchemaId, new MessageLogger());
            }
            schemaErrorTableCache.get(sourceSchemaId).addErrorWithLocation(aKey, "", Lists.newArrayList(arguments));
            addError(BmmMessageIds.ec_BMM_INCERR, schema.getSchemaId(), sourceSchemaId);
        }
    }

    /**
     * append a warning with key `a_key' and `args' array to the `errors' string to the
     * error list for schema with `a_schema_id'
     * @param sourceSchemaId
     * @param aKey
     * @param arguments
     */
    protected void addValidityWarning(String sourceSchemaId, MessageCode aKey, String... arguments) {
        if(sourceSchemaId.equals(schema.getSchemaId())) {
            addWarning(aKey, arguments);
        } else {
            if(!schemaErrorTableCache.containsKey(sourceSchemaId)) {
                schemaErrorTableCache.put(sourceSchemaId, new MessageLogger());
            }
            schemaErrorTableCache.get(sourceSchemaId).addWarningWithLocation(aKey, "", Lists.newArrayList(arguments));
        }
    }

    /**
     * append an info message with key `a_key' and `args' array to the `errors' string to the
     * error list for schema with `a_schema_id'
     * @param sourceSchemaId
     * @param aKey
     * @param arguments
     */
    protected void addValidityInfo(String sourceSchemaId, MessageCode aKey, String... arguments) {
        if(sourceSchemaId.equals(schema.getSchemaId())) {
            addInfo(aKey, arguments);
        } else {
            if(!schemaErrorTableCache.containsKey(sourceSchemaId)) {
                schemaErrorTableCache.put(sourceSchemaId, new MessageLogger());
            }
            schemaErrorTableCache.get(sourceSchemaId).addInfoWithLocation(aKey, "", Lists.newArrayList(arguments));
        }
    }

    /**
     * True if `a_child_prop' conforms to `a_parent_prop' such that it could be used to override it; same types are not considered conforming
     *
     * @param aChildProperty
     * @param aParentProperty
     * @return
     */
    public boolean propertyConformsTo(PersistedBmmProperty aChildProperty, PersistedBmmProperty aParentProperty) {
        boolean retVal = false;
        if(aParentProperty instanceof PersistedBmmSingleProperty) {
            PersistedBmmSingleProperty bmmSingleParentProperty = (PersistedBmmSingleProperty) aParentProperty;
            //True if `a_parent_prop' type is Any
            if (bmmSingleParentProperty.getTypeDefinition().getType().equalsIgnoreCase(BmmDefinitions.ANY_TYPE)) {
                retVal = true;
            }
        } else if(aChildProperty.getName().equalsIgnoreCase(aParentProperty.getName())) {
            //Properties names are the same
            if(aChildProperty instanceof PersistedBmmSingleProperty && aParentProperty instanceof PersistedBmmSingleProperty) {
                PersistedBmmSingleProperty aChildSingleProperty = (PersistedBmmSingleProperty)aChildProperty;
                PersistedBmmSingleProperty aParentSingleProperty = (PersistedBmmSingleProperty)aParentProperty;
                retVal = typeStrictlyConformsTo(aChildSingleProperty.getTypeDefinition().getType(), aParentSingleProperty.getTypeDefinition().getType());
            } else if(aParentProperty instanceof PersistedBmmSinglePropertyOpen) {
                if(aChildProperty instanceof  PersistedBmmSinglePropertyOpen) {
                    //If both properties have the same name and are both open properties, then they do not conform.
                    retVal = false;
                } else if(aChildProperty instanceof PersistedBmmSingleProperty) {
                    retVal = true;
                    //TODO FIXME: proper type conformance to constraining generic type needs to be checked here
                }
            } else if(aChildProperty instanceof PersistedBmmContainerProperty && aParentProperty instanceof PersistedBmmContainerProperty) {
                PersistedBmmContainerProperty aChildContainerProperty = (PersistedBmmContainerProperty)aChildProperty;
                PersistedBmmContainerProperty aParentContainerProperty = (PersistedBmmContainerProperty)aParentProperty;
                retVal = typeStrictlyConformsTo(aChildContainerProperty.getTypeDefinition().asTypeString(), aParentContainerProperty.getTypeDefinition().asTypeString());
            } else if(aChildProperty instanceof PersistedBmmGenericProperty && aParentProperty instanceof PersistedBmmGenericProperty) {
                PersistedBmmGenericProperty aChildGenericProperty = (PersistedBmmGenericProperty)aChildProperty;
                PersistedBmmGenericProperty aParentGenericProperty = (PersistedBmmGenericProperty)aParentProperty;
                retVal = typeStrictlyConformsTo(aChildGenericProperty.getTypeDefinition().asTypeString(), aParentGenericProperty.getTypeDefinition().asTypeString());
            }
        }
        return retVal;
    }

    /**
     * check if type 1 and type 2 are identical; each type may be generic
     *
     * @param type1
     * @param type2
     * @return
     */
    public boolean typeSameAs(String type1, String type2) {
        return BmmDefinitions.typeNameAsFlatList(type1).toString().equalsIgnoreCase(BmmDefinitions.typeNameAsFlatList(type2).toString());
    }

    /**
     * check conformance of type 1 to type 2 for substitutability; each type may be generic
     *
     * @param type1
     * @param type2
     * @return
     */
    public boolean typeConformsTo(String type1, String type2) {
        List<String> typeList1 = null, typeList2 = null;
        typeList1 = BmmDefinitions.typeNameAsFlatList(type1);
        typeList2 = BmmDefinitions.typeNameAsFlatList(type2);
        boolean retVal = true;
        int index = 0;
        while(index < typeList1.size() && index < typeList2.size() && retVal && schema.hasClassOrPrimitiveDefinition(typeList1.get(index)) && schema.hasClassOrPrimitiveDefinition(typeList2.get(index))) {
            retVal = retVal
                        && typeList1.get(index).equalsIgnoreCase(typeList2.get(index))
                        || (ancestorsIndex.containsKey(typeList1.get(index).toUpperCase())
                                && ancestorsIndex.get(typeList1.get(index).toUpperCase()).contains(typeList2.get(index)));
            index++;

        }
        return retVal;
    }

    /**
     * check conformance of type 1 to type 2 for redefinition; each type may be generic
     *
     * @param type1
     * @param type2
     * @return
     */
    public boolean typeStrictlyConformsTo(String type1, String type2) {
        return !typeSameAs(type1,type2) && typeConformsTo(type1, type2);
    }

    /**
     * do some basic validation post initial creation
     * 1. check that package structure is regular:
     *     a) only top-level packages can have qualified names
     *     b) no top-level package name can be a direct parent or child of another
     *     (child package must be declared under the parent)
     * 2. check that all classes are mentioned in the package structure
     * 3. check that all models refer to valid packages
     * TODO Need to test this method
     */
    public void validateCreated() {
        if(schema.getState() == PersistedBmmSchemaState.STATE_CREATED) {
            List<String> packageNames = new ArrayList<>();

            //check top-level names - package names cannot contain each other and be siblings
            packageNames.addAll(schema.getPackages().keySet());
            schema.getPackages().keySet().forEach(name1 -> {
                boolean invalidSiblings = packageNames.stream().filter(name2 ->
                    (!name1.equalsIgnoreCase(name2)) && (name1.startsWith(name2) || name2.startsWith(name1))
                ).count() > 0;
                if(invalidSiblings) {
                    addError(BmmMessageIds.ec_BMM_PKGTL, schema.getSchemaId());
                }
            });

            //check no duplicate properties in any class
            schema.doAllClasses(bmmClassSource -> {
                List<String> propertyList = new ArrayList<>();
                bmmClassSource.getProperties().keySet().forEach(property -> {
                    if(propertyList.contains(property)) { //Maps eliminate duplicates so this should never occur unless Eiffel maps have different behaviors(??)
                        addError(BmmMessageIds.ec_BMM_PRDUP,
                            schema.getSchemaId(),
                            bmmClassSource.getName(),
                            property);
                    } else {
                        propertyList.add(property);
                    }
                });
            });

            //validate package & class structure
            schema.doRecursivePackages(persistedBmmPackage -> {
                //check for lower-down qualified names
                if((!schema.getPackages().containsKey(persistedBmmPackage.getName().toUpperCase())) && persistedBmmPackage.getName().indexOf(BmmDefinitions.PACKAGE_NAME_DELIMITER) >=0) {
                    addError(BmmMessageIds.ec_BMM_PKGQN,
                        schema.getSchemaId(),
                        persistedBmmPackage.getName());
                }
                persistedBmmPackage.getClasses().forEach(persistedBmmClass -> {
                    if(StringUtils.isEmpty(persistedBmmClass)) {
                        addError(new UnknownMessageCode(),
                            schema.getSchemaId(),
                            persistedBmmPackage.getName());
                    } else if(!schema.hasClassOrPrimitiveDefinition(persistedBmmClass)) {
                        addError(new UnknownMessageCode(),
                            schema.getSchemaId(),
                            persistedBmmClass,
                            persistedBmmPackage.getName());
                    }
                });
            });

            if(passed) {
                addInfo(new UnknownMessageCode(),schema.getSchemaId(),
                    ""+schema.getPrimitives().size(),
                    ""+schema.getClassDefinitions().size());
                schema.setState(PersistedBmmSchemaState.STATE_VALIDATED_CREATED);
            }
        } else {
            throw new RuntimeException("Schema state is not created");
        }
    }
}
