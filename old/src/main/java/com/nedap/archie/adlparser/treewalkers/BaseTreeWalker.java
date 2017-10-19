package com.nedap.archie.adlparser.treewalkers;

import com.nedap.archie.adlparser.ADLParserErrors;

/**
 * Created by pieter.bos on 29/10/15.
 */
public class BaseTreeWalker {

    private ADLParserErrors errors;

    public BaseTreeWalker(ADLParserErrors errors) {
        this.errors = errors;
    }

    public void addError(String error) {
        errors.addError(error);
    }

    public void addWarning(String warning) {
        errors.addError(warning);
    }

    public ADLParserErrors getErrors() {
        return errors;
    }
}
