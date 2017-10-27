package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.flattener.ArchetypeRepository;

import java.util.regex.Pattern;

public class ValidationUtils {

    private static Pattern idCodePAttern = Pattern.compile("(id|at|ac)(0|[1-9][0-9]*)(\\.(0|[1-9][0-9]*))*");

    public static int getSpecializationDepth(Archetype archetype, ArchetypeRepository repository) {
        int depth = 0;
        Archetype currentArchetype = archetype;
        while(currentArchetype != null && currentArchetype.getParentArchetypeId() != null) {
            currentArchetype = repository.getArchetype(currentArchetype.getParentArchetypeId());
            depth++;
        }
        return depth;
    }

    public static boolean isValidIdCode(String code) {
        return idCodePAttern.matcher(code).matches();
    }
}
