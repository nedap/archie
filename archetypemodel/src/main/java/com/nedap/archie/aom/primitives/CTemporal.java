package com.nedap.archie.aom.primitives;

import java.time.temporal.Temporal;

/**
 * Created by pieter.bos on 15/10/15.
 */
public  class CTemporal<T> extends COrdered<T>{

    private String patternedConstraint;

    public String getPatternedConstraint() {
        return patternedConstraint;
    }

    public void setPatternedConstraint(String patternedConstraint) {
        this.patternedConstraint = patternedConstraint;
    }
}
