package com.nedap.archie.rules.evaluation.evaluators;

import com.google.common.collect.Lists;
import com.nedap.archie.rules.BinaryOperator;
import com.nedap.archie.rules.Constraint;
import com.nedap.archie.rules.OperatorKind;
import com.nedap.archie.rules.PrimitiveType;
import com.nedap.archie.rules.evaluation.Evaluator;
import com.nedap.archie.rules.evaluation.RuleEvaluation;
import com.nedap.archie.rules.evaluation.Value;
import com.nedap.archie.rules.evaluation.ValueList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


/**
 * Created by pieter.bos on 01/04/16.
 */
public class BinaryOperatorEvaluator implements Evaluator<BinaryOperator> {
    private static final double EPSILON = 0.00001d;

    @Override
    public ValueList evaluate(RuleEvaluation evaluation, BinaryOperator statement) {
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

    private ValueList evaluateImplies(RuleEvaluation evaluation, BinaryOperator statement) {
        return null;//TODO
//        Value leftValue = evaluation.evaluate(statement.getLeftOperand());
//        if(leftValue.getType() != PrimitiveType.Boolean) {
//            throw new IllegalArgumentException("left operand type should be boolean, but was " + leftValue.getType());
//        }
//        if((Boolean) leftValue.getValue()) {
//            //we could use the tag from the overall statement, but not sure if we should
//            Value rightValue = evaluation.evaluate(statement.getRightOperand());
//            evaluation.assertionEvaluated(null /* no tag present here*/, statement.getRightOperand(), rightValue);
//        }
//        return leftValue;//i think?
    }

    private ValueList evaluateBooleanConstraint(RuleEvaluation evaluation, BinaryOperator statement) {
        ValueList leftValues = evaluation.evaluate(statement.getLeftOperand());
        if(!(statement.getRightOperand() instanceof Constraint)){
            throw new IllegalArgumentException("cannot evaluate matches statement, right operand not a constraint");

        }
        Constraint constraint = (Constraint) statement.getRightOperand();
        ValueList result = new ValueList();
        result.setType(PrimitiveType.Boolean);
        for(Value value:leftValues.getValues()) {
            result.addValue(constraint.getItem().isValidValue(value.getValue()), value.getPaths());
        }
        return result;

    }

    private ValueList evaluateBooleanOperator(RuleEvaluation evaluation, BinaryOperator statement) {

        ValueList leftValues = evaluation.evaluate(statement.getLeftOperand());
        ValueList rightValues = evaluation.evaluate(statement.getRightOperand());

        ValueList possibleNullResult = checkAndHandleNull(evaluation, statement, leftValues, rightValues);
        if(possibleNullResult != null) {
            possibleNullResult.setType(PrimitiveType.Boolean);
            return possibleNullResult;
        }


        checkisBoolean(leftValues, rightValues);

        ValueList result = new ValueList();
        result.setType(PrimitiveType.Boolean);
        if(leftValues.size() == rightValues.size()) {
            for(int i = 0; i < leftValues.size();i++) {
                Value<Boolean> leftValue = leftValues.get(i);
                Value<Boolean> rightValue = rightValues.get(i);
                List<String> paths = getPaths(leftValue, rightValue);
                result.addValue(evaluateBoolean(statement, leftValue.getValue(), rightValue.getValue()), paths);
            }
        } else if (leftValues.size() == 1) {
            Value<Boolean> leftValue = leftValues.get(0);
            for(Value<Boolean> rightValue:rightValues.getValues()) {
                List<String> paths = getPaths(leftValue, rightValue);
                result.addValue(evaluateBoolean(statement, leftValue.getValue(), rightValue.getValue()), paths);
            }
        } else if (rightValues.size() == 1) {
            Value<Boolean>  rightValue = rightValues.get(0);
            for(Value<Boolean> leftValue:leftValues.getValues()) {
                List<String> paths = getPaths(leftValue, rightValue);
                result.addValue(evaluateBoolean(statement, leftValue.getValue(), rightValue.getValue()), paths);
            }
        } else {
            throw new IllegalArgumentException("sizes of operator arguments not compatible");
        }

        return result;
    }

    @NotNull
    private List<String> getPaths(Value<Boolean> leftValue, Value<Boolean> rightValue) {
        List<String> paths = new ArrayList<>();
        paths.addAll(leftValue.getPaths());
        paths.addAll(rightValue.getPaths());
        return paths;
    }

    private Boolean evaluateBoolean(BinaryOperator statement, Boolean leftBoolean, Boolean rightBoolean) {
        switch(statement.getOperator()) {
            case and:
                return leftBoolean & rightBoolean;
            case or:
                return leftBoolean | rightBoolean;
            case xor:
                return leftBoolean ^ rightBoolean;
            default:
                throw new IllegalArgumentException("Not a boolean operator: " + statement.getOperator());
        }
    }

    private ValueList evaluateArithmeticOperator(RuleEvaluation evaluation, BinaryOperator statement) {
        ValueList leftValues = evaluation.evaluate(statement.getLeftOperand());
        ValueList rightValues = evaluation.evaluate(statement.getRightOperand());


        ValueList possibleNullResult = checkAndHandleNull(evaluation, statement, leftValues, rightValues);
        if(possibleNullResult != null) {
            possibleNullResult.setType(PrimitiveType.Boolean);
            return possibleNullResult;
        } else {
            ValueList result = new ValueList();
            result.setType(PrimitiveType.Real);

            checkIsNumber(leftValues, rightValues);
            if (leftValues.size() == rightValues.size()) {
                for (int i = 0; i < leftValues.size(); i++) {
                    Value leftValue = leftValues.get(i);
                    Value rightValue = rightValues.get(i);
                    evaluateArithmetic(statement, result, rightValue.getValue(), leftValue.getValue(), getPaths(leftValue, rightValue));
                }
            } else if (leftValues.size() == 1) {
                Value leftValue = leftValues.get(0);
                for (Value rightValue : rightValues.getValues()) {
                    evaluateArithmetic(statement, result, rightValue.getValue(), leftValue.getValue(), getPaths(leftValue, rightValue));
                }
            } else if (rightValues.size() == 1) {
                Value rightValue = rightValues.get(0);
                for (Value leftValue : leftValues.getValues()) {
                    evaluateArithmetic(statement, result, rightValue.getValue(), leftValue.getValue(), getPaths(leftValue, rightValue));
                }
            } else {
                //TODO: this also happens when one of the modelreferences has a value that does not exist in the model, but others have - while the rules are correct. Those are very valid cases
                //how to fix?
                throw new IllegalArgumentException("sizes of operator arguments not compatible");
            }
            return result;
        }

    }

    public ValueList checkAndHandleNull(RuleEvaluation evaluation, BinaryOperator statement, ValueList leftValues, ValueList rightValues) {
        ValueList result = new ValueList();
        if (leftValues.isEmpty() && rightValues.isEmpty()) {
            //this solves part of the null-valued expression problem. But certainly not all.
            result.addValue(Value.createNull(Lists.newArrayList()));
        } else if (leftValues.isEmpty()) {
            for (Value value : rightValues.getValues()) {
                result.addValue(Value.createNull(value.getPaths()));
            }
        } else if (rightValues.isEmpty()) {
            for (Value value : leftValues.getValues()) {
                result.addValue(Value.createNull(value.getPaths()));
            }
        } else {
            return null;
        }
        return result;

    }

    private void evaluateArithmetic(BinaryOperator statement, ValueList result, Object rightValue, Object leftValue, List<String> paths) {
        if(leftValue instanceof Long && rightValue instanceof Long) {
            result.addValue(evaluateIntegerArithmetic(statement.getOperator()
                    , (Long) leftValue
                    , (Long) rightValue), paths);
            result.setType(PrimitiveType.Integer);
        } else {
            result.addValue(evaluateRealArithmetic(statement.getOperator()
                            , convertToDouble(leftValue)
                            , convertToDouble(rightValue))
                    , paths
            );
            result.setType(PrimitiveType.Real);
        }
    }

    private Double evaluateRealArithmetic(OperatorKind operator, Double leftNumber, Double rightNumber) {
        switch(operator) {
            case plus:
                return leftNumber + rightNumber;
            case minus:
                return leftNumber - rightNumber;
            case multiply:
                return leftNumber * rightNumber;
            case divide:
                return leftNumber / rightNumber;
            case modulo:
                return leftNumber % rightNumber;
            case exponent:
                return Math.pow(leftNumber, rightNumber);
            default:
                throw new IllegalArgumentException("Not an arithmetic operator: " + operator);
        }
    }



    private Long evaluateIntegerArithmetic(OperatorKind operator, Long leftNumber, Long rightNumber) {
        switch(operator) {
            case plus:
                return leftNumber + rightNumber;
            case minus:
                return leftNumber - rightNumber;
            case multiply:
                return leftNumber * rightNumber;
            case divide:
                return leftNumber / rightNumber;
            case modulo:
                return leftNumber % rightNumber;
            case exponent:
                return (long) Math.pow(leftNumber, rightNumber);
            default:
                throw new IllegalArgumentException("Not an arithmetic operator: " + operator);
        }
    }

    @NotNull
    private ValueList evaluateRelOpOperator(RuleEvaluation evaluation, BinaryOperator statement) {
        ValueList leftValues = evaluation.evaluate(statement.getLeftOperand());
        ValueList rightValues = evaluation.evaluate(statement.getRightOperand());


        ValueList possibleNullResult = checkAndHandleNull(evaluation, statement, leftValues, rightValues);
        if(possibleNullResult != null) {
            possibleNullResult.setType(PrimitiveType.Boolean);
            return possibleNullResult;
        } else {
            checkIsNumber(leftValues, rightValues);

            ValueList result = new ValueList();
            result.setType(PrimitiveType.Boolean);

            //according to the xpath spec, at least one pair from both collections must exist that matches the condition.
            //want otherwise? Use for_all/every
            result.addValue(evaluateMultipleValuesRelOp(statement, leftValues, rightValues));

            return result;
        }
    }

    private Value evaluateMultipleValuesRelOp(BinaryOperator statement, ValueList leftValues, ValueList rightValues) {

        List<String> allPaths = new ArrayList<>();
        for(Value leftValue:leftValues.getValues()) {
            for (Value rightValue:rightValues.getValues()) {
                Value evaluatedRelOp = evaluateRelOp(statement, leftValue.getValue(), rightValue.getValue(), getPaths(leftValue, rightValue));
                allPaths.addAll(evaluatedRelOp.getPaths());
                if (((Boolean) evaluatedRelOp.getValue()).booleanValue()) {
                    return evaluatedRelOp;
                }
            }
        }
        return new Value(false, allPaths);
    }

    private Value evaluateRelOp(BinaryOperator statement, Object leftValue, Object rightValue, List<String> paths) {
        if(leftValue instanceof Long && rightValue instanceof Long) {
            return new Value(evaluateIntegerRelOp(statement.getOperator(),
                    (Long) leftValue,
                    (Long) rightValue
            ), paths);
        } else {
            return new Value(evaluateRealRelOp(statement.getOperator(),
                    convertToDouble(leftValue),
                    convertToDouble(rightValue)
            ), paths);
        }
    }

    private Boolean evaluateIntegerRelOp(OperatorKind operator, Long leftNumber, Long rightNumber) {

        switch(operator) {
            case eq:
                return leftNumber == rightNumber;
            case ne:
                return leftNumber != rightNumber;
            case gt:
                return leftNumber > rightNumber;
            case lt:
                return leftNumber < rightNumber;
            case ge:
                return leftNumber >= rightNumber;
            case le:
                return leftNumber <= rightNumber;

            default:
                throw new IllegalArgumentException("Not a boolean operator: " + operator);
        }
    }

    private Boolean evaluateRealRelOp(OperatorKind operator, Double leftNumber, Double rightNumber) {

        switch(operator) {
            case eq:
                return Math.abs(leftNumber - rightNumber) < EPSILON;
            case ne:
                return Math.abs(leftNumber - rightNumber) >= EPSILON;
            case gt:
                return leftNumber > rightNumber;
            case lt:
                return leftNumber < rightNumber;
            case ge:
                return leftNumber >= rightNumber;
            case le:
                return leftNumber <= rightNumber;
            default:
                throw new IllegalArgumentException("Not a boolean operator: " + operator);
        }
    }

    private void checkisBoolean(ValueList leftValueList, ValueList rightValueList) {
        EnumSet booleanTypes = EnumSet.of(PrimitiveType.Boolean);
        if(!booleanTypes.contains(leftValueList.getType())) {
            throw new RuntimeException("not a boolean with boolean operator: " + leftValueList.getType());//TODO: proper errors
        }
        if(!booleanTypes.contains(rightValueList.getType())) {
            throw new RuntimeException("not a boolean with boolean operator: " + rightValueList.getType());//TODO: proper errors
        }
    }

    private void checkIsNumber(ValueList leftValueList, ValueList rightValueList) {
        EnumSet numberTypes = EnumSet.of(PrimitiveType.Integer, PrimitiveType.Real);
        if(!numberTypes.contains(leftValueList.getType())) {
            throw new RuntimeException("Type supplied to operator should be a number, but it is not: " + leftValueList.getType());//TODO: proper errors
        }
        if(!numberTypes.contains(rightValueList.getType())) {
            throw new RuntimeException("Type supplied to operator should be a number, but it is not: " + rightValueList.getType());//TODO: proper errors
        }
    }

    private double convertToDouble(Object value) {
        return value instanceof  Double ? (Double) value : ((Long) value).doubleValue();
    }

    @Override
    public List<Class> getSupportedClasses() {
        return Lists.newArrayList(BinaryOperator.class);
    }
}
