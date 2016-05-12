package com.nedap.archie.rm.datavalues.quantity;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DV_AMOUNT", propOrder = {
        "accuracyIsPercent"
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
}
