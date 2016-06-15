package com.nedap.archie.serializer.adl.rules;

import com.nedap.archie.rules.ExpressionVariable;
import com.nedap.archie.rules.VariableDeclaration;
import com.nedap.archie.serializer.adl.ADLRulesSerializer;
import com.nedap.archie.serializer.adl.ADLStringBuilder;

/**
 * Created by pieter.bos on 15/06/16.
 */
public class ExpressionVariableDeclarationSerializer extends RuleElementSerializer<ExpressionVariable> {

    public ExpressionVariableDeclarationSerializer(ADLRulesSerializer serializer) {
        super(serializer);
    }

    @Override
    public void serialize(ExpressionVariable variableDeclaration) {

        builder.append("$");
        builder.append(variableDeclaration.getName());
        builder.append(":");
        builder.append(variableDeclaration.getType().toString());
        builder.append(" ::= ");
        serializer.serializeRuleElement(variableDeclaration.getExpression());
        builder.append(";");
        builder.newline();

    }
}
