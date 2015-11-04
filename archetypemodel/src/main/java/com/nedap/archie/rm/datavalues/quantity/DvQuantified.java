package com.nedap.archie.rm.datavalues.quantity;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class DvQuantified<AccuracyType, MagnitudeType extends Comparable> extends DvOrdered<MagnitudeType> {

    @Nullable
    private String magnitudeStatus;
    @Nullable
    private AccuracyType accuracy;
    private MagnitudeType magnitude;

    @Nullable
    public String getMagnitudeStatus() {
        return magnitudeStatus;
    }

    public void setMagnitudeStatus(@Nullable String magnitudeStatus) {
        this.magnitudeStatus = magnitudeStatus;
    }

    public AccuracyType getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(AccuracyType accuracy) {
        this.accuracy = accuracy;
    }

    public MagnitudeType getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(MagnitudeType magnitude) {
        this.magnitude = magnitude;
    }

    @Override
    public int compareTo(MagnitudeType other) {
        return magnitude.compareTo(other);
    }
}
