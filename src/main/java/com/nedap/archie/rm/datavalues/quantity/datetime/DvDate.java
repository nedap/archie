package com.nedap.archie.rm.datavalues.quantity.datetime;

import com.nedap.archie.rm.datavalues.SingleValuedDataValue;
import com.nedap.archie.util.jaxbtime.DateXmlAdapter;
import com.nedap.archie.util.jaxbtime.TimeXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;

/**
 * TODO: implement java.time.Temporal for this
 * TODO: implement java.time.Temporal for this
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DV_DATE", propOrder = {
        "value"
})
public class DvDate extends DvTemporal<Long> implements SingleValuedDataValue<Temporal> {
    //TODO: in XML this should be a string probably
    private Temporal value;

    @Override
    @XmlElements({
            @XmlElement(type=LocalDate.class),
            @XmlElement(type=YearMonth.class),
            @XmlElement(type=Year.class)

    })
    @XmlJavaTypeAdapter(DateXmlAdapter.class)
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
    @XmlTransient
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
