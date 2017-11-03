package com.nedap.archie.rm.support.identification;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UID_BASED_ID")
public abstract class UIDBasedId extends ObjectId {

    private UID root;
    @Nullable
    private String extension;

    public UID getRoot() {
        return root;
    }

    public void setRoot(UID root) {
        this.root = root;
    }

    @Nullable
    public String getExtension() {
        return extension;
    }

    public void setExtension(@Nullable String extension) {
        this.extension = extension;
    }
}
