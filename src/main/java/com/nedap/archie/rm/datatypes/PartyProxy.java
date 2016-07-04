package com.nedap.archie.rm.datatypes;

import com.nedap.archie.rm.RMObject;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 * TODO: move to correct package
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PARTY_PROXY", propOrder = {
        "externalRef"
})
public class PartyProxy extends RMObject {

    @Nullable
    @XmlElement(name = "external_ref")
    private PartyRef externalRef;
    
    public PartyRef getExternalRef() {
        return externalRef;
    }

    public void setExternalRef(PartyRef externalRef) {
        this.externalRef = externalRef;
    }
}
