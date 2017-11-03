package com.nedap.archie.rminfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by pieter.bos on 25/03/16.
 */
public class RMAttributeInfo {
    private final String name;
    private final Field field;
    private final Class type;
    private final Class typeInCollection;
    private final boolean isMultipleValued;
    private final Method getMethod;
    private final Method setMethod;
    private final Method addMethod;
    private final boolean nullable;

    public RMAttributeInfo(String name, Field field, Class type, Class typeInCollection, boolean nullable, Method getMethod, Method setMethod, Method addMethod) {
        this.name = name;
        this.field = field;
        this.type = type;
        this.nullable = nullable;
        this.getMethod = getMethod;
        this.setMethod = setMethod;
        this.addMethod = addMethod;
        this.isMultipleValued = type instanceof Class && Collection.class.isAssignableFrom(type);
        this.typeInCollection = typeInCollection;
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

    public boolean isMultipleValued() {
        return isMultipleValued;
    }

    public Class getType() {
        return type;
    }

    public boolean isNullable() {
        return nullable;
    }

    /**
     * If isMultipleValued == true, this will return the type used in the collection, eg if the the collection is List<String>, this will return String.class
     * @return
     */
    public Class getTypeInCollection() {
        return typeInCollection;
    }
}
