package com.nedap.archie.rm.generic;

import com.nedap.archie.rm.datavalues.DvCodedText;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 08/07/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="PARTY_RELATED")
public class PartyRelated extends PartyIdentified {
    private DvCodedText relationship;

    public DvCodedText getRelationship() {
        return relationship;
    }

    public void setRelationship(DvCodedText relationship) {
        this.relationship = relationship;
    }
}
