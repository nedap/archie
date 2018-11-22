package org.openehr.bmm.v2.validation;

import org.openehr.utils.message.MessageLogger;

public class BmmSchemaValidationException extends Exception {
    public BmmSchemaValidationException(MessageLogger logger) {
        super(logger.toString());
    }
}
