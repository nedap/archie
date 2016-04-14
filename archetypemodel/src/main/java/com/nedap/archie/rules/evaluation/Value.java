package com.nedap.archie.rules.evaluation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 07/04/16.
 */
public class Value {
    private Object value;
    private List<String> paths;//instead we could use Locatable, but for some reason datavalues are not locatable and not pathable, so hard to use here.

    public Value(Object value) {
        this.value = value;
        this.paths = new ArrayList();
    }

    public Value(Object value, List<String> paths) {
        this.value = value;
        this.paths = paths;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Get the paths used to evaluate this result
     * @return
     */
    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }
}
