package com.nedap.archie.adlparser;

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

    public static URI parseOdinUri(AdlParser.Uri_valueContext context) throws URISyntaxException {
        if(context == null) {
            return null;
        }
        return new URI(context.getText());
    }

    public static List<String> parseListOfStrings(AdlParser.Primitive_objectContext listContext) {
        List<String> result = new ArrayList<>();
        if(listContext.primitive_value() != null) {
            result.add(parseOdinStringValue(listContext.primitive_value().string_value()));
        } else if (listContext.primitive_list_value() != null) {
            for(AdlParser.String_valueContext stringContext: listContext.primitive_list_value().string_list_value().string_value()) {
                result.add(parseOdinStringValue(stringContext));
            }
        }
        return result;
    }
}
