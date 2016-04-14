package com.nedap.archie.rules.evaluation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter.bos on 31/03/16.
 */
public class VariableMap {

    private Map<String, ValueList> variables = new HashMap<>();

    public void put(String name, ValueList valueList) {
        variables.put(name, valueList);
    }

    public ValueList get(String name) {
        return variables.get(name);
    }
}
