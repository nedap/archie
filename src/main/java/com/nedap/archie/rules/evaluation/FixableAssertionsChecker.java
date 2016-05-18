package com.nedap.archie.rules.evaluation;

import com.google.common.collect.ArrayListMultimap;
import com.nedap.archie.rules.BinaryOperator;
import com.nedap.archie.rules.Expression;
import com.nedap.archie.rules.ForAllStatement;
import com.nedap.archie.rules.ModelReference;
import com.nedap.archie.rules.OperatorKind;
import com.nedap.archie.rules.RuleElement;
import com.nedap.archie.rules.UnaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Checks evaluated assertions on what can be fixed automatically, and how this can be done.
 *
 * Created by pieter.bos on 18/05/16.
 */
class FixableAssertionsChecker {

    private static Logger logger = LoggerFactory.getLogger(FixableAssertionsChecker.class);

    ArrayListMultimap<RuleElement, ValueList> ruleElementValues;

    protected FixableAssertionsChecker(ArrayListMultimap<RuleElement, ValueList> ruleElementValues) {
        this.ruleElementValues = ruleElementValues;
    }

    /**
     * Check the assertion result for patterns that can be automatically fixed so they evaluate to true. Currently the following patterns can be automatically fixed:
     *
     * /path/value = ...expression...
     * .... implies _another matched pattern_
     * exists .....
     * not exists ....
     * for all .. in ... satisfies _another matched pattern
     *
     *
     * @param evaluationResult the evaluationresult to set the fix instructions to
     * @param expression the expression to be evaluated
     @param expression the current index, if in a for all expression. 0 (-1? TODO) otherwise
     */
    protected void checkAssertionForFixablePatterns(EvaluationResult evaluationResult, Expression expression, int index) {

        if(expression instanceof ForAllStatement) {
            handleForAll(evaluationResult, expression);

        } else if (expression instanceof BinaryOperator) {
            ValueList expressionResult = ruleElementValues.get(expression).get(index);
            BinaryOperator binaryExpression = (BinaryOperator) expression;
            if (binaryExpression.getOperator() == OperatorKind.eq && binaryExpression.getLeftOperand() instanceof ModelReference) {
                handlePathEquals(evaluationResult, expressionResult, binaryExpression, index);
            } else if (binaryExpression.getOperator() == OperatorKind.exists) {
                //TODO exists expressions

                if(binaryExpression.getRightOperand() instanceof ModelReference) {
                    //matches exists /path/to/value
                    //TODO: this shows that a specific archetype path must exist. But it clould just as well be a path within a specific node. So find a way to get the RM Path
                    //pointing to the right node here
                    evaluationResult.addPathThatMustExist(((ModelReference) binaryExpression.getRightOperand()).getPath());
                }
            } else if (binaryExpression.getOperator() == OperatorKind.implies) {
                handleImplies(evaluationResult, index, binaryExpression);
            }
        } else if (expression instanceof UnaryOperator) {
            UnaryOperator unaryOperator = (UnaryOperator) expression;
            if(unaryOperator.getOperator() == OperatorKind.not) {
                handleNot(evaluationResult, unaryOperator);
            }
        }

        //TODO: not expressions, reversing the expected value?
    }

    private void handleNot(EvaluationResult evaluationResult, UnaryOperator unaryOperator) {
        Expression operand = unaryOperator.getOperand();
        if(operand instanceof BinaryOperator && ((BinaryOperator) operand).getOperator() == OperatorKind.exists) {
            BinaryOperator binaryOperator = (BinaryOperator) operand;
            if(binaryOperator.getRightOperand() instanceof  ModelReference) {
                //matches exists /path/to/value
                evaluationResult.addPathThatMustNotExist(((ModelReference) binaryOperator.getRightOperand()).getPath());
            }
        }
    }

    private void handleImplies(EvaluationResult evaluationResult, int index, BinaryOperator binaryExpression) {
        //matches ... implies ...
        ValueList leftOperandResult = ruleElementValues.get(binaryExpression.getLeftOperand()).get(index);
        boolean shouldEvaluate = leftOperandResult.getSingleBooleanResult();
        if(shouldEvaluate) {
            checkAssertionForFixablePatterns(evaluationResult, binaryExpression.getRightOperand(), index);
        }
    }

    private void handlePathEquals(EvaluationResult evaluationResult, ValueList expressionResult, BinaryOperator binaryExpression, int index) {
        //matches the form /path/to/something = 3 + 5 * /value[id23]
        ValueList valueList = this.ruleElementValues.get(binaryExpression.getRightOperand()).get(index);
        ModelReference pathToSet = (ModelReference) binaryExpression.getLeftOperand();
        setPathsToValues(evaluationResult, pathToSet.getPath(), valueList);
    }


    private void handleForAll(EvaluationResult evaluationResult, Expression expression) {
        //this handles forAll ..., even with an extra for all.
        //TODO: the nested loop forall .. in .. forall .. in .. will not yet work due to problems with the ruleElementValues. Refactor!
        ForAllStatement forAllStatement = (ForAllStatement) expression;
        Collection<ValueList> valueLists = ruleElementValues.get(forAllStatement.getAssertion());
        int i = 0;
        for(ValueList valueList:valueLists) {
            if(!valueList.getSingleBooleanResult()) {
                checkAssertionForFixablePatterns(evaluationResult, forAllStatement.getRightOperand(), i);
            }
            i++;
        }
    }

    private void setPathsToValues(EvaluationResult evaluationResult, String path, ValueList value) {
        logger.info("path {} set to value {} ", path, value);
        evaluationResult.setSetPathValue(path, value);
    }
}
