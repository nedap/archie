package com.nedap.archie.aom;

/**
 * Defined Object. Parameterized so the default value methods can be overridden with a different type
 * Created by pieter.bos on 15/10/15.
 */
public class CDefinedObject<T> extends CObject {

    private Boolean frozen;
    private T defaultValue;

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
