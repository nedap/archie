package com.nedap.archie.rm.archetypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nedap.archie.paths.PathSegment;
import com.nedap.archie.paths.PathUtil;
import com.nedap.archie.query.APathQuery;
import com.nedap.archie.query.RMObjectWithPath;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class Pathable extends RMObject {
    //TODO: implement according to spec: pathExists(path), pathUnique(path), pathOfItem(pathable)

    @JsonIgnore
    @XmlTransient
    @Nullable
    private Pathable parent;
    @JsonIgnore
    @XmlTransient
    @Nullable
    private String parentAttributeName;

    public Object itemAtPath(String s) {
        return new APathQuery(s).find(ArchieRMInfoLookup.getInstance(), this);
    }

    public List<Object> itemsAtPath(String s) {
        List<RMObjectWithPath> objects = new APathQuery(s).findList(ArchieRMInfoLookup.getInstance(), this);
        List<Object> result = new ArrayList<>();
        for(RMObjectWithPath object:objects) {
            result.add(object.getObject());
        }
        return result;
    }

    @JsonIgnore
    @XmlTransient
    public Pathable getParent() {
        return parent;
    }

    private void setParent(Pathable parent) {
        this.parent = parent;
    }

    private void setParentAttributeName(String parentAttributeName) {
        this.parentAttributeName = parentAttributeName;
    }

    /**
    * Utility method to set this object as the parent of the given child,
    * if the child is not null
    */
    protected void setThisAsParent(Pathable child, String attributeName) {
        if(child != null) {
            child.setParent(this);
            child.setParentAttributeName(attributeName);
        }
    }

    protected String getParentAttributeName() {
        return parentAttributeName;
    }

    /**
     * Return the path of an item relative to this item
     * @param item
     * @return
     */
//    public String pathOfItem(Pathable item) {
//
//    }
//
//    public String path() {
//
//    }

    public List<PathSegment> getPathSegments() {
        Pathable parent = getParent();
        if(parent == null) {
            return new ArrayList<>();
        }

        List<PathSegment> segments = parent.getPathSegments();
        segments.add(new PathSegment(parentAttributeName));
        return segments;
    }

    /**
     * Path from the toplevel-RM object. Not sure if this should be here, because the EHR and Folder objects are also in
     * the RM. But for now, it works because the most toplevel element is a Composition
     *
     * API subject to change in the future!
     * @return
     */
    public final String getPath() {
        return PathUtil.getPath(getPathSegments());
    }

}
