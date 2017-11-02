package com.nedap.archie.archetypevalidator;

import com.nedap.archie.aom.Archetype;

import java.util.ArrayList;
import java.util.List;

/**
 * The result of a validation: validation messages, if any,
 * the unprocessed source archetype, and the flattened archetype, if flattening is possible
 */
public class ValidationResult {

    private String archetypeId;//in case we do not even have an AOM because of a parse error
    private List<ValidationMessage> errors = new ArrayList<>();
    private Archetype sourceArchetype;
    private Archetype flattened;

    public ValidationResult(String archetypeId){
        this.archetypeId = archetypeId;
    }

    public ValidationResult(Archetype archetype) {
        this.sourceArchetype = archetype;
        this.archetypeId = archetype.getArchetypeId().toString();
    }

    public List<ValidationMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationMessage> errors) {
        this.errors = errors;
    }

    public Archetype getSourceArchetype() {
        return sourceArchetype;
    }

    public void setSourceArchetype(Archetype sourceArchetype) {
        this.sourceArchetype = sourceArchetype;
    }

    public Archetype getFlattened() {
        return flattened;
    }

    public void setFlattened(Archetype flattened) {
        this.flattened = flattened;
    }

    public String getArchetypeId() {
        return archetypeId;
    }

    public boolean passes() {
        return errors.isEmpty();
    }
}
