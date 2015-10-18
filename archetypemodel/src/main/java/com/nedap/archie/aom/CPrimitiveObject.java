package com.nedap.archie.aom;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CPrimitiveObject<Constraint, AssumedAndDefaultValue> extends CDefinedObject<AssumedAndDefaultValue> {
    private AssumedAndDefaultValue assumedValue;
    private Boolean enumeratedTypeConstraint;
    private Constraint constraint;

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

    public Constraint getConstraint() {
        return constraint;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }
}
