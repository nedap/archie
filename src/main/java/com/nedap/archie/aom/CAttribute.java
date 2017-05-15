package com.nedap.archie.aom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nedap.archie.base.MultiplicityInterval;
import com.nedap.archie.paths.PathSegment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
@JsonPropertyOrder({"@type", "rm_attribute_name", "path", "logical_path", "differential_path", "multiple", "mandatory", "existence", "cardinality", "children"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="C_ATTRIBUTE", propOrder = {
        "existence",
        "differentialPath",
        "multiple",
        "cardinality",
        "children"
})

public class CAttribute extends ArchetypeConstraint {

    @XmlAttribute(name="rm_attribute_name")
    private String rmAttributeName;
    private MultiplicityInterval existence;
    @XmlElement(name="differential_path")
    private String differentialPath;
    @XmlElement(name="is_multiple")
    private boolean multiple;

    private Cardinality cardinality;

    private List<CObject> children = new ArrayList<>();

    public String getRmAttributeName() {
        return rmAttributeName;
    }

    public void setRmAttributeName(String rmAttributeName) {
        this.rmAttributeName = rmAttributeName;
    }

    public MultiplicityInterval getExistence() {
        return existence;
    }

    public void setExistence(MultiplicityInterval existence) {
        this.existence = existence;
    }

    public String getDifferentialPath() {
        return differentialPath;
    }

    public void setDifferentialPath(String differentialPath) {
        this.differentialPath = differentialPath;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public CObject getChild(String nodeId) {
        for(CObject child:children) {
            if(nodeId.equals(child.getNodeId())) {
                return child;
            } else if(child instanceof CArchetypeRoot) {
                if (((CArchetypeRoot) child).getArchetypeRef().equals(nodeId)) {
                    return child;
                }
            }
        }
        return null;
    }

    public CObject getChildByMeaning(String meaning) {
        meaning = meaning.toLowerCase();
        for(CObject child:children) {
            String childMeaning = child.getMeaning();
            if(childMeaning != null) {
                childMeaning = childMeaning.toLowerCase();
                if(meaning.equals(childMeaning)){
                    return child;
                }
            }

        }
        return null;
    }

    public List<CObject> getChildren() {
        return children;
    }

    public void setChildren(List<CObject> children) {
        if(children == null) {
            this.children = new ArrayList<>();
        } else {
            this.children = children;

            for(CObject child:children) {
                child.setParent(this);
            }
        }
    }

    public void addChild(CObject child) {
        children.add(child);
        child.setParent(this);
    }


    public void replaceChild(String nodeId, CObject definition) {

        int index = getIndexOfChildWithnodeId(nodeId);
        if(index > -1) {
            children.set(index, definition);
            definition.setParent(this);
        } else {
            addChild(definition);
        }


    }

    private int getIndexOfChildWithnodeId(String nodeId) {
        for(int i = 0; i < children.size(); i++) {
            CObject child = children.get(i);
            if(nodeId.equals(child.getNodeId())) {
                return i;
            }
        }
        return -1;
    }

    public Cardinality getCardinality() {
        return cardinality;
    }

    public void setCardinality(Cardinality cardinality) {
        this.cardinality = cardinality;
    }

    @Override
    public String toString() {
        return "Cattribute: " + rmAttributeName + ", " + children.size() + " children";
    }

    public List<PathSegment> getPathSegments() {
        CObject parent = getParent();
        if(parent == null) {
            return new ArrayList<>();
        }
        List<PathSegment> segments = parent.getPathSegments();
        segments.add(new PathSegment(getRmAttributeName()));
        return segments;
    }

    @Override
    public CObject getParent() {
        return (CObject) super.getParent();
    }

    public String getLogicalPath() {
        String path = "/" + rmAttributeName;
        if(getParent() != null) {
            path = getParent().getLogicalPath() + path;
        }
        if(path.startsWith("//")) {
            return path.substring(1);
        }
        return path;
    }

    public CAttribute clone() {
        return (CAttribute) super.clone();
    }


    /* Operations defined by UML */

    @JsonIgnore
    @XmlTransient
    public boolean isSingle() {
        return !multiple;
    }

    public boolean isMandatory() {
        if(existence != null) {
            return existence.isMandatory();
        }
        return false;
    }

    public boolean anyAllowed() {
        return children.isEmpty() && !isProhibited();
    }


    public boolean isProhibited() {
        if(existence != null) {
            return existence.isProhibited();
        }
        return false;
    }
    //TODO: congruent and conforms to?

}
