package com.nedap.archie.rm.support.identification;

/**
 * Created by pieter.bos on 08/07/16.
 */
public class VersionTreeId extends UIDBasedId {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
