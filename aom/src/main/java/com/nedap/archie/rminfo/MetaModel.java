package com.nedap.archie.rminfo;

import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmContainerProperty;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmProperty;
import org.openehr.bmm.persistence.validation.BmmDefinitions;

import java.util.List;
import java.util.stream.Collectors;

public class MetaModel {

    private ModelInfoLookup selectedModel;
    private BmmModel selectedBmmModel;

    public MetaModel(ModelInfoLookup selectedModel, BmmModel selectedBmmModel) {
        this.selectedModel = selectedModel;
        this.selectedBmmModel = selectedBmmModel;
    }

    public ModelInfoLookup getSelectedModel() {
        return selectedModel;
    }

    public BmmModel getSelectedBmmModel() {
        return selectedBmmModel;
    }

    public boolean isMultiple(String typeName, String attributeName) {
        if(getSelectedBmmModel() != null) {
            BmmClass classDefinition = getSelectedBmmModel().getClassDefinition(typeName);
            if (classDefinition != null) {
                //TODO: don't flatten on request, create a flattened properties cache just like the eiffel code for much better performance
                BmmClass flatClassDefinition = classDefinition.flattenBmmClass();
                BmmProperty bmmProperty = flatClassDefinition.getProperties().get(attributeName);
                if(bmmProperty == null) {
                    return false;
                } else if(bmmProperty instanceof BmmContainerProperty) {
                    return bmmProperty != null && ((BmmContainerProperty) bmmProperty).getCardinality().has(2);
                } else {
                    return false;
                }
            }
        } else {
            RMAttributeInfo attributeInfo = selectedModel.getAttributeInfo(typeName, attributeName);
            return attributeInfo != null && attributeInfo.isMultipleValued();
        }
        return false;
    }

    public boolean rmTypesConformant(String childTypeName, String parentTypeName) {
        if(getSelectedBmmModel() != null) {
            BmmModel selectedBmmModel = getSelectedBmmModel();
            String parentClassName = BmmDefinitions.typeNameToClassKey(parentTypeName);//generics stripped
            String childClassName = BmmDefinitions.typeNameToClassKey(childTypeName);//generics stripped
            //TODO: generics as well. get the array and check each type?
            BmmClass childClass = selectedBmmModel.getClassDefinition(childClassName);
            if(childClass == null) {
                return true;//will be checked elsewhere
            }
            List<String> allAncestors = childClass.findAllAncestors();
            allAncestors = allAncestors.stream().map((s) -> s.toUpperCase()).collect(Collectors.toList());
            if(!parentClassName.equalsIgnoreCase(childClassName) && !allAncestors.contains(parentClassName)) {
                return false;
            }
            return true;
        } else {
            return selectedModel.rmTypesConformant(childTypeName, parentTypeName);
        }
    }

    public boolean typeNameExists(String typeName) {
        if(getSelectedBmmModel() != null) {
            return selectedBmmModel.getClassDefinition(BmmDefinitions.typeNameToClassKey(typeName)) != null;
        } else {
            return selectedModel.getTypeInfo(typeName) != null;
        }
    }

    public boolean attributeExists(String rmTypeName, String propertyName) {
        if(selectedBmmModel != null) {
            String className = BmmDefinitions.typeNameToClassKey(rmTypeName);
            BmmClass classDefinition = selectedBmmModel.getClassDefinition(className);
            if(classDefinition == null) {
                return false;
            }
            return classDefinition.flattenBmmClass().hasPropertyWithName(propertyName);
        } else {
            return selectedModel.getAttributeInfo(rmTypeName, propertyName) != null;
        }
    }

    public boolean isNullable(String typeId, String attributeName) {
        if(selectedBmmModel != null) {
            String className = BmmDefinitions.typeNameToClassKey(typeId);
            BmmClass classDefinition = selectedBmmModel.getClassDefinition(className);
            if(classDefinition == null) {
                return false;
            }
            if(!classDefinition.hasPropertyWithName(attributeName)) {
                return false;
            }
            BmmClass bmmClass = classDefinition.flattenBmmClass();
            BmmProperty bmmProperty = bmmClass.getProperties().get(attributeName);
            return !bmmProperty.getMandatory() || (bmmProperty.getExistence() != null && !bmmProperty.getExistence().isMandatory());
        } else {
            return selectedModel.getAttributeInfo(typeId, attributeName).isNullable();
        }
    }

    /**
     * return whether the attribute identified by rmTypeName.rmAttributeName can contain the type childConstraintTypeName
     * @param rmTypeName
     * @param rmAttributeName
     * @param childConstraintTypeName
     * @return
     */
    public boolean typeConformant(String rmTypeName, String rmAttributeName, String childConstraintTypeName) {
        if(getSelectedBmmModel() != null) {
            String propertyType = selectedBmmModel.effectivePropertyType(rmTypeName, rmAttributeName);
            BmmClass parentClass = selectedBmmModel.getClassDefinition(BmmDefinitions.typeNameToClassKey(rmTypeName));
            BmmClass childClass = selectedBmmModel.getClassDefinition(BmmDefinitions.typeNameToClassKey(childConstraintTypeName));
            if(childClass != null && parentClass != null) {
                BmmClass flatParentClass = parentClass.flattenBmmClass();
                BmmProperty property = flatParentClass.getProperties().get(rmAttributeName);
                if(property != null) {
                    String propertyConfTypeName = property.getType().getBaseClass().getTypeName();
                   // if(BmmDefinitions.validGenericTypeName(propertyConfTypeName) &&
                   //         !BmmDefinitions.validGenericTypeName(childConstraintTypeName)) {

                    //}
                    return rmTypesConformant(childConstraintTypeName, propertyConfTypeName);
                }


            }
            return false;
        } else {
            RMTypeInfo typeInfo = selectedModel.getTypeInfo(childConstraintTypeName);
            RMAttributeInfo owningAttributeInfo = selectedModel.getAttributeInfo(rmTypeName, rmAttributeName);
            if (owningAttributeInfo != null) {//this case is another validation, see the validate(cattribute) method of this class
                //TODO: make this work with metadata, not directly with classes
                //TODO: generics other than 'typeincollection' might be nice :)
                Class typeInCollection = owningAttributeInfo.getTypeInCollection();
                if (!typeInCollection.isAssignableFrom(typeInfo.getJavaClass())) {
                    return false;
                }
            }
            return true;
        }
    }
}
