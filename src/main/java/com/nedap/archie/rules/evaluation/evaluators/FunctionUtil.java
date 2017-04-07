package com.nedap.archie.rules.evaluation.evaluators;

import com.google.common.collect.Lists;
import com.nedap.archie.rules.evaluation.FunctionCallException;
import com.nedap.archie.rules.evaluation.Value;
import com.nedap.archie.rules.evaluation.ValueList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 07/04/2017.
 */
public class FunctionUtil {

    public static double castToDouble(Value value) throws FunctionCallException {
        if(value.getValue() instanceof Long) {
            return ((Long) value.getValue()).doubleValue();
        } else if (value.getValue() instanceof Double) {
            return (double) value.getValue();
        }
        throw new FunctionCallException("cannot cast " + value.getValue().getClass() + " to a number");
    }

    public static int checkLength(List<ValueList> arguments) {
        int length = 1;
        boolean first = true;
        for(ValueList list:arguments) {
            if(first && list.size() != 1) {
                length = list.size();
            } else  if(list.size() != 1 && list.size() != length) {
                return -1;
            }
        }
        return length;
    }

    public static ValueList checkAndHandleNull(ValueList leftValues, ValueList rightValues) {
        return checkAndHandleNull(Lists.newArrayList(leftValues, rightValues));
    }

    public static ValueList checkAndHandleNull(List<ValueList> arguments) {

        if(atLeastOneValueEmpty(arguments)) {
            ValueList result = new ValueList();
            result.addValue(Value.createNull(gatherPaths(arguments)));
            return result;
        }
        return null;
    }

    public static List<String> gatherPaths(List<ValueList> arguments) {
        List<String> result = new ArrayList<>();
        for(ValueList list:arguments) {
            if(!list.isEmpty()) {
                result.addAll(list.getAllPaths());
            }
        }
        return result;
    }

    private static boolean atLeastOneValueEmpty(List<ValueList> arguments) {
        for(ValueList list:arguments) {
            if(list.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
