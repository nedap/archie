package com.nedap.archie.rules;

/**
 * TODO: this should contain all primitive types and primitive types should be merged into this
 * Created by pieter.bos on 27/10/15.
 */
public enum ExpressionType {
     BOOLEAN, STRING, INTEGER, REAL;

    public static ExpressionType fromString(String string) {
        switch(string) {
            case "Boolean":
                return BOOLEAN;
            case "String":
                return STRING;
            case "Integer":
                return INTEGER;
            case "Real":
                return REAL;
        }
        return null;
    }
}
