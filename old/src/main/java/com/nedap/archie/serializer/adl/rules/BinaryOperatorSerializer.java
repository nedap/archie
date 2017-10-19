package com.nedap.archie.serializer.adl.rules;


import com.nedap.archie.rules.BinaryOperator;
import com.nedap.archie.serializer.adl.ADLRulesSerializer;


/**
 * Created by pieter.bos on 15/06/16.
 */
public class BinaryOperatorSerializer extends RuleElementSerializer<BinaryOperator> {
    public BinaryOperatorSerializer(ADLRulesSerializer serializer) {
        super(serializer);
    }

    @Override
    public void serialize(BinaryOperator operator) {
        serializer.serializeRuleElement(operator.getLeftOperand());
        builder.append(" ");
        builder.append(operator.getOperator().getDefaultCode());
        builder.append(" ");
        serializer.serializeRuleElement(operator.getRightOperand());

    }
}
