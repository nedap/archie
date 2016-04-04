package com.nedap.archie.rules.evaluation.evaluators;

import com.google.common.collect.Lists;
import com.nedap.archie.rules.BinaryOperator;
import com.nedap.archie.rules.OperatorKind;
import com.nedap.archie.rules.PrimitiveType;
import com.nedap.archie.rules.evaluation.Evaluator;
import com.nedap.archie.rules.evaluation.RuleEvaluation;
import com.nedap.archie.rules.evaluation.Value;

import java.util.EnumSet;
import java.util.List;


/**
 * Created by pieter.bos on 01/04/16.
 */
public class BinaryOperatorEvaluator implements Evaluator<BinaryOperator> {
    private static final double EPSILON = 0.00001d;

    @Override
    public Value evaluate(RuleEvaluation evaluation, BinaryOperator statement) {
        if(statement.isPrecedenceOverridden()) {
            //TODO!
        }
        Value leftValue = evaluation.evaluate(statement.getLeftOperand());
        Value rightValue = evaluation.evaluate(statement.getRightOperand());
        return evaluate(statement.getOperator(), leftValue, rightValue);
    }

    private Value evaluate(OperatorKind operator, Value leftValue, Value rightValue) {
        switch(operator) {
            case plus:
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();
                    return new Value(leftNumber + rightNumber);
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(leftNumber + rightNumber);
                } else {
                    throw new RuntimeException("not yet supported");
                }
            case minus:
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();
                    return new Value(leftNumber - rightNumber);
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(leftNumber - rightNumber);
                } else {
                    throw new RuntimeException("not yet supported");
                }
            case multiply:
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();
                    return new Value(leftNumber * rightNumber);
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(leftNumber * rightNumber);
                } else {
                    throw new RuntimeException("not yet supported");
                }
            case divide:
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();
                    return new Value(leftNumber / rightNumber);
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(leftNumber / rightNumber);
                } else {
                    throw new RuntimeException("not yet supported");
                }
            case modulo:
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();
                    return new Value(leftNumber % rightNumber);
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(leftNumber % rightNumber);
                } else {
                    throw new RuntimeException("not yet supported");
                }
            case exponent:
                checkIsNumber(leftValue, rightValue);
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();
                    return new Value((long) Math.pow(leftNumber, rightNumber));
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(Math.pow(leftNumber, rightNumber));
                } else {
                    throw new RuntimeException("not yet supported");
                }
            case gt:
                checkIsNumber(leftValue, rightValue);
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();
                    return new Value(leftNumber > rightNumber);
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(leftNumber > rightNumber);
                } else {
                    throw new RuntimeException("not yet supported");
                }
            case ge:
                checkIsNumber(leftValue, rightValue);
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();
                    return new Value(leftNumber >= rightNumber);
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(leftNumber >= rightNumber);
                } else {
                    throw new RuntimeException("not yet supported");
                }
            case lt:
                checkIsNumber(leftValue, rightValue);
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();
                    return new Value(leftNumber < rightNumber);
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(leftNumber < rightNumber);
                } else {
                    throw new RuntimeException("not yet supported");
                }
            case le:
                checkIsNumber(leftValue, rightValue);
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();
                    return new Value(leftNumber <= rightNumber);
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(leftNumber <= rightNumber);
                } else {
                    throw new RuntimeException("not yet supported");
                }
            case eq:
                checkIsNumber(leftValue, rightValue);
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();

                    return new Value(leftNumber == rightNumber);
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(Math.abs(leftNumber - rightNumber) < EPSILON);
                } else {
                    throw new RuntimeException("not yet supported");
                }
            case ne:
                checkIsNumber(leftValue, rightValue);
                checkIsNumber(leftValue, rightValue);
                if(leftValue.getType() == PrimitiveType.Integer && rightValue.getType() == PrimitiveType.Integer) {
                    Long leftNumber = (Long) leftValue.getValue();
                    Long rightNumber = (Long) rightValue.getValue();

                    return new Value(leftNumber != rightNumber);
                } else if (leftValue.getType() == PrimitiveType.Real && rightValue.getType() == PrimitiveType.Real) {
                    Double leftNumber = (Double) leftValue.getValue();
                    Double rightNumber = (Double) rightValue.getValue();
                    return new Value(Math.abs(leftNumber - rightNumber) > EPSILON);
                } else {
                    throw new RuntimeException("not yet supported");
                }

//                    matches("matches", "∈", "is_in"), , and("and", "∧"), or("or", "∨"), xor("xor"),
//                    implies("implies", "®"), for_all("for_all", "∀"), exists("exists" ,"∃"),;

        }
        throw new RuntimeException("operation " + operator + " not yet supported");
    }

    private void checkIsNumber(Value leftValue, Value rightValue) {
        EnumSet numberTypes = EnumSet.of(PrimitiveType.Integer, PrimitiveType.Real);
        if(!numberTypes.contains(leftValue.getType())) {
            throw new RuntimeException("not a number with number operator: " + leftValue.getType());//TODO: proper errors
        }
        if(!numberTypes.contains(rightValue.getType())) {
            throw new RuntimeException("not a number with number operator: " + rightValue.getType());//TODO: proper errors
        }
    }

    @Override
    public List<Class> getSupportedClasses() {
        return Lists.newArrayList(BinaryOperator.class);
    }
}
