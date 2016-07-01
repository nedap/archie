package com.nedap.archie.rm.datavalues.quantity.datetime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nedap.archie.rm.datavalues.SingleValuedDataValue;
import com.nedap.archie.rm.datavalues.quantity.DvAmount;
import com.nedap.archie.json.DurationDeserializer;
import com.nedap.archie.xml.DurationXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

/**
 * TODO: magnitude of duration is not defined properly
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_DURATION", propOrder = {
        "value"
})
public class DvDuration extends DvAmount<Long> implements SingleValuedDataValue<TemporalAmount> {

    @XmlJavaTypeAdapter(DurationXmlAdapter.class)
    private TemporalAmount value;

    @Override
//    @XmlElements({
//            @XmlElement(type=Period.class),
//            @XmlElement(type=Duration.class)
//    })    
    @JsonDeserialize(using=DurationDeserializer.class)
    public TemporalAmount getValue() {
        return value;
    }

    @Override
    public void setValue(TemporalAmount value) {
        this.value = value;
    }

    @XmlTransient
    @Override
    public Long getMagnitude() {
        return null; //no magnitude defined in spec
    }

}
