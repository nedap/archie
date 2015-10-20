package com.nedap.archie.aom;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ArchetypeConstraint extends ArchetypeModelObject {


    //TODO: parent is present in UML without a type. Should it be ArchetypeModelObject?
    private ArchetypeConstraint parent;
    private CSecondOrder socParent;

    public ArchetypeConstraint getParent() {
        return parent;
    }

    public void setParent(ArchetypeConstraint parent) {
        this.parent = parent;
    }

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
