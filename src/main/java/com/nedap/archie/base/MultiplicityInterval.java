package com.nedap.archie.base;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class MultiplicityInterval extends Interval<Integer> {

    public MultiplicityInterval() {
        super();
    }

    public MultiplicityInterval(int lower, int upper) {
        super(lower, upper);
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

    public boolean upperIsOne() {
        return has(1) && !has(2);
    }

}
