package com.nedap.archie.query;

/**
 * Created by pieter.bos on 18/04/16.
 */
public class RMObjectWithPath {

    private Object object;
    private String path;

    public RMObjectWithPath(Object object, String path) {
        this.object = object;
        this.path = path;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
