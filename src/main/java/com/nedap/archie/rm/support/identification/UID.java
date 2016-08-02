package com.nedap.archie.rm.support.identification;

import com.nedap.archie.rm.RMObject;

/**
 * Created by pieter.bos on 08/07/16.
 */
public abstract class UID extends RMObject {

    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
