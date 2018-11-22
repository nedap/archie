package org.openehr.bmm.v2.validation.validators;

import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.validation.BmmValidation;
import org.openehr.bmm.v2.validation.PSchemaRepository;
import org.openehr.utils.message.MessageLogger;

public class UniqueSchemaIdValidation implements BmmValidation {
    @Override
    public void validate(PBmmSchema schema, PSchemaRepository repository, MessageLogger logger) {

    }
}
