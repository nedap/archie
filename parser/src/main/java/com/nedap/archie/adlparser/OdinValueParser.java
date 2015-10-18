package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlParser;

import java.net.URI;
import java.net.URISyntaxException;

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

    public static URI parseOdinUri(AdlParser.Uri_valueContext context) throws URISyntaxException {
        if(context == null) {
            return null;
        }
        String text = context.getText();
        //strip the quotes
        if(text.length() == 2) { // empty string, ""
            return null;
        }
        return new URI(text.substring(1, text.length() - 1));
    }
}
