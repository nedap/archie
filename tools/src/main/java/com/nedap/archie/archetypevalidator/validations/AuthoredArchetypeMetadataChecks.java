package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.AuthoredArchetype;
import com.nedap.archie.archetypevalidator.ArchetypeValidation;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.flattener.ArchetypeRepository;

import java.util.ArrayList;
import java.util.List;

public class AuthoredArchetypeMetadataChecks implements ArchetypeValidation {
    @Override
    public List<ValidationMessage> validate(Archetype archetype, Archetype flatParent, ArchetypeRepository repository) {
        List<ValidationMessage> result = new ArrayList<>();
        if(archetype instanceof AuthoredArchetype) {
            AuthoredArchetype authoredArchetype = (AuthoredArchetype) archetype;

            checkOriginalLanguagePresent(authoredArchetype, result);
            checkAdlRmVersionIdFormats(authoredArchetype, result);
            checkLanguagesInTranslationAreInTerminology(authoredArchetype, result);
        }
        return result;
    }

    private void checkLanguagesInTranslationAreInTerminology(AuthoredArchetype archetype, List<ValidationMessage> result) {
        if(archetype.getTranslations() != null) {
            for(String language:archetype.getTranslations().keySet()) {
                if(archetype.getTerminology().getTermDefinitions().get(language) == null) {
                    result.add(new ValidationMessage(ErrorType.VOTM, null,
                            String.format("language %s defined in translations, but not defined in terminology", language)));
                }
            }
        }
    }

    private void checkAdlRmVersionIdFormats(AuthoredArchetype archetype, List<ValidationMessage> result) {
        if(!isValidVersion(archetype.getRmRelease())) {
            result.add(new ValidationMessage(ErrorType.VARRV, null,
                    String.format("rm release version %s is not valid", archetype.getRmRelease())));
        }
        if(!isValidVersion(archetype.getAdlVersion())) {
            result.add(new ValidationMessage(ErrorType.VARAV, null,
                    String.format("adl version %s is not valid", archetype.getAdlVersion())));
        }
    }

    private void checkOriginalLanguagePresent(AuthoredArchetype archetype, List<ValidationMessage> result) {
        if(archetype.getOriginalLanguage() != null) {
            String languageCode = archetype.getOriginalLanguage().getCodeString();;
            if(languageCode != null) {
                if(archetype.getTerminology().getTermDefinitions().get(languageCode) == null) {
                    result.add(new ValidationMessage(ErrorType.VOLT, null,
                            String.format("original language %s not defined in terminology", languageCode)));
                }
            }
        }
    }

    private boolean isValidVersion(String version) {
        //from grammar: DIGIT+ '.' DIGIT+ '.' DIGIT+ ( ( '-rc' | '-alpha' ) ( '.' DIGIT+ )? )? ;
        return version != null && version.matches("\\d+\\.\\d+\\.\\d+((-rc|-alpha)(\\.\\d+)?)?");
    }
}
