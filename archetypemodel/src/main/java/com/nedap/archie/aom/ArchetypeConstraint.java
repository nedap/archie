package com.nedap.archie.aom;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ArchetypeConstraint extends ArchetypeModelObject {

    @JsonIgnore //ignore these field in popular object mappers
    @XmlTransient
    private ArchetypeConstraint parent;
    @JsonIgnore //ignore these field in popular object mappers, otherwise we get infinite loops
    @XmlTransient
    private CSecondOrder socParent;

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

    public String getPath() {
        String path = "";
        if(parent != null) {
            path += parent.getPath();
        }
        return path;
    }
}
