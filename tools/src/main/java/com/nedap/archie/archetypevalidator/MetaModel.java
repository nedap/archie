package com.nedap.archie.archetypevalidator;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeHRID;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.ReferenceModels;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.persistence.validation.BmmDefinitions;
import org.openehr.bmm.rmaccess.ReferenceModelAccess;

import java.util.HashSet;
import java.util.Stack;

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
}
