package com.nedap.archie.rm.datavalues.quantity;

import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.datavalues.DvText;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class ReferenceRange<T extends DvOrdered> extends RMObject {

    private DvInterval<T> range;
    private DvText meaning;

    public DvInterval<T> getRange() {
        return range;
    }

    public void setRange(DvInterval<T> range) {
        this.range = range;
    }

    public DvText getMeaning() {
        return meaning;
    }

    public void setMeaning(DvText meaning) {
        this.meaning = meaning;
    }
}
