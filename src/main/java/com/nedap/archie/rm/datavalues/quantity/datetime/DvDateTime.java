package com.nedap.archie.rm.datavalues.quantity.datetime;

import com.nedap.archie.rm.datavalues.SingleValuedDataValue;
import com.nedap.archie.util.jaxbtime.DateTimeXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DV_DATE_TIME", propOrder = {
        "value", "magnitude"
})
public class DvDateTime extends DvTemporal<Long> implements SingleValuedDataValue<TemporalAccessor> {

    private TemporalAccessor value;

    @Override
    @XmlElements({
            @XmlElement(type = OffsetDateTime.class),
            @XmlElement(type = LocalDateTime.class)
    })
    @XmlJavaTypeAdapter(DateTimeXmlAdapter.class)
    public TemporalAccessor getValue() {
        return value;
    }

    @Override
    public void setValue(TemporalAccessor value) {
        this.value = value;
    }

    @Override
    //@XmlTransient
    @XmlElement(type=Long.class)
    //@XmlElement(name="magnitude")
    public Long getMagnitude() {
        if(value == null) {
            return null;
        }
        if(value.query(TemporalQueries.zone()) != null) {
            return ZonedDateTime.from(value).toEpochSecond();
        } else {
            return LocalDateTime.from(value).toEpochSecond(ZoneOffset.UTC);
        }
    }

    @Override
    public void setMagnitude(Long magnitude) {
        if(magnitude == null) {
            value = null;
        } else {
            value = LocalDateTime.ofEpochSecond(magnitude, 0, ZoneOffset.UTC);
        }
    }
}
