package org.openehr.bmm.v2.validation;

import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.v2.persistence.PBmmPackage;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.utils.message.MessageLogger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BmmValidationResult {

    private String schemaId;
    private MessageLogger logger;
    private BmmModel model;
    private PBmmSchema originalSchema;
    private PBmmSchema schemaWithMergedIncludes;

    private Map<String, PBmmPackage> canonicalPackages = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private List<String> mergedSchemas = new ArrayList<>();

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
        return logger == null || !logger.hasErrors();
    }

    public PBmmSchema getOriginalSchema() {
        return originalSchema;
    }

    public void setOriginalSchema(PBmmSchema originalSchema) {
        this.originalSchema = originalSchema;
    }

    public PBmmSchema getSchemaWithMergedIncludes() {
        return schemaWithMergedIncludes;
    }

    public void setSchemaWithMergedIncludes(PBmmSchema schemaWithMergedIncludes) {
        this.schemaWithMergedIncludes = schemaWithMergedIncludes;
    }

    public Map<String, PBmmPackage> getCanonicalPackages() {
        return canonicalPackages;
    }

    public void setCanonicalPackages(Map<String, PBmmPackage> canonicalPackages) {
        this.canonicalPackages = canonicalPackages;
    }

    public void addMergedSchema(String schemaId) {
        mergedSchemas.add(schemaId);
    }

    public List<String> getMergedSchemas() {
        return mergedSchemas;
    }

    @Override
    public String toString() {
        String result =  "Validation result of " + schemaId;
        if(logger != null) {
            result += logger.hasErrors() ? ": Failed" : ": Passed";
        }
        return result;
    }

}
