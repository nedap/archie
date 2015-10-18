package com.nedap.archie.aom;

import com.nedap.archie.base.MultiplicityInterval;

/**
 * Created by pieter.bos on 18/10/15.
 */
public class Cardinality {
    private MultiplicityInterval interval;

    private boolean ordered;
    private boolean unique;

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
}
