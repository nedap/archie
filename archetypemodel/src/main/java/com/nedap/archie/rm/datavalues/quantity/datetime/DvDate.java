package com.nedap.archie.rm.datavalues.quantity.datetime;

import com.nedap.archie.rm.datavalues.SingleValuedDataValue;
import com.nedap.archie.rm.datavalues.quantity.DvAbsoluteQuantity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * TODO: implement java.time.Temporal for this
 * TODO: implement java.time.Temporal for this
 * Created by pieter.bos on 04/11/15.
 */
public class DvDate extends DvTemporal<Long> implements SingleValuedDataValue<Temporal> {

    private Temporal value;

    @Override
    public Temporal getValue() {
        return value;
    }

    @Override
    public void setValue(Temporal value) {
        if(value.isSupported(ChronoField.HOUR_OF_DAY)) {
            //TODO: do we really need this validation?
            throw new IllegalArgumentException("value must only have a year, month or date, but this supports hours: " + value);
        }
        this.value = value;
    }

    @Override
    public Long getMagnitude() {
        return value == null ? null : (long) LocalDate.from(value).toEpochDay();
    }

    @Override
    public void setMagnitude(Long magnitude) {
        if(magnitude == null) {
            value = null;
        } else {
            value = LocalDate.ofEpochDay(magnitude);
        }
    }
}
