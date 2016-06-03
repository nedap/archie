package com.nedap.archie.rm.archetypes;

import com.nedap.archie.rm.datavalues.DvEHRURI;
import com.nedap.archie.rm.datavalues.DvText;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "LINK")
public class Link {

    private DvText meaning;
    private DvText type;
    private DvEHRURI target;

    public DvText getMeaning() {
        return meaning;
    }

    public void setMeaning(DvText meaning) {
        this.meaning = meaning;
    }

    public DvText getType() {
        return type;
    }

    public void setType(DvText type) {
        this.type = type;
    }

    public DvEHRURI getTarget() {
        return target;
    }

    public void setTarget(DvEHRURI target) {
        this.target = target;
    }
}
