package com.nedap.archie.rm.datastructures;

import com.nedap.archie.rm.datavalues.DvCodedText;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class Element extends Item {

    private DvCodedText nullFlavour;

    public DvCodedText getNullFlavour() {
        return nullFlavour;
    }

    public void setNullFlavour(DvCodedText nullFlavour) {
        this.nullFlavour = nullFlavour;
    }
}
