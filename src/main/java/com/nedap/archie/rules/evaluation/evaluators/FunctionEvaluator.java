package com.nedap.archie.rules.evaluation.evaluators;

import com.google.common.collect.Lists;
import com.nedap.archie.rules.Expression;
import com.nedap.archie.rules.Function;
import com.nedap.archie.rules.evaluation.Evaluator;
import com.nedap.archie.rules.evaluation.RuleEvaluation;
import com.nedap.archie.rules.evaluation.ValueList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 06/04/2017.
 */
public class FunctionEvaluator  implements Evaluator<Function> {

    public static final String MIN = "min";
    public static final String MAX = "max";
    public static final String MEAN = "mean";
    public static final String SUM = "sum";

    @Override
    public ValueList evaluate(RuleEvaluation evaluation, Function function) {
        List<ValueList> argumentResults = new ArrayList<>();

        for(Expression argument: function.getArguments()) {
            argumentResults.add(evaluation.evaluate(argument));
        }

        switch(function.getFunctionName()) {
            case MIN:
                return min(argumentResults);
            case MAX:
                return max(argumentResults);
            case MEAN:
                return mean(argumentResults);
            case SUM:
                return sum(argumentResults);
        }
        return null;
    }


    private ValueList min(List<ValueList> argumentResults) {
        return new ValueList(null);
    }

    private ValueList max(List<ValueList> argumentResults) {
        return new ValueList(null);
    }

    private ValueList mean(List<ValueList> argumentResults) {
        return new ValueList(null);
    }

    private ValueList sum(List<ValueList> argumentResults) {
        return new ValueList(null);
    }


    @Override
    public List<Class> getSupportedClasses() {
        return Lists.newArrayList(Function.class);
    }
}
