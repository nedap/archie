package com.nedap.archie.rules.evaluation;

import java.util.List;

/**
 * Created by pieter.bos on 07/04/2017.
 */
public interface FunctionImplementation {

    String getName();
    ValueList evaluate(List<ValueList> arguments) throws FunctionCallException;
}
