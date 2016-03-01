package com.nedap.archie.rules;

/**
 * Assertion object. For now contains all the text of the assertions, and not the parsed form
 * Created by pieter.bos on 15/10/15.
 */
public class Assertion extends RuleStatement {

    private String stringExpression;

    private Expression expression;
    private String tag;

    public Assertion() {

    }

    /**
     * Set the string expression. WARNING: this will not set the other fields, this is not a parser.
     * @param text
     */
    public Assertion(String text) {
        this.stringExpression = text;
    }

    public String getStringExpression() {
        return stringExpression;
    }

    /**
     * Set the string expression. WARNING: this will not set the other fields, this is not a parser.
     * @param text
     */
    public void setStringExpression(String text) {
        this.stringExpression = text;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
