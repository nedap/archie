package com.nedap.archie.base;

import java.util.Objects;

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

    public Interval() {

    }

    public Interval(T lower, T upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public Interval(T lower, T upper, boolean lowerIncluded, boolean upperIncluded) {
        this.lower = lower;
        this.upper = upper;
        this.lowerIncluded = lowerIncluded;
        this.upperIncluded = upperIncluded;
    }

    public static <T>  Interval lowerUnbounded(T upper, boolean upperIncluded) {
        Interval<T> result = new Interval<>(null, upper, true, upperIncluded);
        result.setLowerUnbounded(true);
        return result;
    }

    public static <T>  Interval upperUnbounded(T lower, boolean lowerIncluded) {
        Interval<T> result = new Interval<>(lower, null, lowerIncluded, true);
        result.setUpperUnbounded(true);
        return result;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interval<?> interval = (Interval<?>) o;

        return (lowerUnbounded == interval.lowerUnbounded) &&
            (upperUnbounded == interval.upperUnbounded) &&
            (lowerIncluded == interval.lowerIncluded) &&
            (upperIncluded == interval.upperIncluded) &&
            Objects.equals(lower, interval.lower) &&
            Objects.equals(upper, interval.upper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lower,
                upper,
                lowerUnbounded,
                upperUnbounded,
                lowerIncluded,
                upperIncluded);
    }
}
