package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeHRID;
import com.nedap.archie.aom.OperationalTemplate;
import com.nedap.archie.archetypevalidator.ArchetypeValidator;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.archetypevalidator.ValidationResult;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.ReferenceModels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryFullArchetypeRepository extends SimpleArchetypeRepository implements FullArchetypeRepository {

    private Map<String, ValidationResult> validationResult = new ConcurrentHashMap<>();

    private Map<String, Archetype> flattenedArchetypes = new ConcurrentHashMap<>();
    private Map<String, Archetype> operationalTemplates = new ConcurrentHashMap<>();

    public Archetype getFlattenedArchetype(String archetypeId) {
        return flattenedArchetypes.get(new ArchetypeHRID(archetypeId).getSemanticId());
    }

    public ValidationResult getValidationResult(String archetypeId) {
        return validationResult.get(new ArchetypeHRID(archetypeId).getSemanticId());
    }

    public void setValidationResult(ValidationResult result) {
        validationResult.put(new ArchetypeHRID(result.getArchetypeId()).getSemanticId(), result);
    }

    public void setFlattenedArchetype(Archetype archetype) {
        flattenedArchetypes.put(archetype.getArchetypeId().getSemanticId(), archetype);
    }

    public void setOperationalTemplate(OperationalTemplate template) {
        operationalTemplates.put(template.getArchetypeId().getSemanticId(), template);
    }

    public List<ValidationResult> getAllValidationResults() {
        return new ArrayList<>(validationResult.values());
    }

    public void compile(ReferenceModels models) {
        ArchetypeValidator validator = new ArchetypeValidator(models);
        for(Archetype archetype:getAllArchetypes()) {
            if(getValidationResult(archetype.getArchetypeId().toString()) == null) {
                validator.validate(archetype, this);
            }
        }
    }
}
