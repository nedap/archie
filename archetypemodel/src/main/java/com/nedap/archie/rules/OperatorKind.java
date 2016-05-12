package com.nedap.archie.rules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pieter.bos on 27/10/15.
 */
public enum OperatorKind {
    eq("="), ne("!=", "≠"), le("<", "≤"), lt("<="), ge(">=", "≥"), gt(">"),
    matches("matches", "∈", "is_in"), not("not", "!", "∼", "¬"), and("and", "∧"), or("or", "∨"), xor("xor", "⊻"),
    implies("implies", "⇒"), for_all("for_all", "∀", "every"), exists("exists" ,"∃"),
    plus("+"), minus("-"), multiply("*"), divide("/"), modulo("%"), exponent("^");

    private Set<String> codes;

    OperatorKind(String... items) {
        codes = new HashSet<>();
        Collections.addAll(codes, items);
    }

    public static OperatorKind parse(String operatorString) {
        operatorString = operatorString.toLowerCase();
        for(OperatorKind operator:values()) { //TODO: a hash implementation is faster
            if(operator.codes.contains(operatorString)) {
                return operator;
            }
        }
        return null;
    }


}
