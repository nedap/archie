package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;

import java.util.ArrayList;
import java.util.List;

public class CodeValidation extends ValidatingVisitor {

    @Override
    public List<ValidationMessage> validate(CPrimitiveObject cObject) {
        //validate CTerminology codes
        List<ValidationMessage> result = new ArrayList<>();
        if(cObject instanceof CTerminologyCode) {
            CTerminologyCode terminologyCode = (CTerminologyCode) cObject;

            for(String constraint:terminologyCode.getConstraint()) {
                if(AOMUtils.isValueSetCode(constraint)) {
                    if(!cObject.getArchetype().getTerminology().hasValueSetCode(constraint)) {
                        result.add(new ValidationMessage(ErrorType.VACDF, cObject.path()));
                    }

                } else if (AOMUtils.isValueCode(constraint)) {
                    if(!cObject.getArchetype().getTerminology().hasValueCode(constraint)) {
                        result.add(new ValidationMessage(ErrorType.VATDF, cObject.path()));
                    }
                }
            }
            if(terminologyCode.getAssumedValue() != null) {
                if(!cObject.getArchetype().getTerminology().hasValueCode(terminologyCode.getAssumedValue().getCodeString())) {
                    result.add(new ValidationMessage(ErrorType.VATDF, cObject.path()));
                }
            }
        }
        return result;
    }
}
