package com.nedap.archie.rm.datavalues.quantity.datetime;

import com.nedap.archie.rm.datavalues.SingleValuedDataValue;
import com.nedap.archie.rm.datavalues.quantity.DvAbsoluteQuantity;

/**
 * TODO: implement java.time.Temporal for this
 * TODO: implement java.time.Temporal for this
 * Created by pieter.bos on 04/11/15.
 */
public class DvDate extends DvAbsoluteQuantity<Long> implements SingleValuedDataValue<String> {

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
