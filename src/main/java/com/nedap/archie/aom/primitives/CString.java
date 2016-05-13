package com.nedap.archie.aom.primitives;

import com.nedap.archie.aom.CPrimitiveObject;

import java.util.Objects;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CString extends CPrimitiveObject<String, String> {

    public boolean isValidValue(String value) {
        if(getConstraint().isEmpty()) {
            return true;
        }
        for(String constraint:getConstraint()) {
            if(constraint.length() > 1 &&
                    ((constraint.startsWith("/") && constraint.endsWith("/")) ||
                            (constraint.startsWith("^") && constraint.endsWith("^")))) {
                //regexp. Strip first and last character and match. If you want to input
                //data starting and ending with '/', you cannot in the AOM, although ADL lets you express if just fine.
                //perhaps we should make the constraint object something more expressive than a String?
                if(value.matches(constraint.substring(1).substring(0, constraint.length()-2))) {
                    return true;
                }
            } else {
                //TODO: does case matter here?
                if(Objects.equals(value, constraint)) {
                    return true;
                }
            }
        }
        return false;
    }
}
