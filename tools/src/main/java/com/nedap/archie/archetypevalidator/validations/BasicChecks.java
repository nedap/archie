package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.archetypevalidator.ArchetypeValidation;
import com.nedap.archie.archetypevalidator.ArchetypeValidationBase;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.flattener.ArchetypeRepository;
import com.nedap.archie.rminfo.ModelInfoLookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasicChecks extends ArchetypeValidationBase {

    public BasicChecks() {
        super();
    }

    @Override
    public void validate() {
        checkRmRootType();
        checkIdCodeSpecialisationLevel();
        checkMissingTerminology();
        checkSpecializationDepth();
    }

    private void checkSpecializationDepth() {
        if(archetype.getParentArchetypeId() != null) {
            //parent does NOT need to be flat
            Archetype parent = repository.getArchetype(archetype.getParentArchetypeId());
            if(parent != null) {
                int parentNodeIdSpecialisationDepth = parent.getDefinition().specialisationDepth();
                int nodeIdSpecialisationDepth = archetype.getDefinition().specialisationDepth();
                if(parentNodeIdSpecialisationDepth != nodeIdSpecialisationDepth -1) {
                    addMessage(ErrorType.VACSD,"The specialisation depth of the archetypes must be one greater than the specialisation depth of the parent archetype");
                }
            }
        }
    }

    private void checkMissingTerminology() {
        //missing mandatory parts are checked in grammar, but check here as well
        if(archetype.getTerminology() == null) {
            addMessage(ErrorType.STCNT, "archetype terminology missing");
        } else if(archetype.getTerminology().getTermDefinitions().isEmpty()) {
            addMessage(ErrorType.STCNT,"archetype terminology contains no term definitions");
        }
    }

    private void checkIdCodeSpecialisationLevel() {
        int depth = ValidationUtils.getSpecializationDepth(archetype, repository);
        if(depth != archetype.getDefinition().specialisationDepth()) {
            addMessage(ErrorType.VARCN);
        }
        if(!archetype.getDefinition().getNodeId().matches("id1(.1)*")) {
            addMessage(ErrorType.VARCN, archetype.getDefinition().getNodeId());
        }
    }



    private void checkRmRootType() {
        if(!Objects.equals(archetype.getArchetypeId().getRmClass(), archetype.getDefinition().getRmTypeName())) {
            addMessage(ErrorType.VARDT, String.format("RM type in id %s does not match RM type in definition %s",
                            archetype.getArchetypeId().getConceptId(),
                            archetype.getDefinition().getRmTypeName()));
        }
    }
}
