package com.nedap.archie.rm.datavalues.quantity;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * TODO: This does not implement PROPORTION KIND, because multiple inheritance - won't work.
 * It does have a type=proportion kind enum
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_PROPORTION", propOrder = {
        "numerator",
        "denominator",
        "type",
        "precision"
})
public class DvProportion extends DvAmount<Double> {

    private double numerator;
    private double denominator;
    private long type;
    @Nullable
    private Long precision;

    public double getNumerator() {
        return numerator;
    }

    public void setNumerator(double numerator) {
        this.numerator = numerator;
    }


    public double getDenominator() {
        return denominator;
    }

    public void setDenominator(double denominator) {
        this.denominator = denominator;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    @Nullable
    public Long getPrecision() {
        return precision;
    }

    public void setPrecision(@Nullable Long precision) {
        this.precision = precision;
    }

    @Override
    public Double getMagnitude() {
        if(denominator != 0.0d) {
            return numerator / denominator;
        } else {
            return Double.MAX_VALUE;//TODO: actually: infinity. Max Double value?
        }
    }
}
