package com.nedap.archie.rm.datavalues.quantity;

import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DataValue;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
public abstract class DvOrdered<ComparableType> extends DataValue implements Comparable<ComparableType> {

    @Nullable
    private CodePhrase normalStatus;
    @Nullable
    private DvInterval normalRange;
    private List<ReferenceRange> otherReferenceRanges = new ArrayList<>();

    public DvInterval getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(DvInterval normalRange) {
        this.normalRange = normalRange;
    }

    public List<ReferenceRange> getOtherReferenceRanges() {
        return otherReferenceRanges;
    }

    public void setOtherReferenceRanges(List<ReferenceRange> otherReferenceRanges) {
        this.otherReferenceRanges = otherReferenceRanges;
    }

    public void addOtherReferenceRange(ReferenceRange range) {
        otherReferenceRanges.add(range);
    }
}
