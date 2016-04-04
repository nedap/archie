package com.nedap.archie.rules.evaluation;

import com.nedap.archie.rules.PrimitiveType;

/**
 * Created by pieter.bos on 31/03/16.
 */
public class Value {
    private PrimitiveType type;
    private Object value;

    public Value() {

    }

    public Value(Object value) {
        if(value == null) {
            this.value = null;
            this.type = null;
        } else {
            this.value = value;
            this.type = PrimitiveType.fromJavaType(value.getClass());
        }

    }

    public PrimitiveType getType() {
        return type;
    }

    public void setType(PrimitiveType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return type + ": " + value;
    }
}
