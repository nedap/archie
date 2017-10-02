package com.nedap.archie.flattener;

import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NodeId {
    private String prefix;
    private int id;
    private List<Integer> suffixCodes;

    private static Pattern nodeIdPattern = Pattern.compile("(id|at|ac)(\\d+)(\\.\\d+)*");

    public static NodeId parse(String nodeId) {
        NodeId result = new NodeId();
        Matcher matcher = nodeIdPattern.matcher(nodeId);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("cannot parse nodeid: " + nodeId);
        }
        result.prefix = matcher.group(0);
        result.id = Integer.parseInt(matcher.group(1));
        return result;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getSuffixCodes() {
        return suffixCodes;
    }


}
