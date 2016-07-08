package com.nedap.archie.rm.demographic;

import com.nedap.archie.rm.datatypes.PartyRef;
import com.nedap.archie.rm.datavalues.quantity.DvInterval;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDate;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by pieter.bos on 08/07/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ROLE")
public class Role extends Party {

    @Nullable
    @XmlElement(name="time_validity")
    private DvInterval<DvDate> timeValidity;
    private PartyRef performer;
    @Nullable
    private List<Capability> capabilities;

    @Nullable
    public DvInterval<DvDate> getTimeValidity() {
        return timeValidity;
    }

    public void setTimeValidity(@Nullable DvInterval<DvDate> timeValidity) {
        this.timeValidity = timeValidity;
    }

    public PartyRef getPerformer() {
        return performer;
    }

    public void setPerformer(PartyRef performer) {
        this.performer = performer;
    }

    @Nullable
    public List<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(@Nullable List<Capability> capabilities) {
        this.capabilities = capabilities;
        this.setThisAsParent(capabilities, "capabilities");
    }

    public void addCapability(Capability capability) {
        this.capabilities.add(capability);
        this.setThisAsParent(capability, "capabilities");
    }
}
