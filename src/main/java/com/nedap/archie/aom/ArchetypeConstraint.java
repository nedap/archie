package com.nedap.archie.aom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nedap.archie.paths.PathSegment;
import com.nedap.archie.paths.PathUtil;

import javax.xml.bind.annotation.XmlTransient;

import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public abstract class ArchetypeConstraint extends ArchetypeModelObject {

    @JsonIgnore //ignore these field in popular object mappers
    @XmlTransient
    private transient ArchetypeConstraint parent;
    @JsonIgnore //ignore these field in popular object mappers, otherwise we get infinite loops
    @XmlTransient
    private transient CSecondOrder socParent;

    @JsonIgnore
    @XmlTransient
    public ArchetypeConstraint getParent() {
        return parent;
    }

    public void setParent(ArchetypeConstraint parent) {
        this.parent = parent;
    }

    @JsonIgnore
    @XmlTransient
    public CSecondOrder getSocParent() {
        return socParent;
    }

    public void setSocParent(CSecondOrder socParent) {
        this.socParent = socParent;
    }

    @JsonIgnore
    @XmlTransient
    public abstract List<PathSegment> getPathSegments();

    public final String getPath() {
        return PathUtil.getPath(getPathSegments());
    }

    private void setPath(String path){
        //setter hack for jackson, unfortunately
    }

    public abstract String getLogicalPath();

    private void setLogicalPath(String path){
        //setter hack for jackson, unfortunately
    }

    public String path() {
        return getPath();
    }


    @JsonIgnore
    @XmlTransient
    public Archetype getArchetype() {
        return getParent().getArchetype();
    }

}
