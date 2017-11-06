package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.OperationalTemplate;
import com.nedap.archie.archetypevalidator.ValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * repository that stores a few extra archetypes not affecting the regular repository, specifically for template overlays
 * TODO: find a better name, because this can override, but the main usage is add a few extra
 *
 * Add extra unflattened archetypes with the addExtraArchetype method
 *
 * ValidationResults and flattened archetypes will automatically be added to the extra archetypes repository if it
 * contains the archetype id in its unflattened form
 */
public class OverridingInMemFullArchetypeRepository implements FullArchetypeRepository {

    private final FullArchetypeRepository originalRepository;
    private final FullArchetypeRepository extraArchetypes = new InMemoryFullArchetypeRepository();

    public OverridingInMemFullArchetypeRepository() {
        originalRepository = new InMemoryFullArchetypeRepository();
    }

    public OverridingInMemFullArchetypeRepository(FullArchetypeRepository fullArchetypeRepository) {
        this.originalRepository = fullArchetypeRepository;
    }

    @Override
    public Archetype getFlattenedArchetype(String archetypeId) {
        Archetype result = extraArchetypes.getFlattenedArchetype(archetypeId);
        if(result != null) {
            return result;
        }
        return originalRepository.getFlattenedArchetype(archetypeId);
    }

    @Override
    public ValidationResult getValidationResult(String archetypeId) {
        ValidationResult result = extraArchetypes.getValidationResult(archetypeId);
        if(result != null) {
            return result;
        }
        return originalRepository.getValidationResult(archetypeId);
    }

    @Override
    public void setValidationResult(ValidationResult result) {
        if(extraArchetypes.getArchetype(result.getArchetypeId()) != null) {
            extraArchetypes.setValidationResult(result);
        } else {
            originalRepository.setValidationResult(result);
        }
    }

    @Override
    public void setFlattenedArchetype(Archetype archetype) {
        if(extraArchetypes.getArchetype(archetype.getArchetypeId().toString()) != null) {
            extraArchetypes.setFlattenedArchetype(archetype);
        } else {
            originalRepository.setFlattenedArchetype(archetype);
        }
    }

    @Override
    public void addArchetype(Archetype archetype) {
        originalRepository.addArchetype(archetype);
    }


    @Override
    public void setOperationalTemplate(OperationalTemplate template) {
        originalRepository.setOperationalTemplate(template);
    }

    @Override
    public List<ValidationResult> getAllValidationResults() {
        List<ValidationResult> result = new ArrayList<>(extraArchetypes.getAllValidationResults());
        result.addAll(originalRepository.getAllValidationResults());
        return result;
    }

    @Override
    public Archetype getArchetype(String archetypeId) {
        Archetype result = extraArchetypes.getArchetype(archetypeId);
        if(result != null) {
            return result;
        }
        return originalRepository.getArchetype(archetypeId);
    }

    @Override
    public List<Archetype> getAllArchetypes() {
        List<Archetype> result = new ArrayList<>(extraArchetypes.getAllArchetypes());
        result.addAll(originalRepository.getAllArchetypes());
        return result;
    }


    public void addExtraArchetype(Archetype override) {
        this.extraArchetypes.addArchetype(override);
    }

    public FullArchetypeRepository getExtraArchetypeRepository() {
        return extraArchetypes;
    }
}
