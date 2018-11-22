package org.openehr.bmm.v2.validation.validators;

import org.openehr.bmm.persistence.validation.BmmDefinitions;
import org.openehr.bmm.persistence.validation.BmmMessageIds;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.bmm.v2.validation.BmmValidation;
import org.openehr.bmm.v2.validation.PSchemaRepository;
import org.openehr.utils.message.MessageLogger;

public class BmmVersionValidation implements BmmValidation {
    @Override
    public void validate(PBmmSchema schema, PSchemaRepository repository, MessageLogger logger) {
        if (!BmmDefinitions.isBmmVersionCompatible(schema.getBmmVersion())) {
            logger.addError(BmmMessageIds.ec_BMM_VER,
                    schema.getSchemaId(),
                    schema.getBmmVersion(),
                    BmmDefinitions.BMM_INTERNAL_VERSION);
        }
    }
}
