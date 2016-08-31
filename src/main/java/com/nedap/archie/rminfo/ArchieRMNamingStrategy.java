package com.nedap.archie.rminfo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by pieter.bos on 29/03/16.
 */
public class ArchieRMNamingStrategy implements ModelNamingStrategy {

    protected PropertyNamingStrategy.SnakeCaseStrategy snakeCaseStrategy = new PropertyNamingStrategy.SnakeCaseStrategy();

    @Override
    public String getRMTypeName(Class clazz) {
        String name = clazz.getSimpleName();
        switch(name) {
            case "DvEHRURI":
                return "DV_EHR_URI";
            case "UIDBasedId":
                return "UID_BASED_ID";
            default:

        }
        String result = snakeCaseStrategy.translate(clazz.getSimpleName()).toUpperCase();
        if(name.length() > 1 && name.startsWith("C") && Character.isUpperCase(name.charAt(1))) {
            result = result.replaceFirst("C", "C_");
        }
        return result;
    }

    @Override
    public String getRMAttributeName(Class clazz, Method getMethod) {
        String methodName = getMethod.getName();
        return snakeCaseStrategy.translate(methodName).toUpperCase();
    }

    @Override
    public String getRMAttributeName(Field field) {
        return snakeCaseStrategy.translate(field.getName());
    }
}
