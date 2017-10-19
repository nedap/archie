package com.nedap.archie.serializer.adl.rules;

import com.nedap.archie.rules.Constant;
import com.nedap.archie.serializer.adl.ADLRulesSerializer;

/**
 * Created by pieter.bos on 15/06/16.
 */
public class ConstantSerializer extends RuleElementSerializer<Constant> {
    public ConstantSerializer(ADLRulesSerializer adlRulesSerializer) {
        super(adlRulesSerializer);
    }

    @Override
    public void serialize(Constant ruleElement) {
        builder.append(ruleElement.getValue());//TODO: this works for strings, booleans, doubles and numbers. More needed?
    }
}
