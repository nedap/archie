package com.nedap.archie.rm.datavalues.quantity.datetime;

import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.SingleValuedDataValue;
import com.nedap.archie.rm.datavalues.quantity.DvAbsoluteQuantity;

/**
 * TODO: implement java.time.Temporal for this
 * Created by pieter.bos on 04/11/15.
 */
public class DvDateTime extends DvAbsoluteQuantity<Double> implements SingleValuedDataValue<String> {

    private String value;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
