package com.nedap.archie.rm.datavalues.quantity;

import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DataValue;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DV_ORDERED", propOrder = {
        "normalRange",
        "otherReferenceRanges",
        "normalStatus"
})
public abstract class DvOrdered<ComparableType> extends DataValue implements Comparable<ComparableType> {

    @Nullable
    private CodePhrase normalStatus;
    @Nullable
    private DvInterval normalRange;

    private List<ReferenceRange> otherReferenceRanges = new ArrayList<>();

    @XmlElement(name = "normal_range")
    public DvInterval getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(DvInterval normalRange) {
        this.normalRange = normalRange;
    }

    @XmlElement(name = "other_reference_ranges")
    public List<ReferenceRange> getOtherReferenceRanges() {
        return otherReferenceRanges;
    }

    public void setOtherReferenceRanges(List<ReferenceRange> otherReferenceRanges) {
        this.otherReferenceRanges = otherReferenceRanges;
    }

    public void addOtherReferenceRange(ReferenceRange range) {
        otherReferenceRanges.add(range);
    }

    @Nullable
    @XmlElement(name = "normal_status")
    public CodePhrase getNormalStatus() {
        return normalStatus;
    }

    public void setNormalStatus(@Nullable CodePhrase normalStatus) {
        this.normalStatus = normalStatus;
    }
}
