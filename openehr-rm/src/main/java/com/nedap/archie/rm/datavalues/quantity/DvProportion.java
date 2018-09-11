package com.nedap.archie.rm.datavalues.quantity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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

    private Double numerator;
    private Double denominator;
    private Long type;
    @Nullable
    private Long precision;

    public Double getNumerator() {
        return numerator;
    }

    public void setNumerator(Double numerator) {
        this.numerator = numerator;
    }


    public Double getDenominator() {
        return denominator;
    }

    public void setDenominator(Double denominator) {
        this.denominator = denominator;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Nullable
    public Long getPrecision() {
        return precision;
    }

    public void setPrecision(@Nullable Long precision) {
        this.precision = precision;
    }

    @JsonIgnore
    public boolean isIntegral() {
        return precision != null && precision == 0;
    }

    @Override
    @JsonIgnore
    public Double getMagnitude() {
        if(numerator != null && denominator != null && denominator != 0.0d) {
            return numerator / denominator;
        } else if(numerator == null) {
            return 0.0;
        } else {
            return Double.MAX_VALUE;//TODO: actually: infinity. Max Double value?
        }
    }
}
