package org.openehr.bmm.v2.validation;

import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.validation.validators.BasicSchemaValidations;
import org.openehr.bmm.v2.validation.validators.BmmVersionValidation;
import org.openehr.bmm.v2.validation.validators.ClassesValidator;
import org.openehr.bmm.v2.validation.validators.CreatedSchemaValidation;
import org.openehr.bmm.v2.validation.validators.IncludesValidation;
import org.openehr.bmm.v2.validation.validators.UniqueSchemaIdValidation;
import org.openehr.utils.message.MessageLogger;

public class BmmSchemaValidator {

    private final BmmRepository repository;
    private MessageLogger logger;

    public BmmSchemaValidator(BmmRepository repository) {
        this.repository = repository;
        logger = new MessageLogger();
    }

    public void validateSchema(BmmValidationResult result) {
        run(new BasicSchemaValidations(), result, result.getSchemaWithMergedIncludes());
        run(new ClassesValidator(), result, result.getSchemaWithMergedIncludes());
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
    public void validateCreated(BmmValidationResult validationResult, PBmmSchema schema) {
        run(new CreatedSchemaValidation(), validationResult, schema);
    }

    public void validateBmmVersion(BmmValidationResult validationResult, PBmmSchema schema) {
        run(new BmmVersionValidation(), validationResult, schema);
    }

    public void validateUniqueness(BmmValidationResult validationResult, PBmmSchema schema) {
        run(new UniqueSchemaIdValidation(), validationResult, schema);
    }

    public void validateIncludes(BmmValidationResult validationResult, PBmmSchema schema) {
        run(new IncludesValidation(), validationResult, schema);
    }

    private void run(BmmValidation validation, BmmValidationResult validationResult, PBmmSchema schema) {
        validation.validate(validationResult, repository, logger, schema);
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
