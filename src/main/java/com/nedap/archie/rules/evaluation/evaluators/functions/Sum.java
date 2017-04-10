package com.nedap.archie.rules.evaluation.evaluators.functions;

import com.nedap.archie.rules.evaluation.FunctionCallException;
import com.nedap.archie.rules.evaluation.FunctionImplementation;
import com.nedap.archie.rules.evaluation.ValueList;

import java.util.Collections;
import java.util.List;

/**
 * Created by pieter.bos on 07/04/2017.
 */
public class Sum implements FunctionImplementation {
    @Override
    public String getName() {
        return "sum";
    }

    @Override
    public ValueList evaluate(List<ValueList> arguments) throws FunctionCallException {
        throw new UnsupportedOperationException("The function sum has not yet been implemented");
    }
}