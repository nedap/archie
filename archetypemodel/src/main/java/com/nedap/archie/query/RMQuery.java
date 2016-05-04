package com.nedap.archie.query;

import com.google.common.collect.Lists;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.datavalues.DvText;
import org.apache.commons.jxpath.JXPathContext;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pieter.bos on 03/05/16.
 */
public class RMQuery {

    private String query;

    public RMQuery(String query) {
        this.query = query;
        Pattern pattern = Pattern.compile("\\[(?<first>[^\\]]*)(?<idnode>id\\d+)(?<last>[^\\]]*)\\]");
        Matcher m = pattern.matcher(query);

        this.query = m.replaceAll("[${first}@archetype_node_id='${idnode}'${last}]");

    }

    public <T> List<T> findList(RMObject object) {
        return Lists.newArrayList(JXPathContext.newContext(object).iterate(query));
    }

    public <T> T  find(RMObject object) {
        return (T) JXPathContext.newContext(object).getValue(query);
    }
}
