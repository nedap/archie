package com.nedap.archie.rm.archetypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nedap.archie.query.APathQuery;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class Pathable extends RMObject {
    //TODO: implement according to spec: pathExists(path), pathUnique(path), pathOfItem(pathable)

    @JsonIgnore
    @XmlTransient
    private Pathable parent;

    public Object itemAtPath(String s) {
        return new APathQuery(s).find(ArchieRMInfoLookup.getInstance(), this);
    }

    public List<Object> itemsAtPath(String s) {
        return new APathQuery(s).findList(ArchieRMInfoLookup.getInstance(), this);
    }

    @JsonIgnore
    @XmlTransient
    public Pathable getParent() {
        return parent;
    }

    public void setParent(Pathable parent) {
        this.parent = parent;
    }

    /**
    * Utility method to set this object as the parent of the given child,
    * if the child is not null
    */
    protected void setThisAsParent(Pathable child) {
        if(child != null) {
            child.setParent(this);
        }
    }

}
