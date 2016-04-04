package com.nedap.archie.rules.evaluation.evaluators;

import com.google.common.collect.Lists;
import com.nedap.archie.rules.BinaryOperator;
import com.nedap.archie.rules.OperatorKind;
import com.nedap.archie.rules.PrimitiveType;
import com.nedap.archie.rules.evaluation.Evaluator;
import com.nedap.archie.rules.evaluation.RuleEvaluation;
import com.nedap.archie.rules.evaluation.Value;
import org.jetbrains.annotations.NotNull;

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
        if(statement.getOperator() == OperatorKind.implies) {
            if(leftValue.getType() != PrimitiveType.Boolean) {
                throw new IllegalArgumentException("left operand type should be boolean, but was " + leftValue.getType());
            }
            if((Boolean) leftValue.getValue()) {
                //we could use the tag from the overall statement, but not sure if we should
                evaluation.assertionEvaluated(null /* no tag present here*/, statement.getRightOperand(), rightValue);
            }
        }
        return evaluate(evaluation, statement.getOperator(), leftValue, rightValue);
    }

    private Value evaluate(RuleEvaluation evaluation, OperatorKind operator, Value leftValue, Value rightValue) {
        switch(operator) {
            case plus:
            case minus:
            case multiply:
            case divide:
            case modulo:
            case exponent:
                return evaluateArithmeticOperator(operator, leftValue, rightValue);
            case gt:
            case ge:
            case lt:
            case le:
            case eq:
            case ne:
                return evaluateRelOpOperator(operator, leftValue, rightValue);
            case and:
            case or:
            case xor:
                return evaluateBooleanOperator(evaluation, operator, leftValue, rightValue);
//                    matches("matches", "∈", "is_in"),
//                    implies("implies", "®"), for_all("for_all", "∀"), exists("exists" ,"∃"),;

        }
        throw new RuntimeException("operation " + operator + " not yet supported");
    }

    private Value evaluateBooleanOperator(RuleEvaluation evaluation, OperatorKind operator, Value leftValue, Value rightValue) {
        checkisBoolean(leftValue, rightValue);
        Boolean leftBoolean = (Boolean) leftValue.getValue();
        Boolean rightBoolean = (Boolean) leftValue.getValue();
        switch(operator) {
            case and:
                return new Value(leftBoolean & rightBoolean);
            case or:
                return new Value(leftBoolean | rightBoolean);
            case xor:
                return new Value(leftBoolean ^ rightBoolean);
            default:
                throw new IllegalArgumentException("Not a boolean operator: " + operator);
        }
    }

    private Value evaluateArithmeticOperator(OperatorKind operator, Value leftValue, Value rightValue) {
        checkIsNumber(leftValue, rightValue);
        if(leftValue.getValue() instanceof Long && rightValue.getValue() instanceof Long) {
            return evaluateIntegerArithmetic(operator, leftValue, rightValue);
        } else {
            return evaluateRealArithmetic(operator, leftValue, rightValue);
        }
    }

    private Value evaluateRealArithmetic(OperatorKind operator, Value left, Value right) {
        Double leftNumber = convertToDouble(left);
        Double rightNumber = convertToDouble(right);
        switch(operator) {
            case plus:
                return new Value(leftNumber + rightNumber);
            case minus:
                return new Value(leftNumber - rightNumber);
            case multiply:
                return new Value(leftNumber * rightNumber);
            case divide:
                return new Value(leftNumber / rightNumber);
            case modulo:
                return new Value(leftNumber % rightNumber);
            case exponent:
                return new Value(Math.pow(leftNumber, rightNumber));
            default:
                throw new IllegalArgumentException("Not an arithmetic operator: " + operator);
        }
    }



    private Value evaluateIntegerArithmetic(OperatorKind operator, Value left, Value right) {
        Long leftNumber = (Long) left.getValue();
        Long rightNumber = (Long) right.getValue();
        switch(operator) {
            case plus:
                return new Value(leftNumber + rightNumber);
            case minus:
                return new Value(leftNumber - rightNumber);
            case multiply:
                return new Value(leftNumber * rightNumber);
            case divide:
                return new Value(leftNumber / rightNumber);
            case modulo:
                return new Value(leftNumber % rightNumber);
            case exponent:
                return new Value((long) Math.pow(leftNumber, rightNumber));
            default:
                throw new IllegalArgumentException("Not an arithmetic operator: " + operator);
        }
    }

    @NotNull
    private Value evaluateRelOpOperator(OperatorKind operator, Value leftValue, Value rightValue) {
        checkIsNumber(leftValue, rightValue);
        if(leftValue.getValue() instanceof Long && rightValue.getValue() instanceof Long) {
            return evaluateIntegerRelOp(operator, leftValue, rightValue);
        } else {
            return evaluateRealRelOp(operator, leftValue, rightValue);
        }
    }

    private Value evaluateIntegerRelOp(OperatorKind operator, Value left, Value right) {

        Long leftNumber = (Long) left.getValue();
        Long rightNumber = (Long) right.getValue();
        switch(operator) {
            case eq:
                return new Value(leftNumber == rightNumber);
            case ne:
                return new Value(leftNumber != rightNumber);
            case gt:
                return new Value(leftNumber > rightNumber);
            case lt:
                return new Value(leftNumber < rightNumber);
            case ge:
                return new Value(leftNumber >= rightNumber);
            case le:
                return new Value(leftNumber <= rightNumber);

            default:
                throw new IllegalArgumentException("Not a boolean operator: " + operator);
        }
    }

    private Value evaluateRealRelOp(OperatorKind operator, Value left, Value right) {

        Double leftNumber = convertToDouble(left);
        Double rightNumber = convertToDouble(right);
        switch(operator) {
            case eq:
                return new Value(Math.abs(leftNumber - rightNumber) < EPSILON);
            case ne:
                return new Value(Math.abs(leftNumber - rightNumber) >= EPSILON);
            case gt:
                return new Value(leftNumber > rightNumber);
            case lt:
                return new Value(leftNumber < rightNumber);
            case ge:
                return new Value(leftNumber >= rightNumber);
            case le:
                return new Value(leftNumber <= rightNumber);
            default:
                throw new IllegalArgumentException("Not a boolean operator: " + operator);
        }
    }

    private void checkisBoolean(Value leftValue, Value rightValue) {
        EnumSet booleanTypes = EnumSet.of(PrimitiveType.Boolean);
        if(!booleanTypes.contains(leftValue.getType())) {
            throw new RuntimeException("not a boolean with boolean operator: " + leftValue.getType());//TODO: proper errors
        }
        if(!booleanTypes.contains(rightValue.getType())) {
            throw new RuntimeException("not a boolean with boolean operator: " + rightValue.getType());//TODO: proper errors
        }
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

    private double convertToDouble(Value value) {
        return value.getValue() instanceof  Double ? (Double) value.getValue() : ((Long) value.getValue()).doubleValue();
    }

    @Override
    public List<Class> getSupportedClasses() {
        return Lists.newArrayList(BinaryOperator.class);
    }
}
