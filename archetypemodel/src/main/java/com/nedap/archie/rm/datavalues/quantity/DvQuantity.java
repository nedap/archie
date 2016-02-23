package com.nedap.archie.rm.datavalues.quantity;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class DvQuantity extends DvAmount<Double> {

    @Nullable
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
