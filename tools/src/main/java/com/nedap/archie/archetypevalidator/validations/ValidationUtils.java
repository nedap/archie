package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.flattener.ArchetypeRepository;

import java.util.regex.Pattern;

public class ValidationUtils {



    public static int getSpecializationDepth(Archetype archetype, ArchetypeRepository repository) {
        int depth = 0;
        Archetype currentArchetype = archetype;
        while(currentArchetype != null && currentArchetype.getParentArchetypeId() != null) {
            currentArchetype = repository.getArchetype(currentArchetype.getParentArchetypeId());
            depth++;
        }
        return depth;
    }


}
