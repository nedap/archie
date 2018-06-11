package com.nedap.archie.archetypevalidator;

public class ArchetypeValidationSettings {

    /**
     * When specializing cardinality and existence, is it allowed to specificy the exact same as in the parent object?
     */
    private boolean strictMultiplicitiesSpecializationValidation = true;
    /**
     * Whether to always try to flatten, even on validation errors
     */
    private boolean alwaysTryToFlatten = false;

    public ArchetypeValidationSettings() {
    }

    public boolean isStrictMultiplicitiesSpecializationValidation() {
        return strictMultiplicitiesSpecializationValidation;
    }

    public void setStrictMultiplicitiesSpecializationValidation(boolean strictMultiplicitiesSpecializationValidation) {
        this.strictMultiplicitiesSpecializationValidation = strictMultiplicitiesSpecializationValidation;
    }

    public boolean isAlwaysTryToFlatten() {
        return alwaysTryToFlatten;
    }

    public void setAlwaysTryToFlatten(boolean alwaysTryToFlatten) {
        this.alwaysTryToFlatten = alwaysTryToFlatten;
    }
}
