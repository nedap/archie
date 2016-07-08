package com.nedap.archie.rm.datatypes;

import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.support.identification.ObjectId;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OBJECT_REF", propOrder = {
        "id",
        "namespace",
        "type"
})
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
