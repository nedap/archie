package com.nedap.archie.adlparser;

/**
 * An error, info or warning message from the archetype parsing
 *
 * Created by pieter.bos on 19/10/15.
 */
public class ADLParserMessage {

    private String message;

    public ADLParserMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
