package com.nedap.archie.flattener;

import com.google.common.base.Strings;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.RulesSection;
import com.nedap.archie.rules.*;
import com.nedap.archie.serializer.adl.rules.ExpressionVariableDeclarationSerializer;

import java.util.Objects;

/**
 * Created by pieter.bos on 12/05/2017.
 */
public class RulesFlattener {
    /**
     * Combine the rules for two archetypes at the same level - no path prefix. The rules of destination will be appened
     * with the rules from source.
     * Variable names and tag names will get a prefix to make them unique (TODO: this is not perfect)
     * Model references will be prefixed with pathPrefix to make them work in the new resulting archetype
     *
     * TODO: check that the remaining variables names are actually unique and fix them if they are not
     * @param source
     * @param destination
     */
    public void combineRules(Archetype source, Archetype destination, String variablePrefix, String tagPrefix, String pathPrefix, boolean overWriteTagsWithSameName) {
        if(source.getRules() == null || source.getRules().getRules().isEmpty()) {
            return;
        }
        if(destination.getRules() == null) {
            destination.setRules(new RulesSection());
        }
        for(RuleStatement statement:source.getRules().getRules()) {
            RuleStatement clonedStatement = (RuleStatement) statement.clone();
            addVariableAndTagPrefix(clonedStatement, variablePrefix, tagPrefix, pathPrefix);
            if(overWriteTagsWithSameName) {
                if(clonedStatement instanceof Assertion) {
                    overWriteStatementWithSameTag(destination, clonedStatement);
                } else {
                    destination.getRules().getRules().add(clonedStatement);
                }

            } else {
                destination.getRules().getRules().add(clonedStatement);
            }
        }
    }

    private void overWriteStatementWithSameTag(Archetype destination, RuleStatement clonedStatement) {
        Assertion clonedAssertion = (Assertion) clonedStatement;
        if(Strings.isNullOrEmpty(clonedAssertion.getTag())) {
            destination.getRules().getRules().add(clonedStatement);
        } else {
            int ruleWithTagIndex = findRuleWithTag(destination.getRules(), clonedAssertion.getTag());
            if(ruleWithTagIndex > -1) {
                destination.getRules().getRules().set(ruleWithTagIndex, clonedAssertion);
            } else {
                destination.getRules().getRules().add(clonedStatement);
            }
        }
    }

    private int findRuleWithTag(RulesSection rules, String tag) {
        int i = 0;
        for(RuleStatement statement:rules.getRules()) {
            if(statement instanceof Assertion && Objects.equals(tag, ((Assertion) statement).getTag())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private void addVariableAndTagPrefix(RuleStatement clonedStatement, String variablePrefix, String tagPrefix, String pathPrefix) {
        if(clonedStatement instanceof Assertion) {
            Assertion assertion = (Assertion) clonedStatement;
            if(!Strings.isNullOrEmpty(assertion.getTag())) {
                assertion.setTag(tagPrefix + assertion.getTag());
            }
            addVariableAndTagPrefixToExpression(assertion.getExpression(), variablePrefix, pathPrefix);
        } else if (clonedStatement instanceof ExpressionVariable) {
            ExpressionVariable declaration = (ExpressionVariable) clonedStatement;
            declaration.setName(variablePrefix + declaration.getName());
            addVariableAndTagPrefixToExpression(declaration.getExpression(), variablePrefix, pathPrefix);
        }
    }

    private void addVariableAndTagPrefixToExpression(Expression expression, String variablePrefix, String pathPrefix) {
        if(expression instanceof BinaryOperator) {
            BinaryOperator operator = (BinaryOperator) expression;
            addVariableAndTagPrefixToExpression(operator.getLeftOperand(), variablePrefix, pathPrefix);
            addVariableAndTagPrefixToExpression(operator.getRightOperand(), variablePrefix, pathPrefix);
        } else if (expression instanceof ForAllStatement) {
            ForAllStatement forAllStatement = (ForAllStatement) expression;
            forAllStatement.setVariableName(variablePrefix + forAllStatement.getVariableName());
            addVariableAndTagPrefixToExpression(forAllStatement.getPathExpression(), variablePrefix, pathPrefix);
            addVariableAndTagPrefixToExpression(forAllStatement.getAssertion(), variablePrefix, pathPrefix);
        } else if(expression instanceof ModelReference) {
            ModelReference reference = (ModelReference) expression;
            if(reference.getVariableReferencePrefix() != null) {
                reference.setVariableReferencePrefix(variablePrefix + reference.getVariableReferencePrefix());
            } else {
                reference.setPath(pathPrefix + reference.getPath());
            }
        } else if (expression instanceof UnaryOperator) {
            UnaryOperator operator = (UnaryOperator) expression;
            addVariableAndTagPrefixToExpression(operator.getOperand(), variablePrefix, pathPrefix);
        } else if (expression instanceof VariableReference) {
            VariableReference reference = (VariableReference) expression;
            reference.getDeclaration().setName(variablePrefix + reference.getDeclaration().getName());
        }


    }
}
