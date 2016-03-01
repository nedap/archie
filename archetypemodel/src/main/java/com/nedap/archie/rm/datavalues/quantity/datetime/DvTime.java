package com.nedap.archie.rm.datavalues.quantity.datetime;

import com.nedap.archie.rm.datavalues.SingleValuedDataValue;

import java.time.LocalTime;
import java.time.temporal.Temporal;

/**
 * * TODO: implement java.time.Temporal for this
 *
 * Deviation from the standard: the standard uses a String to represent a value here.
 * We do not, we use the java time types. Perhaps we will add a parser later.
 *
 * Created by pieter.bos on 04/11/15.
 */
public class DvTime extends DvTemporal<Double> implements SingleValuedDataValue<Temporal> {

    private Temporal value;

    @Override
    public void setValue(Temporal value) {
        this.value = value;
    }

    @Override
    public Temporal getValue() {
        return value;
    }

    @Override
    public Double getMagnitude() {
        return value == null ? null : (double) LocalTime.from(value).toSecondOfDay();
    }

    @Override
    public void setMagnitude(Double magnitude) {
        if(magnitude == null) {
            value = null;
        } else {
            value = LocalTime.ofSecondOfDay(Math.round(magnitude));
        }
    }
}
