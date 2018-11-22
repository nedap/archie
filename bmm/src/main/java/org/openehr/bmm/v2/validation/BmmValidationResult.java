package org.openehr.bmm.v2.validation;

import org.openehr.bmm.core.BmmModel;
import org.openehr.utils.message.MessageLogger;

public class BmmValidationResult {

    private String schemaId;
    private MessageLogger logger;
    private BmmModel model;

    public String getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }

    public MessageLogger getLogger() {
        return logger;
    }

    public void setLogger(MessageLogger logger) {
        this.logger = logger;
    }

    public BmmModel getModel() {
        return model;
    }

    public void setModel(BmmModel model) {
        this.model = model;
    }

    public boolean passes() {
        return !logger.hasErrors();
    }
}
