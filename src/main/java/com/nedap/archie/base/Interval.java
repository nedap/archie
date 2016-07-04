package com.nedap.archie.base;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlElement;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.Objects;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class Interval<T>  extends OpenEHRBase {

    @Nullable
    private T lower;
    @Nullable
    private T upper;
    private boolean lowerUnbounded = false;
    private boolean upperUnbounded = false;
    private boolean lowerIncluded = true;
    private boolean upperIncluded = true;

    public Interval() {

    }

    public Interval(T value) {
        this.lower = value;
        this.upper = value;
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

    public static <T extends Comparable>  Interval lowerUnbounded(T upper, boolean upperIncluded) {
        Interval<T> result = new Interval<>(null, upper, true, upperIncluded);
        result.setLowerUnbounded(true);
        return result;
    }

    public static <T extends Comparable>  Interval upperUnbounded(T lower, boolean lowerIncluded) {
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

    @XmlElement(name = "lower_unbounded")
    public boolean isLowerUnbounded() {
        return lowerUnbounded;
    }

    public void setLowerUnbounded(boolean lowerUnbounded) {
        this.lowerUnbounded = lowerUnbounded;
    }

    @XmlElement(name = "upper_unbounded")
    public boolean isUpperUnbounded() {
        return upperUnbounded;
    }

    public void setUpperUnbounded(boolean upperUnbounded) {
        this.upperUnbounded = upperUnbounded;
    }

    @XmlElement(name = "lower_included")
    public boolean isLowerIncluded() {
        return lowerIncluded;
    }

    public void setLowerIncluded(boolean lowerIncluded) {
        this.lowerIncluded = lowerIncluded;
    }

    @XmlElement(name = "upper_included")
    public boolean isUpperIncluded() {
        return upperIncluded;
    }

    public void setUpperIncluded(boolean upperIncluded) {
        this.upperIncluded = upperIncluded;
    }

    public boolean has(T value) {
        if(lowerUnbounded && upperUnbounded) {
            return true;
        }
        //since TemporalAmount does not implement Comparable we have to do some magic here
        Comparable comparableValue;
        Comparable comparableLower;
        Comparable comparableUpper;
        if(value instanceof TemporalAmount && !(value instanceof Comparable) && isNonComparableTemporalAmount(lower) && isNonComparableTemporalAmount(upper)) {
            //TemporalAmount is not comparable, but can always be converted to a duration that is comparable.
            comparableValue = value == null ? null : Duration.from((TemporalAmount) value);
            comparableLower = lower == null ? null : Duration.from((TemporalAmount) lower);
            comparableUpper = upper == null ? null : Duration.from((TemporalAmount) upper);
        }
        else if(!(isComparable(lower) && isComparable(upper) && isComparable(value))) {
            throw new UnsupportedOperationException("subclasses of interval not implementing comparable should implement their own has method");
        } else {
            comparableValue = (Comparable) value;
            comparableLower = (Comparable) lower;
            comparableUpper = (Comparable) upper;
        }

        if(value == null) {
            //interval values are not concerned with cardinality, so return true if not set
            return true;
        }

        if(!lowerUnbounded) {
            int comparedWithLower = comparableValue.compareTo(comparableLower);
            if (comparedWithLower < 0 || (!lowerIncluded && comparedWithLower == 0)) {
                return false;
            }
        }

        if(!upperUnbounded) {
            int comparedWithUpper = comparableValue.compareTo(comparableUpper);
            if (comparedWithUpper > 0 || (!upperIncluded && comparedWithUpper == 0)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNonComparableTemporalAmount(T value) {
        return value == null || (!(value instanceof Comparable) && value instanceof TemporalAmount);
    }

    private boolean isComparable(T upper) {
        return upper == null || upper instanceof Comparable;
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

    @Override
    public String toString() {

        if(lowerUnbounded) {
            return "|" + (upperIncluded ? "<=" : "<") + upper + "|";
        }
        if(upperUnbounded) {
            return "|" + (lowerIncluded ? ">=" : ">") + lower + "|";
        }

        if(lower != null && upper != null && lower == upper) {
            return lower.toString();
        }
        StringBuilder result = new StringBuilder();
        result.append("|");
        if(!lowerIncluded) {
            result.append(">");
        }
        result.append(lower);
        result.append("..");
        if(!upperIncluded) {
            result.append("<");
        }
        result.append(upper);
        result.append("|");
        return result.toString();
    }
}
