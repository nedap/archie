package org.openehr.bmm.v2.validation.validators;

import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.validation.BmmValidation;
import org.openehr.bmm.v2.validation.BmmValidationResult;
import org.openehr.bmm.v2.validation.BmmRepository;
import org.openehr.utils.message.MessageLogger;

public class UniqueSchemaIdValidation implements BmmValidation {
    @Override
    public void validate(BmmValidationResult validationResult, BmmRepository repository, MessageLogger logger, PBmmSchema schema) {

    }
}
