package com.nedap.archie.rules;

/**
 * Created by pieter.bos on 27/10/15.
 */
public class UnaryOperator extends Operator {

    public UnaryOperator() {

    }

    public UnaryOperator(ExpressionType type, OperatorKind operator, Expression operand) {
        setType(type);
        setOperator(operator);
        addOperand(operand);
    }
}
