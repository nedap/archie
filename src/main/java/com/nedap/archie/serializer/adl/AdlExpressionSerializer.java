package com.nedap.archie.serializer.adl;

import com.google.common.collect.ImmutableMap;
import com.nedap.archie.rules.BinaryOperator;
import com.nedap.archie.rules.Constraint;
import com.nedap.archie.rules.Expression;
import com.nedap.archie.rules.ModelReference;

import java.util.Map;
import java.util.function.Function;

/**
 * @author markopi
 */
public class ADLExpressionSerializer {
    private static final Map<Class<? extends Expression>, Function<Expression, String>> SERIALIZERS = ImmutableMap.
            <Class<? extends Expression>, Function<Expression, String>>builder()
            .put(ModelReference.class, e -> ser((ModelReference) e))
            .put(BinaryOperator.class, e -> ser((BinaryOperator) e))
            .put(Constraint.class, e -> ser((Constraint) e))
            .build();

    private static String ser(ModelReference e) {
        return e.toString();
    }

    private static String ser(Constraint e) {
        if (e.getItem() == null) return "null";
        return ADLDefinitionSerializer.serialize(e.getItem());
    }

    private static String ser(BinaryOperator e) {
        final String operatorCode = e.getOperator() != null ? e.getOperator().getDefaultCode() : null;
        return "" + serialize(e.getLeftOperand()) + " " + operatorCode + " {" + serialize(e.getRightOperand()) + "}";
    }


    public static String serialize(Expression e) {
        if (e == null) return "null";

        Function<Expression, String> serializer = SERIALIZERS.get(e.getClass());
        if (serializer == null) {
            throw new AssertionError("No serializer for " + e.getClass());
        }
        return serializer.apply(e);

    }

}
