package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlParser;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class OdinValueParser {

    public static String parseOdinStringValue(AdlParser.String_valueContext context) {
        if(context == null) {
            return null;
        }
        String text = context.getText();
        //strip the quotes
        if(text.length() == 2) { // empty string, ""
            return "";
        }
        return text.substring(1, text.length() - 1);
    }
}
