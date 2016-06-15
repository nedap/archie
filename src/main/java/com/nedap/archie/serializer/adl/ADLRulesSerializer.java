package com.nedap.archie.serializer.adl;

import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.RulesSection;
import com.nedap.archie.rules.*;

import com.nedap.archie.serializer.adl.rules.*;

import java.util.HashMap;
import java.util.Map;


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
        ruleElementSerializers.put(ExpressionVariable.class, new ExpressionVariableDeclarationSerializer(this));
        ruleElementSerializers.put(ModelReference.class, new ModelReferenceSerializer(this));
        ruleElementSerializers.put(Constraint.class, new ConstraintSerializer(this));
        ruleElementSerializers.put(VariableReference.class, new VariableReferenceSerializer(this));
        ruleElementSerializers.put(Constant.class, new ConstantSerializer(this));
        ruleElementSerializers.put(ForAllStatement.class, new ForAllStatementSerializer(this));

    }

    public ADLStringBuilder getBuilder() {
        return builder;
    }

    public void serializeRuleElement(RuleElement element) {
        RuleElementSerializer serializer = getSerializer(element);
        if (serializer != null) {
            boolean shouldSerializeParentheses = isPrecedenceOverride(element);
            if(shouldSerializeParentheses) {
                builder.append(" (");
            }
            serializer.serialize(element);
            if(shouldSerializeParentheses) {
                builder.append(") ");
            }
        } else {
            throw new AssertionError("Unsupported rule element: " + element.getClass().getName());
        }
    }

    private boolean isPrecedenceOverride(RuleElement element) {
        return element instanceof Expression && ((Expression) element).isPrecedenceOverriden();
    }

    private RuleElementSerializer getSerializer(RuleElement element) {
        return ruleElementSerializers.get(element.getClass());
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
