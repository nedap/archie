package com.nedap.archie.aom;

/**
 * Defined Object. Parameterized so we don't have to do instanceof as much
 * Created by pieter.bos on 15/10/15.
 */
public class CDefinedObject<T> extends CObject {

    Boolean frozen;
    T defaultValue;

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }
}
