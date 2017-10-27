package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import com.nedap.archie.aom.terminology.ValueSet;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.archetypevalidator.ArchetypeValidation;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.flattener.ArchetypeRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasicTerminologyValidation implements ArchetypeValidation {
    public BasicTerminologyValidation() {
    }

    @Override
    public List<ValidationMessage> validate(Archetype archetype, Archetype flatParent, ArchetypeRepository repository) {
        List<ValidationMessage> result = new ArrayList<>();
        validateFormatAndSpecializationLevelOfCodes(archetype, result);
        validateLanguageConsistency(archetype, result);
        validateTerminologyBindings(archetype, flatParent, result);
        validateValueSets(archetype, flatParent, result);
        return result;
    }

    private void validateFormatAndSpecializationLevelOfCodes(Archetype archetype, List<ValidationMessage> result) {
        int terminologySpecialisationDepth = archetype.getTerminology().specialisationDepth();
        for(Map<String, ArchetypeTerm> languageSpecificTerminology:archetype.getTerminology().getTermDefinitions().values()) {
            for(ArchetypeTerm term:languageSpecificTerminology.values()) {
                if(!ValidationUtils.isValidIdCode(term.getCode())) {
                    result.add(new ValidationMessage(ErrorType.VATCV, null, String.format("id code %s in terminology is not valid", term.getCode())));
                }
                if(archetype.isDifferential()) {
                    if(terminologySpecialisationDepth != AOMUtils.getSpecializationDepthFromCode(term.getCode())) {
                        result.add(new ValidationMessage(ErrorType.VTSD, null, String.format("id code %s in terminology is of a different specialization depth than the archetype", term.getCode())));
                    }
                } else {
                    if(AOMUtils.getSpecializationDepthFromCode(term.getCode()) > terminologySpecialisationDepth) {
                        result.add(new ValidationMessage(ErrorType.VTSD, null, String.format("id code %s in terminology is of a different specialization depth than the archetype", term.getCode())));
                    }
                }
            }
        }

    }

    public void validateLanguageConsistency(Archetype archetype, List<ValidationMessage> result) {
        List<String> codes = archetype.getTerminology().allCodes();
        for(String code:codes) {
            for (String language : archetype.getTerminology().getTermDefinitions().keySet()) {
                if(!archetype.getTerminology().getTermDefinitions().get(language).containsKey(code)) {
                    result.add(new ValidationMessage(ErrorType.VTLC, "terminology", "code " + code + " is not present in language " + language));
                }
            }
        }
    }

    private void validateTerminologyBindings(Archetype archetype, Archetype flatParent, List<ValidationMessage> result) {
        ArchetypeTerminology terminology = archetype.getTerminology();
        Map<String, Map<String, URI>> termBindings = terminology.getTermBindings();
        for(String terminologyId: termBindings.keySet()) {
            for(String constraintCodeOrPath: termBindings.get(terminologyId).keySet()) {
                boolean archetypeHasPath = false;
                try {
                    archetypeHasPath = archetype.hasPath(constraintCodeOrPath);
                } catch (Exception e) {
                    //if not a valid path, fine
                }
                if(!(ValidationUtils.isValidIdCode(constraintCodeOrPath) || archetypeHasPath
                                //TODO: || referenceMOdel.hasPath(path)
                    )) {
                    result.add(new ValidationMessage(ErrorType.VTTBK, String.format("Term binding key %s in path format is not present in archetype", constraintCodeOrPath)));
                }
                if(ValidationUtils.isValidIdCode(constraintCodeOrPath) &&
                        !(terminology.hasCode(constraintCodeOrPath))
                        || (archetype.isSpecialized() && flatParent != null && !flatParent.getTerminology().hasCode(constraintCodeOrPath)))
                    {
                    result.add(new ValidationMessage(ErrorType.VTTBK, String.format("Term binding key %s is not present in terminology", constraintCodeOrPath)));
                }
                //TODO: two warnings
            }
        }
    }

    private void validateValueSets(Archetype archetype, Archetype flatParent, List<ValidationMessage> result) {
        ArchetypeTerminology terminology = archetype.getTerminology();
        for(ValueSet valueSet:terminology.getValueSets().values()){
            if(!terminology.hasValueSetCode(valueSet.getId())) {
                result.add(new ValidationMessage(ErrorType.VTVSID, String.format("value set code %s is not present in terminology", valueSet.getId())));
            }
            for(String value:valueSet.getMembers()) {
                if(!terminology.hasValueCode(value)) {
                    result.add(new ValidationMessage(ErrorType.VTVSMD, String.format("value set code %s is not present in terminology", valueSet.getId())));
                }
            }
            //TODO: we should check for uniqueness, but valueset is a java.util.Set, so there can be no duplicates by definition

        }
    }


}
