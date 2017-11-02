package com.nedap.archie.archetypevalidator;

import com.google.common.base.Joiner;
import com.nedap.archie.adlparser.modelconstraints.ReflectionConstraintImposer;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.archetypevalidator.validations.*;
import com.nedap.archie.flattener.ArchetypeRepository;
import com.nedap.archie.flattener.Flattener;
import com.nedap.archie.flattener.FullArchetypeRepository;
import com.nedap.archie.flattener.InMemoryFullArchetypeRepository;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.ReferenceModels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public class ArchetypeValidator {
    private static final Logger logger = LoggerFactory.getLogger(ArchetypeValidator.class);
    private final ReferenceModels models;

    //see comment on why there is a phase 0
    private List<ArchetypeValidation> validationsPhase0;

    private List<ArchetypeValidation> validationsPhase1;

    private List<ArchetypeValidation> validationsPhase2;

    private List<ArchetypeValidation> validationsPhase3;


    public ArchetypeValidator(ReferenceModels models) {
        this.models = models;

        validationsPhase0 = new ArrayList<>();
        //defined in spec, but not in three phase validator and not in grammar
        //eiffel checks these in the parser
        //but there's no reason this cannot be parsed, so check them here
        validationsPhase0.add(new AttributeUniquenessValidation());
        validationsPhase0.add(new NodeIdValidation());
        validationsPhase0.add(new ExistenceValidation());


        validationsPhase1 = new ArrayList<>();
        //conforms to spec
        validationsPhase1.add(new BasicChecks());
        validationsPhase1.add(new AuthoredArchetypeMetadataChecks());
        validationsPhase1.add(new DefinitionStructureValidation());
        validationsPhase1.add(new BasicTerminologyValidation());
        validationsPhase1.add(new VariousStructureValidation());
        validationsPhase1.add(new CodeValidation());
        validationsPhase1.add(new AnnotationsValidation());
        //TODO: validation annotations

        validationsPhase2 = new ArrayList<>();

        validationsPhase2.add(new ValidateAgainstReferenceModel());
        validationsPhase2.add(new SpecializedDefinitionValidation());

        validationsPhase3 = new ArrayList<>();



    }

    public ValidationResult validate(Archetype archetype) {
        return validate(archetype, null);
    }


    /**
     * Validate an archetype, plus a repository of other archetypes.
     *
     * If the archetype is specialized, it MUST be in its differential form.
     *
     * Returns a validationResult that contains errors, warnings, the source archetype and the flattened form of the archetype.
     * If the parent of a specialized archetype did not validate, returns the validationResult
     * of the parent - because that is the error. Using classes must check if the actual archetype given in the argument
     * or the parent archetype caused the error by inspecting ValidationResult.getArchetypeId().
     *
     * All results will be stored in the given FullArchetypeRepository
     *
     * @param archetype
     * @param repository
     * @return
     */
    public ValidationResult validate(Archetype archetype, FullArchetypeRepository repository) {
        ModelInfoLookup lookup = models.getModel(archetype);
        if(lookup == null) {
            throw new UnsupportedOperationException("reference model unknown for archetype " + archetype.getArchetypeId());
        }
        //we assume we always want a new validation to be run, for example because the archetype
        //has been updated. Therefore, do not retrieve the old result from the repository
        archetype = cloneAndPreprocess(lookup, archetype);//this clones the actual archetype so the source does not get changed
        ValidationResult parentValidationResult = null;
        Archetype flatParent = null;
        if(archetype.isSpecialized()) {
            parentValidationResult = getParentValidationResult(archetype, repository);
            if(parentValidationResult != null) {
                if(parentValidationResult.passes()) {
                    flatParent = parentValidationResult.getFlattened();
                } else {
                    return parentValidationResult;
                }
            }
        }
        if(repository == null) {
            repository = new InMemoryFullArchetypeRepository();
        }

        List<ValidationMessage> messages = runValidations(lookup, archetype, repository, flatParent, validationsPhase0);
        messages.addAll(runValidations(lookup, archetype, repository, flatParent, validationsPhase1));

        //the separate validations will check if the archtype is specialized and if they need this in phase 2
        //because the RM validations are technically phase 2 and required to run
        //also the separate validations are implemented so that they can run with errors in phase 1 without exceptions
        //plus exceptions will nicely be logged as an OTHER error type - we can safely run it and you will get
        //more errors in one go - could be useful
        messages.addAll(runValidations(lookup, archetype, repository, flatParent, validationsPhase2));

        ValidationResult result = new ValidationResult(archetype);
        result.setErrors(messages);
        if(messages.isEmpty()) {
            result.setFlattened(new Flattener(repository).flatten(archetype));
            messages.addAll(runValidations(lookup, archetype, repository, flatParent, validationsPhase3));
        }
        if(repository != null) {
            repository.setValidationResult(result);
        }
        return result;
    }

    private ValidationResult getParentValidationResult(Archetype archetype, FullArchetypeRepository repository) {
        if(!archetype.isSpecialized()) {
            return null; //no parent
        }
        if(repository == null) {
            return null;
        }
        Archetype parent = repository.getArchetype(archetype.getParentArchetypeId());
        if(parent == null) {
            return null; //this situation will trigger the correct message later
        }

        ValidationResult validationResultOfParent = repository.getValidationResult(archetype.getParentArchetypeId());
        if(validationResultOfParent == null) {
            //parent not yet validated. do it now.
            validationResultOfParent = validate(parent, repository);
        }
        return validationResultOfParent;
    }

    private Archetype cloneAndPreprocess(ModelInfoLookup lookup, Archetype archetype) {
        Archetype preprocessed = archetype.clone();
        new ReflectionConstraintImposer(lookup).setSingleOrMultiple(preprocessed.getDefinition());
        return preprocessed;
    }

    private List<ValidationMessage> runValidations(ModelInfoLookup lookup, Archetype archetype, ArchetypeRepository repository, Archetype flatParent, List<ArchetypeValidation> validations) {

        List<ValidationMessage> messages = new ArrayList<>();
        for(ArchetypeValidation validation: validations) {
            try {
                messages.addAll(validation.validate(lookup, archetype, flatParent, repository));
            } catch (Exception e) {
                logger.error("error running validation processor", e);
                e.printStackTrace();
                messages.add(new ValidationMessage(ErrorType.OTHER, "unknown path", "error running validator : " + Joiner.on("\n").join(e.getStackTrace())));
            }
        }
        return messages;
    }

}
