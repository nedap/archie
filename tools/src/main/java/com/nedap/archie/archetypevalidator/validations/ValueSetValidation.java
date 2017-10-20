package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import com.nedap.archie.archetypevalidator.ArchetypeValidation;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidationMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public class ValueSetValidation implements ArchetypeValidation {
    @Override
    public List<ValidationMessage> validate(Archetype archetype) {
        List<ValidationMessage> result = new ArrayList<>();

        ArchetypeTerminology terminology = archetype.getTerminology();
        for(String acCode:terminology.getValueSets().keySet()) {
            acCodeMustExist(result, terminology, acCode);
            for(String atCode:terminology.getValueSets().get(acCode).getMembers()) {
                atCodeMustExist(result, terminology, atCode);
            }
        }
        return result;
    }

    public static void acCodeMustExist(List<ValidationMessage> result, ArchetypeTerminology terminology, String acCode) {
        Map<String, Map<String, ArchetypeTerm>> termDefinitions = terminology.getTermDefinitions();
        for(String language: termDefinitions.keySet()) {
            Map<String, ArchetypeTerm> translations = termDefinitions.get(language);
            if(translations.get(acCode) == null) {
                result.add(new ValidationMessage(ErrorType.VTVSID, "value_sets", "code " + acCode + " has no term definition in language " + language));
            }
        }
    }

    public static void atCodeMustExist(List<ValidationMessage> result, ArchetypeTerminology terminology, String atCode) {
        Map<String, Map<String, ArchetypeTerm>> termDefinitions = terminology.getTermDefinitions();
        for(String language: termDefinitions.keySet()) {
            Map<String, ArchetypeTerm> translations = termDefinitions.get(language);
            if(translations.get(atCode) == null) {
                //TODO: this must be in the flattened form, not necessarilty this archetype!
                result.add(new ValidationMessage(ErrorType.VTVSMD, "value_sets", "code " + atCode + " has no term definition in language " + language));
            }
        }
    }
}
