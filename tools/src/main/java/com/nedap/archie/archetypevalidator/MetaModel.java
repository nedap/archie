package com.nedap.archie.archetypevalidator;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeHRID;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.RMAttributeInfo;
import com.nedap.archie.rminfo.ReferenceModels;
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmContainerProperty;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmProperty;
import org.openehr.bmm.persistence.validation.BmmDefinitions;
import org.openehr.bmm.rmaccess.ReferenceModelAccess;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import static org.openehr.bmm.persistence.validation.BmmDefinitions.typeNameToClassKey;

/**
 * MetaModel class that provides some opertaions for archetype validation and flattener that is either based on
 * an implementation-derived model (ModelInfoLookup) or BMM
 */
public class MetaModel {

    private final ReferenceModels models;
    private final ReferenceModelAccess bmmModels;

    private transient ModelInfoLookup selectedModel;
    private transient BmmModel selectedBmmModel;

    public MetaModel(ReferenceModels models, ReferenceModelAccess bmmModels) {
        this.models = models;
        this.bmmModels = bmmModels;
    }

    public void selectModel(Archetype archetype) {
        if(models != null) {
            selectedModel = models.getModel(archetype);
        }
        if(bmmModels != null) {
            selectedBmmModel = bmmModels.getReferenceModelForClosure(BmmDefinitions.publisherQualifiedRmClosureName(archetype.getArchetypeId().getRmPublisher(), archetype.getArchetypeId().getRmPackage()));
        }
    }

    public ModelInfoLookup getSelectedModel() {
        return selectedModel;
    }

    public BmmModel getSelectedBmmModel() {
        return selectedBmmModel;
    }

    public ReferenceModels getReferenceModels() {
        return models;
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

    public boolean isDescendantOf(String parentTypeName, String descendantTypeName) {
        if(getSelectedBmmModel() != null) {
            BmmModel selectedBmmModel = getSelectedBmmModel();
            String parentClassName = typeNameToClassKey(parentTypeName);//generics stripped
            String descendantClassName = typeNameToClassKey(descendantTypeName);//generics stripped
            //TODO: generics as well. get the array and check each type?
            List<String> allAncestors = selectedBmmModel.getClassDefinition(descendantClassName).findAllAncestors();
            if(!parentClassName.equalsIgnoreCase(descendantClassName) && !allAncestors.contains(parentClassName)) {
                return false;
            }
            return true;
        } else {
            if (!selectedModel.getTypeInfo(parentTypeName).isDescendantOrEqual(selectedModel.getTypeInfo(descendantTypeName))) {
                return false;
            }
            return true;
        }
    }

    public boolean typeNameExists(String typeName) {
        if(getSelectedBmmModel() != null) {
            return selectedBmmModel.getClassDefinition(typeName) != null;
        } else {
            return selectedModel.getTypeInfo(typeName) != null;
        }
    }
}
