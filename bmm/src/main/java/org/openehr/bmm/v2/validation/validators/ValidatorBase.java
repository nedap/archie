package org.openehr.bmm.v2.validation.validators;

import org.openehr.bmm.persistence.validation.BmmMessageIds;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.utils.message.MessageCode;
import org.openehr.utils.message.MessageDescriptor;
import org.openehr.utils.message.MessageLogger;
import org.openehr.utils.message.MessageSeverity;

public abstract class ValidatorBase {

    protected MessageLogger logger;

    public ValidatorBase(MessageLogger logger) {
        this.logger = logger;
    }

    public ValidatorBase() {}

    public void setLogger(MessageLogger logger) {
        this.logger = logger;
    }

    /**
     * append an error with key `a_key' and `args' array to the `errors' string to the
     * error list for schema with `a_schema_id'
     *  @param schema
     * @param aKey
     * @param sourceSchemaId
     * @param arguments
     */
    protected void addValidityError(PBmmSchema schema, String sourceSchemaId, MessageCode aKey, Object... arguments) {
        if(sourceSchemaId.equals(schema.getSchemaId())) {
            logger.addError(aKey, arguments);
        } else {
            //addSchemaErrorTableIfNotExists(sourceSchemaId);
            //schemaErrorTableCache.get(sourceSchemaId).addErrorWithLocation(aKey, "", arguments);
            MessageDescriptor messageDescriptor = new MessageDescriptor(aKey, MessageSeverity.ERROR, aKey.getMessage(arguments), null);
            logger.addError(BmmMessageIds.ec_BMM_INCERR2, schema.getSchemaId(), sourceSchemaId, messageDescriptor.getMessage());
        }
    }
}
