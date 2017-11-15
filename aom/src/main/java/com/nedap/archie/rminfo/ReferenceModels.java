package com.nedap.archie.rminfo;


import com.nedap.archie.aom.Archetype;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReferenceModels {
    private Map<RMPackageId, ModelInfoLookup> referenceModelsById = new ConcurrentHashMap<>();

    public ReferenceModels() {

    }

    public ReferenceModels(ModelInfoLookup... models) {
        for(ModelInfoLookup model:models) {
            registerModel(model);
        }
    }

    public void registerModel(ModelInfoLookup model) {
        for(RMPackageId id:model.getId()) {
            referenceModelsById.put(id, model);
        }
    }

    public ModelInfoLookup getModel(String publisher, String aPackage) {
        return referenceModelsById.get(new RMPackageId(publisher, aPackage));
    }

    public ModelInfoLookup getModel(Archetype archetype) {
        return referenceModelsById.get(new RMPackageId(archetype.getArchetypeId().getRmPublisher(), archetype.getArchetypeId().getRmPackage()));
    }

    public List<ModelInfoLookup> getAllModels() {
        return new ArrayList<>(referenceModelsById.values());
    }
}
