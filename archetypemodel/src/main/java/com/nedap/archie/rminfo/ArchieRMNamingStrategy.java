package com.nedap.archie.rminfo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by pieter.bos on 29/03/16.
 */
public class ArchieRMNamingStrategy implements ModelNamingStrategy {

    protected PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy lowerCaseWithUnderscoresStrategy = new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy();

    @Override
    public String getRMTypeName(Class clazz) {
        String name = clazz.getSimpleName();
        switch(name) {
            //some overrides to get every name right. The DV_URI is not really required i think, just to be sure
            case "DvURI":
                return "DV_URI";
            case "DvEHRURI":
                return "DV_EHR_URI";
            case "UIDBasedId":
                return "UID_BASED_ID";
            default:

        }
        return lowerCaseWithUnderscoresStrategy.translate(clazz.getSimpleName()).toUpperCase();
    }

    @Override
    public String getRMAttributeName(Class clazz, Method getMethod) {
        String methodName = getMethod.getName();
        return lowerCaseWithUnderscoresStrategy.translate(methodName).toUpperCase();
    }

    @Override
    public String getRMAttributeName(Field field) {
        return lowerCaseWithUnderscoresStrategy.translate(field.getName());
    }
}
