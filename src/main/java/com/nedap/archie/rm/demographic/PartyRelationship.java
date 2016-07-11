package com.nedap.archie.rm.demographic;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.support.identification.PartyRef;
import com.nedap.archie.rm.datavalues.quantity.DvInterval;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDate;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 08/07/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="PARTY_RELATIONSHIP")
public class PartyRelationship extends Locatable {

    @Nullable
    private ItemStructure details;
    @Nullable
    @XmlElement(name="time_validity")
    private DvInterval<DvDate> timeValidity;

    private PartyRef source;
    private PartyRef target;

    @Nullable
    public ItemStructure getDetails() {
        return details;
    }

    public void setDetails(@Nullable ItemStructure details) {
        this.details = details;
        setThisAsParent(details, "details");
    }

    @Nullable
    public DvInterval<DvDate> getTimeValidity() {
        return timeValidity;
    }

    public void setTimeValidity(@Nullable DvInterval<DvDate> timeValidity) {
        this.timeValidity = timeValidity;
    }

    public PartyRef getSource() {
        return source;
    }

    public void setSource(PartyRef source) {
        this.source = source;
    }

    public PartyRef getTarget() {
        return target;
    }

    public void setTarget(PartyRef target) {
        this.target = target;
    }
}
