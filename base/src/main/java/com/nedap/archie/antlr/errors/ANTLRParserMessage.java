package com.nedap.archie.antlr.errors;

/**
 * An error, info or warning message from the archetype parsing
 *
 * Created by pieter.bos on 19/10/15.
 */
public class ANTLRParserMessage {

    private String message;

    public ANTLRParserMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
