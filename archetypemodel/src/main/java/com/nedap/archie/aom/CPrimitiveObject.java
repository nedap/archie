package com.nedap.archie.aom;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CPrimitiveObject<T> extends CDefinedObject<T> {
    private T assumedValue;
    private Boolean enumeratedTypeConstraint;
    private T constraint;

    public T getAssumedValue() {
        return assumedValue;
    }

    public void setAssumedValue(T assumedValue) {
        this.assumedValue = assumedValue;
    }

    public Boolean getEnumeratedTypeConstraint() {
        return enumeratedTypeConstraint;
    }

    public void setEnumeratedTypeConstraint(Boolean enumeratedTypeConstraint) {
        this.enumeratedTypeConstraint = enumeratedTypeConstraint;
    }

    public T getConstraint() {
        return constraint;
    }

    public void setConstraint(T constraint) {
        this.constraint = constraint;
    }
}
