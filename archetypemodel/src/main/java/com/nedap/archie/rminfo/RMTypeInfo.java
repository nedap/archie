package com.nedap.archie.rminfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pieter.bos on 25/03/16.
 */
public class RMTypeInfo {

    private final String rmName;
    private final Class javaClass;
    private Map<String, RMAttributeInfo> attributes = new HashMap<>();

    public RMTypeInfo(Class clazz, String rmName) {
        this.javaClass = clazz;
        this.rmName = rmName;
    }

    public String getRmName() {
        return rmName;
    }

    public Class getJavaClass() {
        return javaClass;
    }

    public Map<String, RMAttributeInfo> getAttributes() {
        return attributes;
    }

    public void addAttribute(RMAttributeInfo attribute) {
        attributes.put(attribute.getRmName(), attribute);
    }

    public RMAttributeInfo getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }
}
