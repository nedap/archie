package com.nedap.archie.aom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nedap.archie.paths.PathSegment;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public abstract class ArchetypeConstraint extends ArchetypeModelObject {

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

    @JsonIgnore
    @XmlTransient
    public abstract List<PathSegment> getPathSegments();

    public final String getPath() {
        StringBuilder result = new StringBuilder();

        List<PathSegment> pathSegments = getPathSegments();
        if(pathSegments.isEmpty()) {
            return "/";
        }
        for(PathSegment segment: pathSegments) {
            result.append("/");
            result.append(segment.getNodeName());
            if(segment.getNodeId() != null && !segment.getNodeId().equals(CPrimitiveObject.PRIMITIVE_NODE_ID_VALUE)) {
                result.append("[");
                result.append(segment.getNodeId());
                result.append("]");
            }
        }
        return result.toString();
    }

    public abstract String getLogicalPath();

    public String path() {
        return getPath();
    }


    @JsonIgnore
    @XmlTransient
    public Archetype getArchetype() {
            return getParent().getArchetype();
    }

}
