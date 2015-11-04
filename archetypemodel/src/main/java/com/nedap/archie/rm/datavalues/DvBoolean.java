package com.nedap.archie.rm.datavalues;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class DvBoolean extends DataValue {

    private boolean value;

    public DvBoolean() {

    }

    public DvBoolean(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
