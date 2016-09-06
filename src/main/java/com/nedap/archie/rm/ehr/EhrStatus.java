package com.nedap.archie.rm.ehr;

/**
 * Created by pieter.bos on 08/07/16.
 */

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.generic.PartySelf;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="EHR_STATUS")
public class EhrStatus extends Locatable {

    private PartySelf subject;
    private boolean isQueryable;
    private boolean isModifiable;
    @Nullable
    private ItemStructure otherDetails;

    public PartySelf getSubject() {
        return subject;
    }

    public void setSubject(PartySelf subject) {
        this.subject = subject;
    }

    public boolean isQueryable() {
        return isQueryable;
    }

    public void setQueryable(boolean queryable) {
        isQueryable = queryable;
    }

    public boolean isModifiable() {
        return isModifiable;
    }

    public void setModifiable(boolean modifiable) {
        isModifiable = modifiable;
    }

    @Nullable
    public ItemStructure getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(@Nullable ItemStructure otherDetails) {
        this.otherDetails = otherDetails;
        setThisAsParent(otherDetails, "other_details");
    }
}
