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
}
