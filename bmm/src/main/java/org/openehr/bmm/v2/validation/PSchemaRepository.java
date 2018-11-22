package org.openehr.bmm.v2.validation;

import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.v2.persistence.PBmmSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PSchemaRepository {

    private Map<String, PBmmSchema> persistentSchemasById = new ConcurrentHashMap<>();

    private Map<String, BmmValidationResult> modelsById = new ConcurrentHashMap<>();

    public PSchemaRepository() {

    }

    public List<PBmmSchema> getPersistentSchemas() {
        return new ArrayList<>(persistentSchemasById.values());
    }

    public PBmmSchema getPersistentSchema(String schemaId) {
        return persistentSchemasById.get(schemaId.toLowerCase());
    }

    public void addPersistentSchema(PBmmSchema schema) {
        this.persistentSchemasById.put(schema.getSchemaId().toLowerCase(), schema);
    }

    public List<BmmValidationResult> getModels() {
        return new ArrayList<>(modelsById.values());
    }

    public BmmValidationResult getModel(String schemaId) {
        return modelsById.get(schemaId.toLowerCase());
    }

    public void addModel(BmmValidationResult model) {
        this.modelsById.put(model.getSchemaId().toLowerCase(),  model);
    }

    public boolean containsPersistentSchema(String schemaId) {
        return persistentSchemasById.containsKey(schemaId.toLowerCase());
    }
}
