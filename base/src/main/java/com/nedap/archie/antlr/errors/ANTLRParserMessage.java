package com.nedap.archie.antlr.errors;

/**
 * An error, info or warning message from the archetype parsing
 *
 * Created by pieter.bos on 19/10/15.
 */
public class ANTLRParserMessage {

    private Integer lineNumber;
    private Integer columnNumber;
    private String message;

    public ANTLRParserMessage(String message) {
        this.message = message;
    }

    public ANTLRParserMessage(String message, Integer lineNumber, Integer columnNumber) {
        this.message = message;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }


    public String getMessage() {
        return message;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
