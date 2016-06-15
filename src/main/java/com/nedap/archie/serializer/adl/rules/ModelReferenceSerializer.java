package com.nedap.archie.serializer.adl.rules;

import com.nedap.archie.rules.ModelReference;
import com.nedap.archie.serializer.adl.ADLRulesSerializer;
import com.nedap.archie.serializer.adl.ADLStringBuilder;

import java.util.function.BinaryOperator;

/**
 * Created by pieter.bos on 15/06/16.
 */
public class ModelReferenceSerializer extends RuleElementSerializer<ModelReference> {


    public ModelReferenceSerializer(ADLRulesSerializer serializer) {
        super(serializer);
    }

    @Override
    public void serialize(ModelReference reference) {
        if(reference.getVariableReferencePrefix() != null) {
            builder.append("$");
            builder.append(reference.getVariableReferencePrefix());
        }
        builder.append(reference.getPath());
    }

}
