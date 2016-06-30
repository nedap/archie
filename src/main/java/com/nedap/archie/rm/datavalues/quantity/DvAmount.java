package com.nedap.archie.rm.datavalues.quantity;

import com.nedap.archie.rm.datavalues.quantity.datetime.DvDuration;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DV_AMOUNT", propOrder = {
        "accuracy",
        "accuracyIsPercent"
})
@XmlSeeAlso({
        DvProportion.class,
        DvCount.class,
        DvDuration.class,
        DvQuantity.class
})
public abstract class DvAmount<MagnitudeType extends Comparable> extends DvQuantified<Double, MagnitudeType>{
    @Nullable

    private Boolean accuracyIsPercent;

    @Nullable
    @XmlElement(name = "accuracy_is_percent")
    public Boolean getAccuracyIsPercent() {
        return accuracyIsPercent;
    }

    public void setAccuracyIsPercent(@Nullable Boolean accuracyIsPercent) {
        this.accuracyIsPercent = accuracyIsPercent;
    }

    @Override
    @XmlElement(type=Double.class)
    public Double getAccuracy() {
        return super.getAccuracy();
    }

    @Override
    public void setAccuracy(Double accuracy) {
        super.setAccuracy(accuracy);
    }
}
