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

/**
 * The result of validating a PBmmSchema  and converting it to a BmmModel. Contains:
 * - the original schema in persisted format
 * - the schema in persisted format, with all included files merged in
 * - the resulting BmmModel
 * - the messageLogger containing any info, warning and debug messages during validation and conversion
 * - the schema ids that have been merged in
 * - the schema ids that should have been merged in, but failed to merge
 */
public class BmmValidationResult {

    //input from file
    private String schemaId;
    private PBmmSchema originalSchema;

    //all fields below are the result from validating and conversion
    private MessageLogger logger;
    private BmmModel model;


    private PBmmSchema schemaWithMergedIncludes;

    /**
     * packages in canonical format, so never "org.openehr.ehr", but "org" -> "openehr" -> "ehr"
     */
    private Map<String, PBmmPackage> canonicalPackages = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Schemas that have been merged into schemaWithMergedIncludes
     */
    private List<String> mergedSchemas = new ArrayList<>();

    /**
     * Schemas that have failed to merge into schemaWithMergedIncludes
     */
    private List<String> failedMergedSchemas = new ArrayList<>();

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

    public List<String> getFailedMergedSchemas() {
        return failedMergedSchemas;
    }

    public void setFailedMergedSchemas(List<String> failedMergedSchemas) {
        this.failedMergedSchemas = failedMergedSchemas;
    }

    public void addFailedMerge(String schemaId) {
        this.failedMergedSchemas.add(schemaId);
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
