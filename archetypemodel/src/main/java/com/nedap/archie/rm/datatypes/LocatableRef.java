package com.nedap.archie.rm.datatypes;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "LOCATABLE_REF", propOrder = {
        "path"
})
public class LocatableRef extends ObjectRef {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
