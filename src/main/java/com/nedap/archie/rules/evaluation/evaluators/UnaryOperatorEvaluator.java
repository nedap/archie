package com.nedap.archie.rules.evaluation.evaluators;

import com.google.common.collect.Lists;
import com.nedap.archie.rules.Expression;
import com.nedap.archie.rules.OperatorKind;
import com.nedap.archie.rules.PrimitiveType;
import com.nedap.archie.rules.UnaryOperator;
import com.nedap.archie.rules.evaluation.Evaluator;
import com.nedap.archie.rules.evaluation.RuleEvaluation;
import com.nedap.archie.rules.evaluation.Value;
import com.nedap.archie.rules.evaluation.ValueList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by pieter.bos on 04/04/16.
 */
public class UnaryOperatorEvaluator implements Evaluator<UnaryOperator> {
    @Override
    public ValueList evaluate(RuleEvaluation evaluation, UnaryOperator statement) {
        OperatorKind operator = statement.getOperator();
        Expression operand = statement.getOperand();
        switch(operator) {
            case not:
                return handleNot(evaluation, statement);
            case exists:
                return handleExists(evaluation, statement);
            default:
                throw new UnsupportedOperationException("not yet supported " + operator);
        }
    }

    private ValueList handleExists(RuleEvaluation evaluation, UnaryOperator statement) {
        ValueList value = evaluation.evaluate(statement.getOperand());
        if(value.isEmpty() || value.containsOnlyNullValues()) {
            return new ValueList(false, value.getAllPaths());
        } else {
            return new ValueList(true, value.getAllPaths());
        }
    }

    @NotNull
    public ValueList handleNot(RuleEvaluation evaluation, UnaryOperator statement) {
        Expression operand = statement.getOperand();
        ValueList input = evaluation.evaluate(operand);
        List<Value> values = input.getValues();
        ValueList result = new ValueList();
        result.setType(PrimitiveType.Boolean);
        for(Value value:values){
            if(value == null) {
                values.add(null);
            } else if(value.getValue() instanceof Boolean) {
                result.addValue(!(Boolean)value.getValue(), value.getPaths());
            } else {
                throw new IllegalStateException("Not operator only works on boolean, but " + value + " was supplied");
            }
        }
        return result;
    }

    @Override
    public List<Class> getSupportedClasses() {
        return Lists.newArrayList(UnaryOperator.class);
    }
}
