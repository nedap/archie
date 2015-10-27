package com.nedap.archie.rules;

/**
 * Created by pieter.bos on 27/10/15.
 */
public class ModelReference extends Leaf {

    private String path;

    public ModelReference() {

    }

    public ModelReference(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
