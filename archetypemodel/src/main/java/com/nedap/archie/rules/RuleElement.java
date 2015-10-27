package com.nedap.archie.rules;

/**
 * Created by pieter.bos on 27/10/15.
 */
public class RuleElement {

    private ExpressionType type;//TODO: enum?

    public ExpressionType getType() {
        return type;
    }

    public void setType(ExpressionType type) {
        this.type = type;
    }
}
