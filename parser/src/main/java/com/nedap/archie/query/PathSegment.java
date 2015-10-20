package com.nedap.archie.query;

/**
 * Segment of an apath-query
 * Created by pieter.bos on 19/10/15.
 */
public class PathSegment {

    private String nodeName;
    private String nodeId;

    public PathSegment(String nodeName, String nodeId) {
        this.nodeName = nodeName;
        this.nodeId = nodeId;
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

    @Override
    public String toString() {
        return "/" + nodeName + "[" + nodeId + "]";
    }
}
