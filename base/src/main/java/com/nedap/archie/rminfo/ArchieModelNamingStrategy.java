package com.nedap.archie.rminfo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.lang.reflect.Field;

/**
 * Created by pieter.bos on 29/03/16.
 * A naming helper for both Archie RM and AOM objects
 */
public class ArchieModelNamingStrategy implements ModelNamingStrategy {

    protected PropertyNamingStrategy.SnakeCaseStrategy snakeCaseStrategy = new PropertyNamingStrategy.SnakeCaseStrategy();

    @Override
    public String getTypeName(Class clazz) {
        // For some RM objects the name is an exception on the snakecase -> uppercase strategy
        String name = clazz.getSimpleName();
        switch(name) {
            case "DvEHRURI":
                return "DV_EHR_URI";
            case "UIDBasedId":
                return "UID_BASED_ID";
            default:

        }
        String result = snakeCaseStrategy.translate(clazz.getSimpleName()).toUpperCase();

        // For some AOM objects (ie. CComplexObject and CAttribute), the name cannot be gotten
        // through the normal snakecase -> uppercase strategy
        if(name.length() > 1 && name.startsWith("C") && Character.isUpperCase(name.charAt(1))) {
            result = result.replaceFirst("C", "C_");
        }
        return result;
    }

    @Override
    public String getAttributeName(Field field) {
        return snakeCaseStrategy.translate(field.getName());
    }
}
