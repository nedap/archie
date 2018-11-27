package org.openehr.bmm.v2.validation;


import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.persistence.validation.BmmDefinitions;
import org.openehr.bmm.persistence.validation.BmmMessageIds;
import org.openehr.bmm.v2.persistence.PBmmPackage;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.validation.converters.CanonicalPackagesGenerator;
import org.openehr.bmm.v2.validation.converters.BmmModelCreator;
import org.openehr.bmm.v2.validation.converters.DescendantsCalculator;
import org.openehr.bmm.v2.validation.converters.IncludesProcessor;
import org.openehr.bmm.v2.validation.converters.PreprocessPersistedSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Converts a Persisted BMM Schema into a computable BMM Schema. Can convert only ONE schema at a time!
 */
public class BmmSchemaConverter {

    private static final Logger logger = LoggerFactory.getLogger(BmmSchemaConverter.class);

    private final BmmRepository repository;
    /**
     * package structure in which all top-level qualified package names like xx.yy.zz have been
     * expanded out to a hierarchy of BMM_PACKAGE objects
     */
    private BmmSchemaValidator schemaValidator;

    public BmmSchemaConverter(BmmRepository repository) {
        this.repository = repository;
        schemaValidator = new BmmSchemaValidator(repository);
    }

    /**
     * Load the first validations and setup the
     * @param schema
     * @throws BmmSchemaValidationException
     */
    public BmmValidationResult validateAndConvert(PBmmSchema schema) {
        //clear error messages
        schemaValidator = new BmmSchemaValidator(repository);

        logger.info("loading " + schema.getSchemaId());

        BmmValidationResult result = new BmmValidationResult();
        result.setLogger(schemaValidator.getLogger());
        result.setSchemaId(schema.getSchemaId());
        result.setOriginalSchema(schema);
        try {
            schemaValidator.validateCreated(result, schema);
            schemaValidator.checkNoExceptions();

            schemaValidator.validateBmmVersion(result, schema);
            schemaValidator.checkNoExceptions();
            schemaValidator.validateUniqueness(result, schema);
            schemaValidator.validateIncludes(result, schema);
            schemaValidator.checkNoExceptions();

            new PreprocessPersistedSchema().preprocess(schema);

            Map<String, PBmmPackage> canonicalPackages = new CanonicalPackagesGenerator().generateCanonicalPackages(schema);
            result.setCanonicalPackages(canonicalPackages);

            new IncludesProcessor().cloneSchemaAndAddIncludes(result, repository, schemaValidator.getLogger());
            schemaValidator.checkNoExceptions();

            //step 3: validate schema
            schemaValidator.validateSchema(result);
            schemaValidator.checkNoExceptions();

            BmmModel bmmModel = new BmmModelCreator().create(result);
            schemaValidator.checkNoExceptions();

            new DescendantsCalculator().calculateDescendants(bmmModel);
            result.setModel(bmmModel);

            createModelsByClosureAndVersion(result);

            return result;
        } catch (BmmSchemaValidationException ex) {
            //cannot continue on validation error
            return result;
        }
    }

    private void createModelsByClosureAndVersion(BmmValidationResult validationResult) {
        BmmModel model = validationResult.getModel();
        List<String> rmClosures = new ArrayList<>();
        String schemaId = model.getSchemaId();
        String modelPublisher = model.getRmPublisher();
        String modelName = model.getModelName();
        if(modelName != null) {
            addClosure(schemaId, validationResult, modelPublisher, modelName);
        } else {
            //possibly old style BMM, test
            for(String closureName:model.getArchetypeRmClosurePackages()) {
                addClosure(schemaId, validationResult, modelPublisher, closureName);
            }
        }
    }

    private void addClosure(String schemaId, BmmValidationResult validationResult, String modelPublisher, String modelName) {
        String qualifiedRmClosureName = BmmDefinitions.publisherQualifiedRmClosureName(modelPublisher, modelName) + "_" + validationResult.getModel().getRmRelease();
        BmmValidationResult existingSchema = repository.getModelByClosure(qualifiedRmClosureName);
        if (existingSchema != null) {
            schemaValidator.getLogger().addInfo(BmmMessageIds.ec_bmm_schema_duplicate_found,
                    qualifiedRmClosureName,
                    existingSchema.getSchemaId(),
                    schemaId);
        } else {
            repository.addModelByClosure(qualifiedRmClosureName, validationResult);
        }

    }


}
