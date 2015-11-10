package com.nedap.archie.rm.datastructures;

import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.SingleValuedDataValue;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class Element extends Item implements SingleValuedDataValue<DataValue> {

    @Nullable
    private DataValue value;

    private DvCodedText nullFlavour;

    public DvCodedText getNullFlavour() {
        return nullFlavour;
    }

    public void setNullFlavour(DvCodedText nullFlavour) {
        this.nullFlavour = nullFlavour;
    }

    @Override
    public DataValue getValue() {
        return value;
    }

    @Override
    public void setValue(DataValue value) {
        this.value = value;
    }
}
