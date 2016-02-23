package com.nedap.archie.aom.primitives;

import com.nedap.archie.aom.CDefinedObject;
import com.nedap.archie.aom.CPrimitiveObject;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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
                if(value.matches(constraint.substring(1).substring(0, constraint.length()-1))) {
                    return true;
                }
            } else {
                //TODO: does case matter here?
                return Objects.equals(value, constraint);
            }
        }
        return false;
    }
}
