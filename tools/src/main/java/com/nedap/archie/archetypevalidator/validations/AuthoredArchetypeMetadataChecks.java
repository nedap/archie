package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.AuthoredArchetype;
import com.nedap.archie.archetypevalidator.ArchetypeValidation;
import com.nedap.archie.archetypevalidator.ArchetypeValidationBase;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.flattener.ArchetypeRepository;
import com.nedap.archie.rminfo.ModelInfoLookup;

import java.util.ArrayList;
import java.util.List;

public class AuthoredArchetypeMetadataChecks extends ArchetypeValidationBase {

    public AuthoredArchetypeMetadataChecks(ModelInfoLookup lookup) {
        super(lookup);
    }
    @Override
    public void validate() {
        if(archetype instanceof AuthoredArchetype) {
            AuthoredArchetype authoredArchetype = (AuthoredArchetype) archetype;

            checkOriginalLanguagePresent();
            checkAdlRmVersionIdFormats();
            checkLanguagesInTranslationAreInTerminology();
        }
    }

    private void checkLanguagesInTranslationAreInTerminology() {
        if(archetype.getTranslations() != null) {
            for(String language:archetype.getTranslations().keySet()) {
                if(archetype.getTerminology().getTermDefinitions().get(language) == null) {
                    addMessage(ErrorType.VOTM, String.format("language %s defined in translations, but not defined in terminology", language));
                }
            }
        }
    }

    private void checkAdlRmVersionIdFormats() {
        if(!isValidVersion(archetype.getRmRelease())) {
            addMessage(ErrorType.VARRV, String.format("rm release version %s is not valid", archetype.getRmRelease()));
        }
        if(!isValidVersion(archetype.getAdlVersion())) {
            addMessage(ErrorType.VARAV, String.format("adl version %s is not valid", archetype.getAdlVersion()));
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
        }
    }

    private boolean isValidVersion(String version) {
        //from grammar: DIGIT+ '.' DIGIT+ '.' DIGIT+ ( ( '-rc' | '-alpha' ) ( '.' DIGIT+ )? )? ;
        return version != null && version.matches("\\d+\\.\\d+\\.\\d+((-rc|-alpha)(\\.\\d+)?)?");
    }
}
