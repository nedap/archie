package com.nedap.archie.adlparser.odin;

import com.nedap.archie.adlparser.antlr.AdlParser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class OdinValueParser {

    public static String parseOdinStringValue(AdlParser.String_valueContext context) {
        if(context == null) {
            return null;
        }
        String text = context.getText();
        //regexps
        if(text.startsWith("/")) {
            return text;
        }
        if(text.startsWith("^")) {
            return text;
        }

        if(!text.startsWith("\"")) {
            throw new IllegalArgumentException("text should start with '/', '^' or '\"'");
        }
        //strip the quotes
        if(text.length() == 2) { // empty string, ""
            return "";
        }
        return text.substring(1, text.length() - 1);
    }
}
