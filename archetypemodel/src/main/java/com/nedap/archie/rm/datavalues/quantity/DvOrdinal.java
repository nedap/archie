package com.nedap.archie.rm.datavalues.quantity;

import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.SingleValuedDataValue;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class DvOrdinal extends DvOrdered<DvOrdinal> implements SingleValuedDataValue<Long>, Comparable<DvOrdinal> {

    private DvCodedText symbol;
    private Long value;

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public int compareTo(DvOrdinal o) {
        return value.compareTo(o.value);
    }
}
