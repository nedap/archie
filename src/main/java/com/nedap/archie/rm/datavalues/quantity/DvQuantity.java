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
@XmlType(name = "DV_QUANTITY", propOrder = {
        "magnitude",
        "units",
        "precision"
})
public class DvQuantity extends DvAmount<Double> {

    @Nullable
    private Long precision;
    private String units;

    @Nullable
    @XmlElement(defaultValue = "-1")
    public Long getPrecision() {
        return precision;
    }

    public void setPrecision(@Nullable Long precision) {
        this.precision = precision;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @XmlElement(type=Double.class)
    @Override
    public Double getMagnitude() {
        return super.getMagnitude();
    }

    @Override
    public void setMagnitude(Double magnitude) {
        super.setMagnitude(magnitude);
    }
}
