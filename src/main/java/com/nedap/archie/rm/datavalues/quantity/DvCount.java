package com.nedap.archie.rm.datavalues.quantity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DV_COUNT")
public class DvCount extends DvAmount<Long> {

    @XmlElement(type=Long.class)
    @Override
    public Long getMagnitude() {
        return super.getMagnitude();
    }

    @Override
    public void setMagnitude(Long magnitude) {
        super.setMagnitude(magnitude);
    }
}
