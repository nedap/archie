package com.nedap.archie.query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts an APath to an XPath-query
 *
 * Created by pieter.bos on 11/05/16.
 */
public class APathToXPathConverter {

    public static String convertQueryToXPath(String query, String firstNodeName) {
        Pattern pattern = Pattern.compile("\\[(?<first>[^\\]]*)(?<idnode>id\\d+)(?<last>[^\\]]*)\\]");
        Matcher m = pattern.matcher(query);

        query = m.replaceAll("[${first}@archetype_node_id='${idnode}'${last}]");
        if(query.startsWith("/")) {
            return "/" + firstNodeName + query;
        } else {
            return query;
        }
    }
}
