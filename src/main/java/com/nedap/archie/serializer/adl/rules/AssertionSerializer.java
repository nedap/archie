package com.nedap.archie.serializer.adl.rules;

import com.nedap.archie.rules.Assertion;
import com.nedap.archie.rules.VariableDeclaration;
import com.nedap.archie.serializer.adl.ADLRulesSerializer;

/**
 * Created by pieter.bos on 15/06/16.
 */
public class AssertionSerializer extends RuleElementSerializer<Assertion> {

    public AssertionSerializer(ADLRulesSerializer serializer) {
        super(serializer);
    }

    @Override
    public void serialize(Assertion ruleElement) {
        if(ruleElement.getTag() != null) {
            builder.append(ruleElement.getTag());
            builder.append(": ");
        }
        if(!ruleElement.getVariables().isEmpty()) {
            for(VariableDeclaration variable:ruleElement.getVariables()) {
                serializer.serializeRuleElement(variable);
                builder.append(";");
                builder.newline();
            }
        } else {
            serializer.serializeRuleElement(ruleElement.getExpression());
            builder.append(";");
            builder.newline();
        }
    }
}
