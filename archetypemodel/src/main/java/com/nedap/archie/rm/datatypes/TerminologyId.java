package com.nedap.archie.rm.datatypes;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class TerminologyId {
    private String value;

    public TerminologyId() {

    }

    public TerminologyId(String terminologyId, String terminologyVersion) {
        value = terminologyId + "(" + terminologyVersion + ")";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
