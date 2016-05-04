package com.nedap.archie.rm.datavalues.quantity;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "DV_QUANTITY", propOrder = {
        "units",
        "precision"
})
public class DvQuantity extends DvAmount<Double> {

    @Nullable
    @XmlElement(defaultValue = "-1")
    private Long precision;
    private String units;

    @Nullable
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

}
