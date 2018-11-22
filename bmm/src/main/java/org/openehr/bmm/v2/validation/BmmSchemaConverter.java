package org.openehr.bmm.v2.validation;


import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.v2.persistence.PBmmPackage;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.validation.converters.CanonicalPackagesGenerator;
import org.openehr.bmm.v2.validation.converters.BmmModelCreator;
import org.openehr.bmm.v2.validation.converters.IncludesProcessor;

import java.util.List;
import java.util.Map;

/**
 * Converts a Persisted BMM Schema into a computable BMM Schema. Can convert only ONE schema at a time!
 */
public class BmmSchemaConverter {

    private final PSchemaRepository repository;
    /**
     * package structure in which all top-level qualified package names like xx.yy.zz have been
     * expanded out to a hierarchy of BMM_PACKAGE objects
     */
    private BmmSchemaValidator schemaValidator;

    public BmmSchemaConverter(PSchemaRepository repository) {
        this.repository = repository;
        schemaValidator = new BmmSchemaValidator(repository);
    }

    public BmmSchemaConverter(PSchemaRepository repository, BmmSchemaValidator validator) {
        this.repository = repository;
        this.schemaValidator = validator;
    }


    /**
     * Load the first validations and setup the
     * @param schema
     * @throws BmmSchemaValidationException
     */
    public BmmValidationResult validateAndConvert(PBmmSchema schema) throws BmmSchemaValidationException {
        try {
            schemaValidator.validateCreated(schema);
            schemaValidator.checkNoExceptions();

            schemaValidator.validateBmmVersion(schema);
            schemaValidator.checkNoExceptions();
            schemaValidator.validateUniqueness(schema);
            schemaValidator.validateIncludes(schema);
            schemaValidator.checkNoExceptions();
        } catch (BmmSchemaValidationException ex) {
            BmmValidationResult result = new BmmValidationResult();
            result.setSchemaId(schema.getSchemaId());
            return result;
        }

        BmmValidationResult result = new BmmValidationResult();
        result.setSchemaId(schema.getSchemaId());
        BmmModel bmmModel = new BmmModelCreator().create(schema);
        result.setModel(bmmModel);
        Map<String, PBmmPackage> canonicalPackages = new CanonicalPackagesGenerator().generateCanonicalPackages(schema);

        new IncludesProcessor().addIncludes(schema, bmmModel, repository, schemaValidator.getLogger());
    }

    public void convertPhase1() {


    }

}
