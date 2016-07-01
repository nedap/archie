package com.nedap.archie.rm.datavalues.quantity.datetime;

import com.nedap.archie.rm.datavalues.quantity.DvAbsoluteQuantity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 01/03/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_TEMPORAL")
public abstract class DvTemporal<MagnitudeType extends Comparable> extends DvAbsoluteQuantity<DvDuration, MagnitudeType> {

    private DvDuration accuracy;

    @Override
    public DvDuration getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(DvDuration accuracy) {
        this.accuracy = accuracy;
    }
}
