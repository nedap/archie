package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CPrimitiveObject<Constraint, AssumedAndDefaultValue> extends CDefinedObject<AssumedAndDefaultValue> {
    private AssumedAndDefaultValue assumedValue;
    private Boolean enumeratedTypeConstraint;
    private List<Constraint> constraints = new ArrayList<>();

    public AssumedAndDefaultValue getAssumedValue() {
        return assumedValue;
    }

    public void setAssumedValue(AssumedAndDefaultValue assumedValue) {
        this.assumedValue = assumedValue;
    }

    public Boolean getEnumeratedTypeConstraint() {
        return enumeratedTypeConstraint;
    }

    public void setEnumeratedTypeConstraint(Boolean enumeratedTypeConstraint) {
        this.enumeratedTypeConstraint = enumeratedTypeConstraint;
    }

    public List<Constraint> getConstraint() {
        return constraints;
    }

    public void setConstraint(List<Constraint> constraint) {
        this.constraints = constraint;
    }

    public void addConstraint(Constraint constraint) {
        this.constraints.add(constraint);
    }
}

