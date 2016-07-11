package com.nedap.archie.aom.primitives;

/**
 * Created by pieter.bos on 15/10/15.
 */
public abstract class CTemporal<T> extends COrdered<T>{

    private String patternedConstraint;

    public String getPatternedConstraint() {
        return patternedConstraint;
    }

    public void setPatternedConstraint(String patternedConstraint) {
        this.patternedConstraint = patternedConstraint;
    }

    public boolean isValidValue(T value) {
        if(getConstraint().isEmpty() && patternedConstraint == null) {
            return true;
        }
        if(patternedConstraint == null) {
            return super.isValidValue(value);
        } else {
            //TODO: find a library that validates ISO 8601 patterns
            return true;
        }
    }
}
