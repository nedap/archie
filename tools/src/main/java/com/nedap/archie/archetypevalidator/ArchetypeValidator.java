package com.nedap.archie.archetypevalidator;

import com.google.common.base.Joiner;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.archetypevalidator.validations.*;
import com.nedap.archie.flattener.ArchetypeRepository;
import com.nedap.archie.flattener.Flattener;
import com.nedap.archie.flattener.SimpleArchetypeRepository;
import com.nedap.archie.rminfo.ModelInfoLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public class ArchetypeValidator {
    private static final Logger logger = LoggerFactory.getLogger(ArchetypeValidator.class);

    private List<ArchetypeValidation> validations;
    private ModelInfoLookup modelInfoLookup;

    public ArchetypeValidator(ModelInfoLookup lookup) {
        modelInfoLookup = lookup;

        validations = new ArrayList<>();
        validations.add(new BasicChecks());
        validations.add(new AuthoredArchetypeMetadataChecks());
        validations.add(new DefinitionStructureValidation());
        validations.add(new BasicTerminologyValidation());
        validations.add(new NodeIdValidation());
        validations.add(new ModelConformanceValidation(lookup));
        validations.add(new AttributeUniquenessValidation());
        validations.add(new ValueSetValidation());
        validations.add(new CTerminologyCodeValidation());

    }

    public List<ValidationMessage> validate(Archetype archetype) {
        return validate(archetype, null);
    }

    public List<ValidationMessage> validate(Archetype archetype, ArchetypeRepository repository) {
        Archetype flatParent = null;
        if(archetype.isSpecialized()) {
            flatParent = new Flattener(repository).flatten(archetype);
        }
        if(repository == null) {
            repository = new SimpleArchetypeRepository();
        }

        List<ValidationMessage> messages = new ArrayList<>();

        for(ArchetypeValidation validation:validations) {
            try {
                messages.addAll(validation.validate(archetype, flatParent, repository));
            } catch (Exception e) {
                logger.error("error running validation processor", e);
                messages.add(new ValidationMessage(ErrorType.OTHER, "unknown path", "error running validator : " + Joiner.on("\n").join(e.getStackTrace())));
            }
        }
        return messages;
    }

 }
