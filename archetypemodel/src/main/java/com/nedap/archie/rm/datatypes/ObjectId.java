package com.nedap.archie.rm.datatypes;

import com.nedap.archie.rm.RMObject;

/**
 * Created by pieter.bos on 01/03/16.
 */
public class ObjectId extends RMObject {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
