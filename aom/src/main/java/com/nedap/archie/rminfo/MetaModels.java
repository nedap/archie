package com.nedap.archie.rminfo;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.RMAttributeInfo;
import com.nedap.archie.rminfo.ReferenceModels;
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmContainerProperty;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmProperty;
import org.openehr.bmm.persistence.validation.BmmDefinitions;
import org.openehr.bmm.rmaccess.ReferenceModelAccess;

import java.util.List;

import static org.openehr.bmm.persistence.validation.BmmDefinitions.typeNameToClassKey;

/**
 * MetaModel class that provides some opertaions for archetype validation and flattener that is either based on
 * an implementation-derived model (ModelInfoLookup) or BMM
 */
public class MetaModels {

    private final ReferenceModels models;
    private final ReferenceModelAccess bmmModels;

    private MetaModel selectedModel;


    public MetaModels(ReferenceModels models, ReferenceModelAccess bmmModels) {
        this.models = models;
        this.bmmModels = bmmModels;
    }

    public void selectModel(Archetype archetype) {
        ModelInfoLookup selectedModel = null;
        BmmModel selectedBmmModel = null;
        if(models != null) {
             selectedModel = models.getModel(archetype);
        }
        if(bmmModels != null) {
             selectedBmmModel = bmmModels.getReferenceModelForClosure(BmmDefinitions.publisherQualifiedRmClosureName(archetype.getArchetypeId().getRmPublisher(), archetype.getArchetypeId().getRmPackage()));
        }

        this.selectedModel = new MetaModel(selectedModel, selectedBmmModel);

    }

    public ModelInfoLookup getSelectedModel() {
        return selectedModel == null ? null : selectedModel.getSelectedModel();
    }

    public BmmModel getSelectedBmmModel() {
        return selectedModel == null ? null : selectedModel.getSelectedBmmModel();
    }

    public ReferenceModels getReferenceModels() {
        return models;
    }

    public boolean isMultiple(String typeName, String attributeName) {
        return selectedModel == null ? false : selectedModel.isMultiple(typeName, attributeName);
    }

    public boolean rmTypesConformant(String childTypeName, String parentTypeName) {
        return selectedModel == null ? false : selectedModel.rmTypesConformant(childTypeName, parentTypeName);

    }

    public boolean typeNameExists(String typeName) {
        return selectedModel == null ? false : selectedModel.typeNameExists(typeName);
    }

    public boolean attributeExists(String rmTypeName, String propertyName) {
        return selectedModel == null ? false : selectedModel.attributeExists(rmTypeName, propertyName);
   }
}
