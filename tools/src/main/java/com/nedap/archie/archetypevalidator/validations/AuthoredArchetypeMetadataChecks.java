package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.AuthoredArchetype;
import com.nedap.archie.aom.ResourceDescriptionItem;
import com.nedap.archie.aom.TranslationDetails;
import com.nedap.archie.archetypevalidator.ArchetypeValidationBase;
import com.nedap.archie.archetypevalidator.ErrorType;

import java.util.Objects;

public class AuthoredArchetypeMetadataChecks extends ArchetypeValidationBase {

    public AuthoredArchetypeMetadataChecks() {
        super();
    }
    @Override
    public void validate() {
        if(archetype instanceof AuthoredArchetype) {
            AuthoredArchetype authoredArchetype = (AuthoredArchetype) archetype;

            checkOriginalLanguagePresent();
            checkAdlRmVersionIdFormats();
            validateDescription();
            if(this.hasPassed()) {
                checkLanguagesInTranslationAreInTerminology();
            }

        }
    }

    private void validateDescription() {
        if(archetype.getDescription().getDetails() != null) {
            for(String language:archetype.getDescription().getDetails().keySet()) {
                ResourceDescriptionItem resourceDescriptionItem = archetype.getDescription().getDetails().get(language);
                if(!Objects.equals(language, resourceDescriptionItem.getLanguage().getCodeString())){
                    addMessage(ErrorType.VRDLA, String.format("resource description language %s has its key set wrong: %s", language, resourceDescriptionItem.getLanguage().getCodeString()));
                }

            }
        }
    }

    private void checkLanguagesInTranslationAreInTerminology() {
        if(archetype.getTranslations() != null) {
            for(String language:archetype.getTranslations().keySet()) {
                TranslationDetails translationDetails = archetype.getTranslations().get(language);
                if(translationDetails.getLanguage() == null || !language.equals(translationDetails.getLanguage().getCodeString())) {
                    addMessage(ErrorType.VTRLA, String.format("key for language % is wrong: %s", language, translationDetails.getLanguage().getCodeString()));
                }
                //check if also defined in terminology
                if(archetype.getTerminology().getTermDefinitions().get(language) == null) {
                    addMessage(ErrorType.VOTM, String.format("language %s defined in translations, but not defined in terminology", language));
                }
            }
        }
    }

    private void checkAdlRmVersionIdFormats() {
        if(!isValidVersion(archetype.getAdlVersion())) {
            addMessage(ErrorType.VARAV, String.format("adl version %s is not valid", archetype.getAdlVersion()));
        }
        if(!isValidVersion(archetype.getRmRelease())) {
            addMessage(ErrorType.VARRV, String.format("rm release version %s is not valid", archetype.getRmRelease()));
        }
    }

    private void checkOriginalLanguagePresent() {
        if(archetype.getOriginalLanguage() != null) {
            String languageCode = archetype.getOriginalLanguage().getCodeString();;
            if(languageCode != null) {
                if(archetype.getTerminology().getTermDefinitions().get(languageCode) == null) {
                    addMessage(ErrorType.VOLT, String.format("original language %s not defined in terminology", languageCode));
                }
            }
        } else {
            addMessage(ErrorType.VDEOL);
        }
    }

    private boolean isValidVersion(String version) {
        //from grammar: DIGIT+ '.' DIGIT+ '.' DIGIT+ ( ( '-rc' | '-alpha' ) ( '.' DIGIT+ )? )? ;
        return version != null && version.matches("\\d+\\.\\d+\\.\\d+((-rc|-alpha)(\\.\\d+)?)?");
    }
}
