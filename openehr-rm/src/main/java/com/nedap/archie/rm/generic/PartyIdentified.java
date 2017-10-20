package com.nedap.archie.rm.generic;

import com.nedap.archie.rm.datavalues.DvIdentifier;
import com.nedap.archie.rm.generic.PartyProxy;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 01/03/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PARTY_IDENTIFIED", propOrder = {
        "name",
        "identifiers"
})
public class PartyIdentified extends PartyProxy {
    @Nullable
    private String name;
    @Nullable
    private List<DvIdentifier> identifiers = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DvIdentifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<DvIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    public void addIdentifier(DvIdentifier identifier) {
        identifiers.add(identifier);
    }
}
