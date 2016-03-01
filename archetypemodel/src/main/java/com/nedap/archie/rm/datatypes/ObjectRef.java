package com.nedap.archie.rm.datatypes;

import com.nedap.archie.rm.RMObject;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class ObjectRef extends RMObject {
    private String namespace;
    private String type;
    private ObjectId id;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
