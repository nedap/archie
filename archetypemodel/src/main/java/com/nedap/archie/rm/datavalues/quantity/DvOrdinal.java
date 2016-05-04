package com.nedap.archie.rm.datavalues.quantity;

import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.SingleValuedDataValue;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "DV_ORDINAL", propOrder = {
        "value",
        "symbol"
})
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

    public DvCodedText getSymbol() {
        return symbol;
    }

    public void setSymbol(DvCodedText symbol) {
        this.symbol = symbol;
    }
}
