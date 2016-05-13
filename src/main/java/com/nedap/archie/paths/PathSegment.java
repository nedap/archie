package com.nedap.archie.paths;

/**
 * Segment of an apath-query
 * Created by pieter.bos on 19/10/15.
 */
public class PathSegment {

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

        StringBuilder result = new StringBuilder("/");
        result.append(nodeName);
        if(hasExpressions()) {
            result.append("[");
            boolean first = true;
            if(nodeId != null) {
                result.append(nodeId);
                first = false;
            }
            if(index != null) {
                if(!first) {
                    result.append(", ");
                }
                result.append(Integer.toString(index));
            }
            result.append("]");
        }
        return result.toString();

    }

    public boolean hasExpressions() {
        return nodeId != null || index != null;
    }
}
