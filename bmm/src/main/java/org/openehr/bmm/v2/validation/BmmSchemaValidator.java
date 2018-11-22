package org.openehr.bmm.v2.validation;

import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.validation.validators.BmmVersionValidation;
import org.openehr.bmm.v2.validation.validators.CreatedSchemaValidation;
import org.openehr.bmm.v2.validation.validators.IncludesValidation;
import org.openehr.bmm.v2.validation.validators.UniqueSchemaIdValidation;
import org.openehr.utils.message.MessageLogger;

public class BmmSchemaValidator {

    private final PSchemaRepository repository;
    private MessageLogger logger;

    public BmmSchemaValidator(PSchemaRepository repository) {
        this.repository = repository;
        logger = new MessageLogger();
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
    public void validateCreated(PBmmSchema schema) {
        new CreatedSchemaValidation().validate(schema, repository, logger);
    }

    public void validateBmmVersion(PBmmSchema schema) {
        new BmmVersionValidation().validate(schema, repository, logger);
    }

    public void validateUniqueness(PBmmSchema schema) {
        new UniqueSchemaIdValidation().validate(schema, repository, logger);
    }

    public void validateIncludes(PBmmSchema schema) {
        new IncludesValidation().validate(schema, repository, logger);
    }

    public MessageLogger getLogger() {
        return logger;
    }

    public void checkNoExceptions() throws BmmSchemaValidationException {

        if(logger.hasErrors()) {
            throw new BmmSchemaValidationException(logger);
        }
    }

}
