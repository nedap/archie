package com.nedap.archie.base;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class MultiplicityInterval extends Interval<Integer> {

    /**
     * Marker to use in string form of interval between limits.
     */
    public static final String MULTIPLICITY_RANGE_MARKER = "..";
    /**
     * Symbol to use to indicate upper limit unbounded.
     */
    public static final Character MULTIPLICITY_UNBOUNDED_MARKER = '*';

    public MultiplicityInterval() {
        super();
    }

    public MultiplicityInterval(int lower, int upper) {
        super(lower, upper);
    }

    public MultiplicityInterval(Integer lower, Boolean lowerIncluded, Boolean lowerUnbounded, Integer upper, Boolean upperIncluded, Boolean upperUnbounded) {
        setLower(lower);
        setLowerIncluded(lowerIncluded);
        setLowerUnbounded(lowerUnbounded);
        setUpper(upper);
        setUpperIncluded(upperIncluded);
        setUpperUnbounded(upperUnbounded);
    }

    /**
     * Equal to '0..*' or '*'
     * @return
     */
    public static MultiplicityInterval unbounded() {
        MultiplicityInterval result = new MultiplicityInterval();
        result.setLower(0);
        result.setUpperUnbounded(true);
        return result;
    }

    public boolean isOpen() {
        return Integer.valueOf(0).equals(getLower()) && isUpperUnbounded() && isLowerIncluded();
    }

    public boolean isOptional() {
        return Integer.valueOf(0).equals(getLower()) && Integer.valueOf(1).equals(getUpper()) && !isUpperUnbounded() && isLowerIncluded() && isUpperIncluded();
    }

    public boolean isMandatory() {
        return !isLowerUnbounded() && getLower() >= 1 ;
    }

    public boolean isProhibited() {
        return Integer.valueOf(0).equals(getLower()) && Integer.valueOf(0).equals(getUpper()) && !isUpperUnbounded();
    }

    /**
     * Creates interval of type [0,inf)
     *
     * @return
     */
    public static MultiplicityInterval createOpen() {
        return new MultiplicityInterval(0, true, false, null, false, true);
    }

    /**
     * Creates interval of type [0,1]
     *
     * @return
     */
    public static MultiplicityInterval createOptional() {
        return new MultiplicityInterval(0, true, false, 1, true, false);
    }

    /**
     * Creates interval of type [1,1]
     *
     * @return
     */
    public static MultiplicityInterval createMandatory() {
        return new MultiplicityInterval(1, true, false, 1, true, false);
    }

    /**
     * Creates interval of type [0,1]
     *
     * @return
     */
    public static MultiplicityInterval createProhibited() {
        return new MultiplicityInterval(0, true, false, 0, true, false);
    }

    public static MultiplicityInterval createUpperUnbounded(Integer lower) {
        return new MultiplicityInterval(lower, true, false, 0, true, true);
    }

    public static MultiplicityInterval createBounded(int lower, int upper) {
        return new MultiplicityInterval(lower, true, false, upper, true, false);
    }

    public boolean upperIsOne() {
        return has(1) && !has(2);
    }

    public String toString() {
        if(isOpen()) {
            return getLower() + MULTIPLICITY_RANGE_MARKER + MULTIPLICITY_UNBOUNDED_MARKER;
        } else {
            return getLower() + MULTIPLICITY_RANGE_MARKER + getUpper();
        }
    }


}
