package com.nedap.archie.rm.datatypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TERMINOLOGY_ID")
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
