package com.nedap.archie.serializer.odin;

import com.nedap.archie.adlparser.antlr.AdlParser;
import org.apache.commons.text.StringEscapeUtils;

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
        return StringEscapeUtils.unescapeJava(text.substring(1, text.length() - 1));
    }
}
