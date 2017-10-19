package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.terminology.ValueSet;
import com.nedap.archie.archetypevalidator.ArchetypeValidation;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidationMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public class TerminologyValidation implements ArchetypeValidation {
    @Override
    public List<ValidationMessage> validate(Archetype archetype) {
        List<ValidationMessage> result = new ArrayList<>();
        HashSet<String> codes = gatherCodes(archetype);
        for(String code:codes) {
            for (String language : archetype.getTerminology().getTermDefinitions().keySet()) {
                if(!archetype.getTerminology().getTermDefinitions().get(language).containsKey(code)) {
                    result.add(new ValidationMessage(ErrorType.VTLC, "terminology", "code " + code + " is not present in language " + language));
                }
            }
        }

        return result;
    }

    private HashSet<String> gatherCodes(Archetype archetype) {
        HashSet<String> codes = new HashSet<>();
        for(String language:archetype.getTerminology().getTermDefinitions().keySet()) {
            for(String code:archetype.getTerminology().getTermDefinitions().get(language).keySet()) {
                codes.add(code);
            }
        }
        return codes;
    }
}
