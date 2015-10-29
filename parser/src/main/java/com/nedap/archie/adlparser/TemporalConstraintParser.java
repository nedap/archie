package com.nedap.archie.adlparser;

import org.antlr.runtime.tree.BaseTree;

/**
 * Created by pieter.bos on 29/10/15.
 */
public class TemporalConstraintParser extends BaseTreeWalker {

    public TemporalConstraintParser(ADLParserErrors errors) {
        super(errors);
    }
}
