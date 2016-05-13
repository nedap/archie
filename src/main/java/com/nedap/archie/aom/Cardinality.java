package com.nedap.archie.aom;

import com.nedap.archie.base.MultiplicityInterval;

/**
 * Created by pieter.bos on 18/10/15.
 */
public class Cardinality extends ArchetypeModelObject {

    private MultiplicityInterval interval;

    private boolean ordered = false;
    private boolean unique = false;

    public Cardinality() {

    }

    public Cardinality(int lower, int higher) {
        ordered = false;
        unique = lower == 1 && higher == 1;
        interval = new MultiplicityInterval(lower, higher);
    }

    public MultiplicityInterval getInterval() {
        return interval;
    }

    public void setInterval(MultiplicityInterval interval) {
        this.interval = interval;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public static Cardinality unbounded() {
        Cardinality result = new Cardinality();
        result.setInterval(MultiplicityInterval.unbounded());
        return result;
    }
}
