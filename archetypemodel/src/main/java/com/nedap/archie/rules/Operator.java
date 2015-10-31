package com.nedap.archie.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 27/10/15.
 */
public class Operator  extends Expression {

    private OperatorKind operator;
    private boolean precedenceOverridden = false;

    private List<Expression> operands = new ArrayList<>();


    public OperatorKind getOperator() {
        return operator;
    }

    public void setOperator(OperatorKind operator) {
        this.operator = operator;
    }

    public boolean isPrecedenceOverridden() {
        return precedenceOverridden;
    }

    public void setPrecedenceOverridden(boolean precedenceOverridden) {
        this.precedenceOverridden = precedenceOverridden;
    }

    public List<Expression> getOperands() {
        return operands;
    }

    public void setOperands(List<Expression> operands) {
        this.operands = operands;
    }

    public Expression getLeftOperand() {
        return operands.size() > 0 ? operands.get(0) : null;
    }

    public Expression getRightOperand() {
        return operands.size() > 1 ? operands.get(1) : null;
    }

    public boolean isUnary() {
        return operands.size() == 1;
    }

    public void addOperand(Expression expression) {
        operands.add(expression);
    }
}
