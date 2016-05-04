package com.nedap.archie.rm.datatypes;

import com.nedap.archie.rm.RMObject;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 01/03/16.
 */
@XmlType(name = "OBJECT_ID", propOrder = {
        "value"
})
public class ObjectId extends RMObject {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
