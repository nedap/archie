package com.nedap.archie.aom;

/**
 * Created by pieter.bos on 22/10/15.
 */
public class SiblingOrder extends ArchetypeModelObject {

    private boolean before;
    private String siblingNodeId;

    public boolean isBefore() {
        return before;
    }

    public void setBefore(boolean before) {
        this.before = before;
    }

    public String getSiblingNodeId() {
        return siblingNodeId;
    }

    public void setSiblingNodeId(String siblingNodeId) {
        this.siblingNodeId = siblingNodeId;
    }

    public static SiblingOrder createAfter(String nodeId) {
        SiblingOrder result = new SiblingOrder();
        result.setSiblingNodeId(nodeId);
        result.setBefore(false);
        return result;
    }

    public static SiblingOrder createBefore(String nodeId) {
        SiblingOrder result = new SiblingOrder();
        result.setSiblingNodeId(nodeId);
        result.setBefore(true);
        return result;
    }
}
