package com.nedap.archie.archetypevalidator;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.flattener.ArchetypeRepository;
import com.nedap.archie.rminfo.ModelInfoLookup;

import java.util.ArrayList;
import java.util.List;

public abstract class ArchetypeValidationBase implements ArchetypeValidation {

    protected final ModelInfoLookup lookup;
    protected Archetype archetype;
    protected Archetype flatParent;
    protected ArchetypeRepository repository;
    protected List<ValidationMessage> messages;

    public ArchetypeValidationBase(ModelInfoLookup lookup) {
        this.lookup = lookup;
    }

    @Override
    public List<ValidationMessage> validate(Archetype archetype, Archetype flatParent, ArchetypeRepository repository) {
        this.archetype = archetype;
        this.flatParent = flatParent;
        this.repository = repository;
        messages = new ArrayList<>();
        validate();
        return messages;
    }

    public abstract void validate();

    protected void addMessage(ErrorType errorType) {
        messages.add(new ValidationMessage(errorType));
    }

    protected void addMessageWithPath(ErrorType errorType, String path) {
        messages.add(new ValidationMessage(errorType, path));
    }

    protected void addMessageWithPath(ErrorType errorType, String path, String customMessage) {
        messages.add(new ValidationMessage(errorType, path, customMessage));
    }

    protected void addMessage(ErrorType errorType, String customMessage) {
        messages.add(new ValidationMessage(errorType, null, customMessage));
    }
}
