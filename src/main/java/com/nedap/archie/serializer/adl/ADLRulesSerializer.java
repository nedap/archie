package com.nedap.archie.serializer.adl;

import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.RulesSection;
import com.nedap.archie.rules.Assertion;
import com.nedap.archie.rules.Constraint;
import com.nedap.archie.rules.Expression;
import com.nedap.archie.rules.ExpressionVariable;
import com.nedap.archie.rules.ModelReference;
import com.nedap.archie.rules.RuleElement;
import com.nedap.archie.rules.RuleStatement;
import com.nedap.archie.rules.UnaryOperator;

import com.nedap.archie.rules.VariableReference;
import com.nedap.archie.serializer.adl.rules.AssertionSerializer;
import com.nedap.archie.serializer.adl.rules.BinaryOperatorSerializer;
import com.nedap.archie.serializer.adl.rules.ConstraintSerializer;
import com.nedap.archie.serializer.adl.rules.ModelReferenceSerializer;
import com.nedap.archie.serializer.adl.rules.RuleElementSerializer;
import com.nedap.archie.serializer.adl.rules.UnaryOperatorSerializer;
import com.nedap.archie.serializer.adl.rules.VariableReferenceSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

/**
 * Created by pieter.bos on 15/06/16.
 */
public class ADLRulesSerializer {

    private ADLStringBuilder builder;
    private ADLDefinitionSerializer definitionSerializer;

    private final Map<Class, RuleElementSerializer> ruleElementSerializers;

    public ADLRulesSerializer(ADLStringBuilder builder, ADLDefinitionSerializer definitionSerializer) {
        this.builder = builder;
        this.definitionSerializer = definitionSerializer;

        ruleElementSerializers = new HashMap();
        ruleElementSerializers.put(UnaryOperator.class, new UnaryOperatorSerializer(this));
        ruleElementSerializers.put(BinaryOperator.class, new BinaryOperatorSerializer(this));
        ruleElementSerializers.put(Assertion.class, new AssertionSerializer(this));
        ruleElementSerializers.put(ExpressionVariable.class, new AssertionSerializer(this));
        ruleElementSerializers.put(ModelReference.class, new ModelReferenceSerializer(this));
        ruleElementSerializers.put(Constraint.class, new ConstraintSerializer(this));
        ruleElementSerializers.put(VariableReference.class, new VariableReferenceSerializer(this));

    }

    public ADLStringBuilder getBuilder() {
        return builder;
    }

    public void serializeRuleElement(RuleElement element) {
        RuleElementSerializer serializer = getSerializer(element);
        if (serializer != null) {
            serializer.serialize(element);
        } else {
            throw new AssertionError("Unsupported rule element: " + element.getClass().getName());
        }
    }

    private RuleElementSerializer getSerializer(RuleElement element) {
        return ruleElementSerializers.get(element);
    }

    public ADLDefinitionSerializer getDefinitionSerializer() {
        return definitionSerializer;
    }

    public void appendRules(RulesSection rules) {
        for(RuleStatement rule:rules.getRules()) {
            serializeRuleElement(rule);
        }
    }
}
