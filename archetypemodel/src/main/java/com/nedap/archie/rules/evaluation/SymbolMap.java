package com.nedap.archie.rules.evaluation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter.bos on 31/03/16.
 */
public class SymbolMap {

    private Map<String, Value> variables = new HashMap<>();

    public void put(String name, Value value) {
        variables.put(name, value);
    }

    public Value get(String name) {
        return variables.get(name);
    }
}
