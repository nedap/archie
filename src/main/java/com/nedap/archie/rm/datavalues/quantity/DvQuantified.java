package com.nedap.archie.rm.datavalues.quantity;


import com.nedap.archie.rm.datavalues.quantity.datetime.DvTemporal;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_QUANTIFIED", propOrder = {
        "magnitudeStatus"
})
@XmlSeeAlso({
        DvTemporal.class,
        DvAmount.class
})
public abstract class DvQuantified<AccuracyType, MagnitudeType extends Comparable> extends DvOrdered<MagnitudeType> {

    @Nullable
        @XmlElement(name = "magnitude_status")
    private String magnitudeStatus;

    @Nullable
    public String getMagnitudeStatus() {
        return magnitudeStatus;
    }

    public void setMagnitudeStatus(@Nullable String magnitudeStatus) {
        this.magnitudeStatus = magnitudeStatus;
    }

    public abstract AccuracyType getAccuracy();

    public abstract MagnitudeType getMagnitude();

    @Override
    public int compareTo(MagnitudeType other) {
        return getMagnitude().compareTo(other);
    }
}
