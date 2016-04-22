package com.nedap.archie.aom.primitives;

import com.nedap.archie.ArchieLanguageConfiguration;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import com.nedap.archie.aom.terminology.TerminologyCodeWithArchetypeTerm;
import com.nedap.archie.aom.terminology.ValueSet;
import com.nedap.archie.base.terminology.TerminologyCode;
import com.nedap.archie.rm.datatypes.CodePhrase;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by pieter.bos on 15/10/15.
 */
public class CTerminologyCode extends CPrimitiveObject<String, TerminologyCode> {

    public boolean isValidValue(TerminologyCode value) {
        return isValidValueCodePhrase(value);
    }

    public boolean isValidValue(CodePhrase value) {
        return isValidValueCodePhrase(value);
    }

    private boolean isValidValueCodePhrase(CodePhrase value) {
        if(getConstraint().isEmpty()) {
            return true;
        }
        for(String constraint:getConstraint()) {
            if(constraint.startsWith("at")) {
                if(value.getCodeString() != null && value.getCodeString().equals(constraint)) {
                    return true;
                }
            } else if (constraint.startsWith("ac")) {
                if(value.getTerminologyId() != null && value.getTerminologyId().getValue().equals(constraint)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Get the ArchetypeTerms in the selected meaning and description language for all the possible options if this is a
     * locally defined terminology.
     * See the ArchieLanguageConfiguration for the language settings.
     *
     * @return
     */
    public List<TerminologyCodeWithArchetypeTerm> getTerms() {
        List<TerminologyCodeWithArchetypeTerm> result = new ArrayList<>();
        Archetype archetype = getArchetype();
        ArchetypeTerminology terminology = archetype.getTerminology(this);
        String language = ArchieLanguageConfiguration.getMeaningAndDescriptionLanguage();
        String defaultLanguage = ArchieLanguageConfiguration.getDefaultMeaningAndDescriptionLanguage();
        for(String constraint:getConstraint()) {
            if(constraint.startsWith("at")) {
                ArchetypeTerm termDefinition = terminology.getTermDefinition(language, constraint);
                if(termDefinition != null) {
                    result.add(new TerminologyCodeWithArchetypeTerm(constraint, termDefinition));
                }
            } else if (constraint.startsWith("ac")) {
                ValueSet ac = terminology.getValueSets().get(constraint);
                if(ac != null) {
                    for(String member:ac.getMembers()) {
                        ArchetypeTerm termDefinition = terminology.getTermDefinition(language, member);
                        if(termDefinition == null) {
                            termDefinition = terminology.getTermDefinition(defaultLanguage, member);
                        }
                        if(termDefinition != null) {
                            result.add(new TerminologyCodeWithArchetypeTerm(constraint, termDefinition));
                        }
                    }
                }
            }
        }
        return result;
    }

}
