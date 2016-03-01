package com.nedap.archie.rm.datatypes;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class LocatableRef extends ObjectRef {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
