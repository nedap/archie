package com.nedap.archie.rm.datavalues.quantity.datetime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nedap.archie.json.TimeDeserializer;
import com.nedap.archie.rm.datavalues.SingleValuedDataValue;
import com.nedap.archie.xml.adapters.TimeXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;

/**
 * TODO: implement java.time.Temporal for this object?
 * <p>
 * Deviation from the standard: the standard uses a String to represent a value here.
 * We do not, we use the java time types. Perhaps we will add a parser later.
 * possible issue: people constraining the String field of DvTime directly instead of using a Ctime. Ask people if this is an issue.
 * <p>
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_TIME", propOrder = {
        "value"
})
public class DvTime extends DvTemporal<Double> implements SingleValuedDataValue<TemporalAccessor> {

    @XmlJavaTypeAdapter(TimeXmlAdapter.class)
    private TemporalAccessor value;

    @Override
    public void setValue(TemporalAccessor value) {
        this.value = value;
    }

    @Override
//    @XmlElements({
//            @XmlElement(type=OffsetTime.class),
//            @XmlElement(type=LocalTime.class)
//    })    
    @JsonDeserialize(using=TimeDeserializer.class)
    public TemporalAccessor getValue() {
        return value;
    }

    @Override
    public Double getMagnitude() {
        return value == null ? null : (double) LocalTime.from(value).toSecondOfDay();
    }

    public void setMagnitude(Double magnitude) {
        if(magnitude == null) {
            value = null;
        } else {
            value = LocalTime.ofSecondOfDay(Math.round(magnitude));
        }
    }
}
