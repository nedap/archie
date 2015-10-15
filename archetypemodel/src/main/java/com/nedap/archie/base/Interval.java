package com.nedap.archie.base;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class Interval<T> {

    T lower;
    T upper;
    boolean lowerUnbounded = false;
    boolean upperUnbounded = false;
    boolean lowerIncluded = true;
    boolean upperIncluded = true;

    public T getLower() {
        return lower;
    }

    public void setLower(T lower) {
        this.lower = lower;
    }

    public T getUpper() {
        return upper;
    }

    public void setUpper(T upper) {
        this.upper = upper;
    }

    public boolean isLowerUnbounded() {
        return lowerUnbounded;
    }

    public void setLowerUnbounded(boolean lowerUnbounded) {
        this.lowerUnbounded = lowerUnbounded;
    }

    public boolean isUpperUnbounded() {
        return upperUnbounded;
    }

    public void setUpperUnbounded(boolean upperUnbounded) {
        this.upperUnbounded = upperUnbounded;
    }

    public boolean isLowerIncluded() {
        return lowerIncluded;
    }

    public void setLowerIncluded(boolean lowerIncluded) {
        this.lowerIncluded = lowerIncluded;
    }

    public boolean isUpperIncluded() {
        return upperIncluded;
    }

    public void setUpperIncluded(boolean upperIncluded) {
        this.upperIncluded = upperIncluded;
    }
}
