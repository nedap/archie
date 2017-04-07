package com.nedap.archie.rules.evaluation.evaluators.functions;

import com.nedap.archie.rules.evaluation.FunctionCallException;
import com.nedap.archie.rules.evaluation.FunctionImplementation;
import com.nedap.archie.rules.evaluation.ValueList;

import java.util.Collections;
import java.util.List;

/**
 * Created by pieter.bos on 07/04/2017.
 */
public class Max implements FunctionImplementation {
    @Override
    public String getName() {
        return "max";
    }

    @Override
    public ValueList evaluate(List<ValueList> arguments) throws FunctionCallException {
        return new ValueList(Collections.emptyList());
    }
}