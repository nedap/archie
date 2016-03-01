package com.nedap.archie.rm.datatypes;

import com.nedap.archie.rm.RMObject;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 * TODO: move to correct package
 */
public class PartyProxy extends RMObject {

    @Nullable
    private PartyRef externalRef;

    public PartyRef getExternalRef() {
        return externalRef;
    }

    public void setExternalRef(PartyRef externalRef) {
        this.externalRef = externalRef;
    }
}
