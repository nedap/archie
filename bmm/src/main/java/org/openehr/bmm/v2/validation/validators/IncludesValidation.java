package org.openehr.bmm.v2.validation.validators;

import org.openehr.bmm.persistence.validation.BmmMessageIds;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.validation.BmmValidation;
import org.openehr.bmm.v2.validation.PSchemaRepository;
import org.openehr.utils.message.MessageLogger;

public class IncludesValidation implements BmmValidation {
    @Override
    public void validate(PBmmSchema schema, PSchemaRepository repository, MessageLogger logger) {
        if(schema.getIncludes() != null) {
            schema.getIncludes().keySet().forEach(include -> {
                if(!repository.containsPersistentSchema(include)) {
                    logger.addError(BmmMessageIds.ec_BMM_INC, schema.getSchemaId(), include);
                }
            });
        }
    }
}
