package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.archetypevalidator.ArchetypeValidation;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.flattener.ArchetypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasicChecks implements ArchetypeValidation {
    @Override
    public List<ValidationMessage> validate(Archetype archetype, Archetype flatParent, ArchetypeRepository repository) {
        List<ValidationMessage> result = new ArrayList<>();
        checkRmRootType(archetype, result);
        checkIdCodeSpecialisationLevel(archetype, repository, result);
        checkMissingTerminology(archetype, result);
        checkSpecializationDepth(archetype, repository, result);
        return result;
    }

    private void checkSpecializationDepth(Archetype archetype, ArchetypeRepository repository, List<ValidationMessage> result) {
        if(archetype.getParentArchetypeId() != null) {
            Archetype parent = repository.getArchetype(archetype.getParentArchetypeId());
            if(parent != null) {
                int parentNodeIdSpecialisationDepth = parent.getDefinition().specialisationDepth();
                int nodeIdSpecialisationDepth = archetype.getDefinition().specialisationDepth();
                if(parentNodeIdSpecialisationDepth != nodeIdSpecialisationDepth -1) {
                    result.add(new ValidationMessage(ErrorType.VACSD, null,
                            "The specialisation depth of the archetypes must be one greater than the specialisation depth of the parent archetype"));
                }
            }
        }
    }

    private void checkMissingTerminology(Archetype archetype, List<ValidationMessage> result) {
        //missing mandatory parts are checked in grammar, but check here as well
        if(archetype.getTerminology() == null) {
            result.add(new ValidationMessage(ErrorType.STCNT, null,
                    "archetype terminology missing"));
        } else if(archetype.getTerminology().getTermDefinitions().isEmpty()) {
            result.add(new ValidationMessage(ErrorType.STCNT, null,
                    "archetype terminology contains no term definitions"));
        }
    }

    private void checkIdCodeSpecialisationLevel(Archetype archetype, ArchetypeRepository repository, List<ValidationMessage> result) {
        int depth = ValidationUtils.getSpecializationDepth(archetype, repository);
        if(depth != archetype.getDefinition().specialisationDepth()) {
            result.add(new ValidationMessage(ErrorType.VARCN));
        }

    }


    private void checkRmRootType(Archetype archetype, List<ValidationMessage> result) {
        if(!Objects.equals(archetype.getArchetypeId().getRmClass(), archetype.getDefinition().getRmTypeName())) {
            result.add(new ValidationMessage(ErrorType.VARDT, null,
                    String.format("RM type in id %s does not match RM type in definition %s",
                            archetype.getArchetypeId().getConceptId(),
                            archetype.getDefinition().getRmTypeName())));
        }
    }
}
