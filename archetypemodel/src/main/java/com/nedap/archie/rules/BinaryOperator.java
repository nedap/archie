package com.nedap.archie.rules;

/**
 * Created by pieter.bos on 27/10/15.
 */
public class BinaryOperator extends Operator {

    public BinaryOperator() {

    }

    public BinaryOperator(ExpressionType type, OperatorKind operator, Expression leftOperand, Expression rightOperand) {
        setType(type);
        setOperator(operator);
        addOperand(leftOperand);
        addOperand(rightOperand);
    }
}
