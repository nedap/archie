package com.nedap.archie.rm.datavalues;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class DvBoolean extends DataValue implements SingleValuedDataValue<Boolean>{

    private Boolean value;

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
