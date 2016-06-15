package com.nedap.archie.rules;

/**
 * Created by pieter.bos on 27/10/15.
 */
public class Expression extends RuleElement {
    /**
     * If true, this statement originally was placed between ()-signs
     */
    private boolean precedenceOverriden = false;

    public boolean isPrecedenceOverriden() {
        return precedenceOverriden;
    }

    public void setPrecedenceOverridden(boolean precedenceOverriden) {
        this.precedenceOverriden = precedenceOverriden;
    }
}
