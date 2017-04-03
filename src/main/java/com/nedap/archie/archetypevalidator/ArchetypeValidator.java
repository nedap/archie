package com.nedap.archie.archetypevalidator;

import com.google.common.base.Joiner;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.archetypevalidator.validations.AttributeUniquenessValidation;
import com.nedap.archie.archetypevalidator.validations.CTerminologyCodeValidation;
import com.nedap.archie.archetypevalidator.validations.ModelConformanceValidation;
import com.nedap.archie.archetypevalidator.validations.NodeIdValidation;

import com.nedap.archie.archetypevalidator.validations.TerminologyValidation;
import com.nedap.archie.archetypevalidator.validations.ValueSetValidation;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.rminfo.ModelInfoLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public class ArchetypeValidator {
    private static final Logger logger = LoggerFactory.getLogger(ArchetypeValidator.class);

    private List<ArchetypeValidation> validations;
    private ModelInfoLookup modelInfoLookup;

    public ArchetypeValidator() {
        this(ArchieRMInfoLookup.getInstance());
    }

    public ArchetypeValidator(ModelInfoLookup lookup) {
        modelInfoLookup = lookup;

        validations = new ArrayList<>();
        validations.add(new NodeIdValidation());
        validations.add(new ModelConformanceValidation(lookup));
        validations.add(new AttributeUniquenessValidation());
        validations.add(new ValueSetValidation());
        validations.add(new TerminologyValidation());
        validations.add(new CTerminologyCodeValidation());

    }

    public List<ValidationMessage> validate(Archetype archetype) {

        List<ValidationMessage> messages = new ArrayList<>();

        for(ArchetypeValidation validation:validations) {
            try {
                messages.addAll(validation.validate(archetype));
            } catch (Exception e) {
                logger.error("error running validation processor", e);
                messages.add(new ValidationMessage(ErrorType.OTHER, "unknown path", "error running validator : " + Joiner.on("\n").join(e.getStackTrace())));
            }
         }
        return messages;
    }

 }
