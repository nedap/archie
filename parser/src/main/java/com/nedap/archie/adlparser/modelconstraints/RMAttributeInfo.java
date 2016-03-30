package com.nedap.archie.adlparser.modelconstraints;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by pieter.bos on 25/03/16.
 */
public class RMAttributeInfo {
    private final String name;
    private final Field field;
    private final Class type;
    private final Method getMethod;
    private final Method setMethod;
    private final Method addMethod;
    private final boolean nullable;

    public RMAttributeInfo(String name, Field field, Class type, boolean nullable, Method getMethod, Method setMethod, Method addMethod) {
        this.name = name;
        this.field = field;
        this.type = type;
        this.nullable = nullable;
        this.getMethod = getMethod;
        this.setMethod = setMethod;
        this.addMethod = addMethod;
    }

    public String getRmName() {
        return name;
    }

    public Method getGetMethod() {
        return getMethod;
    }

    public Method getSetMethod() {
        return setMethod;
    }

    public Method getAddMethod() {
        return addMethod;
    }

    public Field getField() {
        return field;
    }

    public Class getType() {
        return type;
    }

    public boolean isNullable() {
        return nullable;
    }

}
