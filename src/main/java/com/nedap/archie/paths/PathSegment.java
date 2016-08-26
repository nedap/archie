package com.nedap.archie.paths;

import com.google.common.base.Joiner;

/**
 * Segment of an apath-query
 * Created by pieter.bos on 19/10/15.
 */
public class PathSegment {
    private static Joiner expressionJoiner = Joiner.on(", ").skipNulls();

    private String nodeName;
    private String nodeId;
    private Integer index;

    public PathSegment(String nodeName, Integer index) {
        this(nodeName, null, index);
    }

    public PathSegment(String nodeName, String nodeId) {
        this(nodeName, nodeId, null);
    }

    public PathSegment(String nodeName) {
        this(nodeName, null, null);
    }

    public PathSegment(String nodeName, String nodeId, Integer index) {
        this.nodeName = nodeName;
        this.nodeId = nodeId;
        this.index = index;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public boolean hasIdCode() {
        return nodeId != null && nodeId.matches("id(\\.?\\d)+");
    }

    public boolean hasNumberIndex() { return index != null;}

    public boolean hasArchetypeRef() {
        return nodeId != null && nodeId.matches("(.*::)?.*-.*-.*\\..*\\.v.*");
    }

    @Override
    public String toString() {
        if(hasExpressions()) {
            return String.format("/%s[%s]", nodeName, expressionJoiner.join(nodeId, index));
        } else {
            return String.format("/%s", nodeName);
        }
    }

    public boolean hasExpressions() {
        return nodeId != null || index != null;
    }
}
