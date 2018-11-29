package com.nedap.archie.rminfo;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.profile.AomProfile;
import com.nedap.archie.aom.profile.AomProfiles;
import com.nedap.archie.base.MultiplicityInterval;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.persistence.validation.BmmDefinitions;
import org.openehr.bmm.rmaccess.ReferenceModelAccess;
import org.openehr.bmm.v2.validation.BmmValidationResult;
import org.openehr.bmm.v2.validation.BmmRepository;

/**
 * MetaModel class that provides some opertaions for archetype validation and flattener that is either based on
 * an implementation-derived model (ModelInfoLookup) or BMM
 *
 * To use, select a model first using the selectModel() method. Then you can use any of the methods from MetaModelInterface
 * or obtain the underlying models directly. Trying to use the MetaModelInterface methods without selecting a model will
 * result in a NoModelSelectedException being thrown.
 *
 */
public class MetaModels implements MetaModelInterface {

    private final ReferenceModels models;
    private final ReferenceModelAccess bmmModels;
    private final BmmRepository bmmRepository;
    private AomProfiles aomProfiles;

    private MetaModel selectedModel;
    private AomProfile selectedAomProfile;


    public MetaModels(ReferenceModels models, ReferenceModelAccess bmmModels, AomProfiles profiles) {
        this.models = models;
        this.bmmModels = bmmModels;
        this.aomProfiles = profiles;
        this.bmmRepository = null;
    }

    public MetaModels(ReferenceModels models, ReferenceModelAccess bmmModels) {
        this.models = models;
        this.bmmModels = bmmModels;
        aomProfiles = new AomProfiles();
        this.bmmRepository = null;
    }


    public MetaModels(ReferenceModels models, BmmRepository repository) {
        this.models = models;
        this.bmmModels = null;
        this.bmmRepository = repository;
        aomProfiles = new AomProfiles();
    }

    public MetaModels(ReferenceModels models, BmmRepository repository, AomProfiles profiles) {
        this.models = models;
        this.bmmModels = null;
        this.bmmRepository = repository;
        aomProfiles = profiles;
    }

    /**
     * Select a meta model based on an archetype
     * @param archetype the archetype to find the model for
     * @throws ModelNotFoundException when no BMM and no ModelInfoLookup model has been found matching the archetype
     */
    public void selectModel(Archetype archetype) throws ModelNotFoundException {
        selectModel(archetype, null);
    }


    /**
     * Select a model based on an archetype, but override the RM Release with the given rm release version
     * @param archetype the archetype to find the model for
     * @param overridenRmRelease the version of the reference model you want to check with.
     * @throws ModelNotFoundException
     */
    public void selectModel(Archetype archetype, String overridenRmRelease) throws ModelNotFoundException {
        ModelInfoLookup selectedModel = null;
        BmmModel selectedBmmModel = null;
        String rmRelease = overridenRmRelease == null ? archetype.getRmRelease() : overridenRmRelease;
        if(models != null) {
             selectedModel = models.getModel(archetype);
        }
        if(bmmRepository != null) {
            BmmValidationResult validationResult = bmmRepository.getModelByClosure(BmmDefinitions.publisherQualifiedRmClosureName(archetype.getArchetypeId().getRmPublisher(), archetype.getArchetypeId().getRmPackage()) + "_" +  rmRelease);
            selectedBmmModel = validationResult == null ? null : validationResult.getModel();
        } else if(bmmModels != null) {
            selectedBmmModel = bmmModels.getReferenceModelForClosure(BmmDefinitions.publisherQualifiedRmClosureName(archetype.getArchetypeId().getRmPublisher(), archetype.getArchetypeId().getRmPackage()), rmRelease);
        }

        for(AomProfile profile:aomProfiles.getProfiles()) {
            if(profile.getProfileName().equalsIgnoreCase(archetype.getArchetypeId().getRmPublisher())) {
                this.selectedAomProfile = profile;
                break;
            }
        }

        if(selectedModel == null && selectedBmmModel == null) {
            throw new ModelNotFoundException(String.format("model for %s not found", archetype.getArchetypeId().toString()));
        }
        this.selectedModel = new MetaModel(selectedModel, selectedBmmModel, selectedAomProfile);



    }

    public ModelInfoLookup getSelectedModelInfoLookup() {
        return selectedModel == null ? null : selectedModel.getSelectedModel();
    }

    public BmmModel getSelectedBmmModel() {
        return selectedModel == null ? null : selectedModel.getSelectedBmmModel();
    }

    public MetaModel getSelectedModel() {
        return selectedModel;
    }

    public ReferenceModels getReferenceModels() {
        return models;
    }

    public ReferenceModelAccess getReferenceModelAccess() {
        return bmmModels;
    }

    public BmmRepository getBmmRepository() {
        return bmmRepository;
    }

    public boolean isMultiple(String typeName, String attributeName) {
        checkThatModelHasBeenSelected();
        return selectedModel.isMultiple(typeName, attributeName);
    }

    public boolean rmTypesConformant(String childTypeName, String parentTypeName) {
        checkThatModelHasBeenSelected();
        return selectedModel.rmTypesConformant(childTypeName, parentTypeName);
    }

    public boolean typeNameExists(String typeName) {
        checkThatModelHasBeenSelected();
        return selectedModel.typeNameExists(typeName);
    }

    public boolean attributeExists(String rmTypeName, String propertyName) {
        checkThatModelHasBeenSelected();
        return selectedModel.attributeExists(rmTypeName, propertyName);
   }

    @Override
    public boolean isNullable(String typeId, String attributeName) {
        checkThatModelHasBeenSelected();
        return selectedModel.isNullable(typeId, attributeName);
    }


    public boolean typeConformant(String rmTypeName, String rmAttributeName, String childConstraintTypeName) {
        checkThatModelHasBeenSelected();
        return selectedModel.typeConformant(rmTypeName, rmAttributeName, childConstraintTypeName);

    }

    public boolean hasReferenceModelPath(String rmTypeName, String path) {
        checkThatModelHasBeenSelected();
        return selectedModel.hasReferenceModelPath(rmTypeName, path);
    }

    public MultiplicityInterval referenceModelPropMultiplicity(String rmTypeName, String rmAttributeName) {
        checkThatModelHasBeenSelected();
        return selectedModel.referenceModelPropMultiplicity(rmTypeName, rmAttributeName);
    }

    public boolean validatePrimitiveType(String rmTypeName, String rmAttributeName, CPrimitiveObject cObject) {
        checkThatModelHasBeenSelected();
        return selectedModel.validatePrimitiveType(rmTypeName, rmAttributeName, cObject);
    }

    private void checkThatModelHasBeenSelected() throws NoModelSelectedException {
        if(selectedModel == null) {
            throw new NoModelSelectedException("Please call the selectModel() method before trying to use MetaModels");
        }

    }

    public boolean isOrdered(String rmTypeName, String rmAttributeName) {
        checkThatModelHasBeenSelected();
        return selectedModel.isOrdered(rmTypeName, rmAttributeName);
    }

    public AomProfiles getAomProfiles() {
        return aomProfiles;
    }

    public AomProfile getSelectedAomProfile() {
        return selectedAomProfile;
    }


}
