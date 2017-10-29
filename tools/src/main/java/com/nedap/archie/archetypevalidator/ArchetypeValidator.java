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

    //see comment on why there is a phase 0
    private List<ArchetypeValidation> validationsPhase0;

    private List<ArchetypeValidation> validationsPhase1;

    private List<ArchetypeValidation> validationsPhase2;
    private ModelInfoLookup modelInfoLookup;

    public ArchetypeValidator(ModelInfoLookup lookup) {
        modelInfoLookup = lookup;

        validationsPhase0 = new ArrayList<>();
        //defined in spec, but not in three phase validator and not in grammar
        //eiffel checks these in the parser
        //but there's no reason this cannot be parsed, so check them here
        validationsPhase0.add(new AttributeUniquenessValidation(lookup));
        validationsPhase0.add(new NodeIdValidation(lookup));


        validationsPhase1 = new ArrayList<>();
        //conforms to spec
        validationsPhase1.add(new BasicChecks(lookup));
        validationsPhase1.add(new AuthoredArchetypeMetadataChecks(lookup));
        validationsPhase1.add(new DefinitionStructureValidation(lookup));
        validationsPhase1.add(new BasicTerminologyValidation(lookup));
        validationsPhase1.add(new CodeValidation(lookup));
        validationsPhase1.add(new VariousStructureValidation(lookup));

        validationsPhase2 = new ArrayList<>();

        //probably does not conform to spec

        validationsPhase2.add(new ValidateAgainstReferenceModel(lookup));
        validationsPhase2.add(new SpecializedDefinitionValidation(lookup));



    }

    public List<ValidationMessage> validate(Archetype archetype) {
        return validate(archetype, null);
    }


    /**
     * Validate an archetype, plus a repository of other archetypes.
     *
     * If the archetype is specialized, it MUST be in its differential form.
     * @param archetype
     * @param repository
     * @return
     */
    public List<ValidationMessage> validate(Archetype archetype, ArchetypeRepository repository) {
        Archetype flatParent = null;
        if(archetype.isSpecialized()) {
            try {
                Archetype parent = repository.getArchetype(archetype.getParentArchetypeId());
                if(parent != null) {
                    flatParent = new Flattener(repository).flatten(parent);
                }
            } catch (Exception e) {
                //...all the validators can handle flatParent being null
            }
        }
        if(repository == null) {
            repository = new SimpleArchetypeRepository();
        }

        List<ValidationMessage> messages = runValidations(archetype, repository, flatParent, validationsPhase0);
        messages.addAll(runValidations(archetype, repository, flatParent, validationsPhase1));
        messages.addAll(runValidations(archetype, repository, flatParent, validationsPhase2));
        return messages;
    }

    private List<ValidationMessage> runValidations(Archetype archetype, ArchetypeRepository repository, Archetype flatParent, List<ArchetypeValidation> validations) {
        List<ValidationMessage> messages = new ArrayList<>();
        for(ArchetypeValidation validation: validations) {
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