package com.nedap.archie.rules.evaluation.evaluators;

import com.google.common.collect.Lists;
import com.nedap.archie.rules.BinaryOperator;
import com.nedap.archie.rules.Constraint;
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
        switch(statement.getOperator()) {
            case plus:
            case minus:
            case multiply:
            case divide:
            case modulo:
            case exponent:
                return evaluateArithmeticOperator(evaluation, statement);
            case gt:
            case ge:
            case lt:
            case le:
            case eq:
            case ne:
                return evaluateRelOpOperator(evaluation, statement);
            case and:
            case or:
            case xor:
                return evaluateBooleanOperator(evaluation, statement);
            case matches:
                return evaluateBooleanConstraint(evaluation, statement);
            case implies:
                return evaluateImplies(evaluation, statement);
                //for_all("for_all", "∀"), exists("exists" ,"∃"),;
        }
        throw new RuntimeException("operation " + statement.getOperator() + " not yet supported");
    }

    private Value evaluateImplies(RuleEvaluation evaluation, BinaryOperator statement) {
        Value leftValue = evaluation.evaluate(statement.getLeftOperand());
        if(leftValue.getType() != PrimitiveType.Boolean) {
            throw new IllegalArgumentException("left operand type should be boolean, but was " + leftValue.getType());
        }
        if((Boolean) leftValue.getValue()) {
            //we could use the tag from the overall statement, but not sure if we should
            Value rightValue = evaluation.evaluate(statement.getRightOperand());
            evaluation.assertionEvaluated(null /* no tag present here*/, statement.getRightOperand(), rightValue);
        }
        return leftValue;//i think?
    }

    private Value evaluateBooleanConstraint(RuleEvaluation evaluation, BinaryOperator statement) {
        Value leftValue = evaluation.evaluate(statement.getLeftOperand());
        if(!(statement.getRightOperand() instanceof Constraint)){
            throw new IllegalArgumentException("cannot evaluate matches statement, right operand not a constraint");

        }
        Constraint constraint = (Constraint) statement.getRightOperand();
        return new Value(constraint.getItem().isValidValue(leftValue.getValue()));

    }

    private Value evaluateBooleanOperator(RuleEvaluation evaluation, BinaryOperator statement) {
        Value leftValue = evaluation.evaluate(statement.getLeftOperand());
        Value rightValue = evaluation.evaluate(statement.getRightOperand());
        checkisBoolean(leftValue, rightValue);
        Boolean leftBoolean = (Boolean) leftValue.getValue();
        Boolean rightBoolean = (Boolean) leftValue.getValue();
        switch(statement.getOperator()) {
            case and:
                return new Value(leftBoolean & rightBoolean);
            case or:
                return new Value(leftBoolean | rightBoolean);
            case xor:
                return new Value(leftBoolean ^ rightBoolean);
            default:
                throw new IllegalArgumentException("Not a boolean operator: " + statement.getOperator());
        }
    }

    private Value evaluateArithmeticOperator(RuleEvaluation evaluation, BinaryOperator statement) {
        Value leftValue = evaluation.evaluate(statement.getLeftOperand());
        Value rightValue = evaluation.evaluate(statement.getRightOperand());

        checkIsNumber(leftValue, rightValue);
        if(leftValue.getValue() instanceof Long && rightValue.getValue() instanceof Long) {
            return evaluateIntegerArithmetic(statement.getOperator(), leftValue, rightValue);
        } else {
            return evaluateRealArithmetic(statement.getOperator(), leftValue, rightValue);
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
    private Value evaluateRelOpOperator(RuleEvaluation evaluation, BinaryOperator statement) {
        Value leftValue = evaluation.evaluate(statement.getLeftOperand());
        Value rightValue = evaluation.evaluate(statement.getRightOperand());
        checkIsNumber(leftValue, rightValue);
        if(leftValue.getValue() instanceof Long && rightValue.getValue() instanceof Long) {
            return evaluateIntegerRelOp(statement.getOperator(), leftValue, rightValue);
        } else {
            return evaluateRealRelOp(statement.getOperator(), leftValue, rightValue);
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
