package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.rminfo.ModelInfoLookup;

import java.util.ArrayList;
import java.util.List;

public class CodeValidation extends ValidatingVisitor {

    public CodeValidation(ModelInfoLookup lookup) {
        super(lookup);
    }

    @Override
    public void validate(CPrimitiveObject cObject) {
        //validate CTerminology codes
        if(cObject instanceof CTerminologyCode) {
            CTerminologyCode terminologyCode = (CTerminologyCode) cObject;

            for(String constraint:terminologyCode.getConstraint()) {
                if(AOMUtils.isValueSetCode(constraint)) {
                    if(!cObject.getArchetype().getTerminology().hasValueSetCode(constraint)) {
                        addMessageWithPath(ErrorType.VACDF, cObject.path());
                    }

                } else if (AOMUtils.isValueCode(constraint)) {
                    if(!cObject.getArchetype().getTerminology().hasValueCode(constraint)) {
                        addMessageWithPath(ErrorType.VATDF, cObject.path());
                    }
                }
            }
            if(terminologyCode.getAssumedValue() != null) {
                if(!cObject.getArchetype().getTerminology().hasValueCode(terminologyCode.getAssumedValue().getCodeString())) {
                    addMessageWithPath(ErrorType.VATDF, cObject.path());
                }
            }
        }
    }
}
