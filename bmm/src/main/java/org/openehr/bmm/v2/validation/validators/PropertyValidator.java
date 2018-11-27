package org.openehr.bmm.v2.validation.validators;

import org.apache.commons.lang.StringUtils;
import org.openehr.bmm.persistence.validation.BmmMessageIds;
import org.openehr.bmm.v2.persistence.*;
import org.openehr.utils.message.MessageLogger;

import java.util.List;
import java.util.Map;

public class PropertyValidator extends ValidatorBase {

    private final PBmmSchema schema;
    private MessageLogger logger;

    private ConformanceChecker conformanceChecker = new ConformanceChecker();

    public PropertyValidator(MessageLogger logger, PBmmSchema schema) {
        super(logger);
        this.logger = logger;
        this.schema = schema;
    }

    public void validateProperty(PBmmClass pBmmClass, PBmmProperty pBmmProperty) {
        //first check if any property replicates a property from a parent class
        validateOverriddenPropertyType(pBmmClass, pBmmProperty);

        //For single properties, check if property type is empty or not defined in the schema
        if(pBmmProperty instanceof PBmmSingleProperty) {
            validateSingleProperty(pBmmClass, pBmmProperty);
        } else if(pBmmProperty instanceof PBmmSinglePropertyOpen) {
            validateSimpleOpenProperty(pBmmClass, pBmmProperty);
        } else if(pBmmProperty instanceof PBmmContainerProperty) {
            validateContainerProperty(pBmmClass, pBmmProperty);
        } else if (pBmmProperty instanceof PBmmGenericProperty){
            validateGenericProperty(pBmmClass, pBmmProperty);
        }
    }


    private void validateGenericProperty(PBmmClass pBmmClass, PBmmProperty pBmmProperty) {
        PBmmGenericProperty genericPropertyDefinition = (PBmmGenericProperty)pBmmProperty;
        PBmmGenericType attributeTypeDefinition = genericPropertyDefinition.getTypeDef();
        if(attributeTypeDefinition != null) {
            if(!schema.hasClassOrPrimitiveDefinition(attributeTypeDefinition.getRootType())) {
                addValidityError(schema, pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_GPRT, pBmmClass.getSourceSchemaId(),
                        pBmmClass.getName(),
                        pBmmProperty.getName(),
                        attributeTypeDefinition.getRootType());
            }

            for(PBmmType genericParameter:attributeTypeDefinition.getGenericParameterDefs().values()) {
                validateGenericTypeDefParameter(pBmmClass, pBmmProperty, attributeTypeDefinition, genericParameter);
            }
        } else {
            addValidityError(schema, pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_GPT,
                    pBmmClass.getSourceSchemaId(),
                    pBmmClass.getName(),
                    pBmmProperty.getName());
        }
    }

    private void validateGenericTypeDefParameter(PBmmClass pBmmClass, PBmmProperty pBmmProperty, PBmmGenericType attributeTypeDefinition, PBmmType genericParameter) {
        List<String> typeReferences = genericParameter.flattenedTypeList();
        for(String typeReference:typeReferences) {
            if(!schema.hasClassOrPrimitiveDefinition(typeReference)) {
                if (pBmmClass.isGeneric()) {  //it might be a formal parameter, to be matched against those of enclosing class
                    Map<String, PBmmGenericParameter> genericParameters = pBmmClass.getGenericParameterDefs();
                    if (!genericParameters.containsKey(typeReference)) {
                        addValidityError(schema, pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_GPGPU,
                                pBmmClass.getSourceSchemaId(),
                                pBmmClass.getName(),
                                pBmmProperty.getName(),
                                attributeTypeDefinition.getRootType());
                    } else {
                        //Should this be logged?
                    }
                } else {
                    //cannot have a generic type declaration for a non-generic class
                    addValidityError(schema, pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_GPGPT,
                            pBmmClass.getSourceSchemaId(),
                            pBmmClass.getName(),
                            pBmmProperty.getName(),
                            typeReference);
                }
            }
        }
    }

    private void validateContainerProperty(PBmmClass pBmmClass, PBmmProperty pBmmProperty) {
        PBmmContainerProperty containerPropertyDefinition = (PBmmContainerProperty) pBmmProperty;
        PBmmContainerType attributeTypeDefinition = containerPropertyDefinition.getTypeRef();
        PBmmType attributeTypeReference = attributeTypeDefinition.getTypeRef();
        if(!schema.hasClassOrPrimitiveDefinition(attributeTypeDefinition.getContainerType())) {
            addValidityError(schema, pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_CPCT,
                    pBmmClass.getSourceSchemaId(),
                    pBmmClass.getName(),
                    pBmmProperty.getName(),
                    attributeTypeDefinition.getType());
        } else if(attributeTypeReference != null){
            //Loop through types inside container type
            List<String> typeReferences = attributeTypeReference.flattenedTypeList();
            if(typeReferences != null) {
                for(String typeReference:typeReferences) {
                    validateContainerTypeReference(pBmmClass, pBmmProperty, attributeTypeDefinition, typeReference);
                }
            } else {
                //Should this be logged?
            }
        } else {
            addValidityError(schema, pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_CPT,
                    pBmmClass.getSourceSchemaId(),
                    pBmmClass.getName(),
                    pBmmProperty.getName());
        }
        if(containerPropertyDefinition.getCardinality() == null) {
//                    addValidityInfo(pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_CPTNC,
//                            pBmmClass.getSourceSchemaId(),
//                            pBmmClass.getName(),
//                            pBmmProperty.getName());
        }
    }

    private void validateContainerTypeReference(PBmmClass pBmmClass, PBmmProperty pBmmProperty, PBmmContainerType attributeTypeDefinition, String typeReference) {
        if (!schema.hasClassOrPrimitiveDefinition(typeReference)) {
            if (pBmmClass.isGeneric()) {  //it might be a formal parameter, to be matched against those of enclosing class
                Map<String, PBmmGenericParameter> genericParameters = pBmmClass.getGenericParameterDefs();
                if (!genericParameters.containsKey(typeReference)) {
                    addValidityError(schema, pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_GPGPU,
                            pBmmClass.getSourceSchemaId(),
                            pBmmClass.getName(),
                            pBmmProperty.getName(),
                            attributeTypeDefinition.getType());
                } else {
                    //Should this be logged?
                }
            } else {
                addValidityError(schema, pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_CPTV,
                        pBmmClass.getSourceSchemaId(),
                        pBmmClass.getName(),
                        pBmmProperty.getName(),
                        attributeTypeDefinition.getType());
            }
        }
    }

    private void validateSimpleOpenProperty(PBmmClass pBmmClass, PBmmProperty pBmmProperty) {
        //For open properties, check if the containing class is a generic class and has a parameter of that type
        PBmmSinglePropertyOpen singlePropertyOpenDefinition = (PBmmSinglePropertyOpen) pBmmProperty;
        PBmmOpenType attributeTypeDefinition = singlePropertyOpenDefinition.getTypeRef();
        if(!pBmmClass.isGeneric() || !pBmmClass.getGenericParameterDefs().containsKey(attributeTypeDefinition.getType())) {
            addValidityError(schema, pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_SPOT, pBmmClass.getSourceSchemaId(), pBmmClass.getName(), pBmmProperty.getName(), attributeTypeDefinition.getType());
        } else {
            //Should this be logged?
        }
    }

    private void validateSingleProperty(PBmmClass pBmmClass, PBmmProperty pBmmProperty) {
        PBmmSingleProperty singlePropertyDefinition = (PBmmSingleProperty)pBmmProperty;
        PBmmSimpleType attributeTypeDefinition = singlePropertyDefinition.getTypeRef();
        if(StringUtils.isEmpty(attributeTypeDefinition.getType()) || !schema.hasClassOrPrimitiveDefinition(attributeTypeDefinition.getType())) {
            addValidityError(schema, pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_SPT,
                    pBmmClass.getSourceSchemaId(),
                    pBmmClass.getName(),
                    pBmmProperty.getName(),
                    attributeTypeDefinition.getType());
        } else {
            //Should this be logged?
        }
    }

    private void validateOverriddenPropertyType(PBmmClass pBmmClass, PBmmProperty pBmmProperty) {
        for(String ancestorName:pBmmClass.getAncestors()) {
            PBmmClass ancestor = schema.findClassOrPrimitiveDefinition(ancestorName);
            if(ancestor != null) {
                PBmmProperty ancestorProperty = ancestor.getProperties().get(pBmmProperty.getName());
                if (ancestor != null && ancestorProperty != null && !conformanceChecker.propertyConformsTo(schema, pBmmProperty, ancestorProperty)) {
                    addValidityError(schema, pBmmClass.getSourceSchemaId(), BmmMessageIds.ec_BMM_PRNCF, pBmmClass.getSourceSchemaId(), pBmmClass.getName(), pBmmProperty.getName(), ancestorName);
                }
            }
        }
    }


}
