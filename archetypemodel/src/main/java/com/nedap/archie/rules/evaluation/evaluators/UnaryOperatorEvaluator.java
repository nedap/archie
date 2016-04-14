package com.nedap.archie.rules.evaluation.evaluators;

import com.google.common.collect.Lists;
import com.nedap.archie.rules.Expression;
import com.nedap.archie.rules.OperatorKind;
import com.nedap.archie.rules.PrimitiveType;
import com.nedap.archie.rules.UnaryOperator;
import com.nedap.archie.rules.evaluation.Evaluator;
import com.nedap.archie.rules.evaluation.RuleEvaluation;
import com.nedap.archie.rules.evaluation.ValueList;

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
                ValueList input = evaluation.evaluate(operand);
                List values = input.getValues();
                ValueList result = new ValueList();
                result.setType(PrimitiveType.Boolean);
                for(Object value:values){
                    if(value == null) {
                        values.add(null);
                    } else if(value instanceof Boolean) {
                        result.addValue(!(Boolean)value);
                    } else {
                        throw new IllegalStateException("Not operator only works on boolean, but " + value + " was supplied");
                    }
                }
                return result;
            case for_all:
            case exists:
            default:
                throw new UnsupportedOperationException("not yet supported " + operator);
        }
    }

    @Override
    public List<Class> getSupportedClasses() {
        return Lists.newArrayList(UnaryOperator.class);
    }
}
