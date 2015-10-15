package com.nedap.archie.aom;

import com.nedap.archie.base.MultiplicityInterval;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CObject extends ArchetypeConstraint {

    private String rmTypeName;
    private MultiplicityInterval occurences;
    private String nodeId;
    private Boolean deprecated;

    public String getRmTypeName() {
        return rmTypeName;
    }

    public void setRmTypeName(String rmTypeName) {
        this.rmTypeName = rmTypeName;
    }

    public MultiplicityInterval getOccurences() {
        return occurences;
    }

    public void setOccurences(MultiplicityInterval occurences) {
        this.occurences = occurences;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }
}
