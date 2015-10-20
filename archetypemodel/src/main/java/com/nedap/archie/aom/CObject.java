package com.nedap.archie.aom;

import com.nedap.archie.base.MultiplicityInterval;

import java.util.Collections;
import java.util.List;

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

    /**
     * Return the named attribute if this is a constrained complex object. Return null if there is no such named attribute,
     * or this is not a CComplexObject
     * @param name
     * @return
     */
    public CAttribute getAttribute(String name) {
        return null;
    }

    public List<CAttribute> getAttributes() {
        return Collections.EMPTY_LIST;
    }


    @Override
    public String getPath() {
        if(getParent() == null) {
            return "/";
        }
        String path = getParent().getPath() + "[" + nodeId + "]";
        if(path.startsWith("//")) {
            return path.substring(1);
        }
        return path;
    }
}
