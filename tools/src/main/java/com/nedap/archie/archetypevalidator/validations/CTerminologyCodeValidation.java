package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 03/04/2017.
 */
public class CTerminologyCodeValidation extends ValidatingVisitor {

    @Override
    public List<ValidationMessage> validate(CPrimitiveObject cObject) {
        List<ValidationMessage> result = new ArrayList<>();
        if(cObject instanceof CTerminologyCode) {
            CTerminologyCode terminologyCode = (CTerminologyCode) cObject;
            for(String constraint:terminologyCode.getConstraint()) {
                if(constraint.startsWith("ac")) {
                    ValueSetValidation.acCodeMustExist(result, cObject.getArchetype().getTerminology(), constraint);
                } else if (constraint.startsWith("at")) {
                    ValueSetValidation.atCodeMustExist(result, cObject.getArchetype().getTerminology(), constraint);
                }
            }
        }
        return result;
    }

}
