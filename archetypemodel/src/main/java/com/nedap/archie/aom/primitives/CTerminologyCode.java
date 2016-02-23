package com.nedap.archie.aom.primitives;

import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.base.terminology.TerminologyCode;
import com.nedap.archie.rm.datatypes.CodePhrase;

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

}
