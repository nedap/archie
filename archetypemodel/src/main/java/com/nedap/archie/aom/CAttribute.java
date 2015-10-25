package com.nedap.archie.aom;

import com.nedap.archie.base.MultiplicityInterval;
import com.nedap.archie.paths.PathSegment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CAttribute extends ArchetypeConstraint {

    private String rmAttributeName;
    private MultiplicityInterval existence; //TODO: this is just a regular Interval<Integer>. Change it?
    private String differentialPath;
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
        this.children = children;
        for(CObject child:children) {
            child.setParent(this);
        }
    }

    public void addChild(CObject child) {
        children.add(child);
        child.setParent(this);
    }


    public void replaceChild(String nodeId, CComplexObject definition) {
        Iterator<CObject> iter = children.iterator();
        while(iter.hasNext()) {
            CObject child = iter.next();
            if(nodeId.equals(child.getNodeId())) {
                iter.remove();
            }
        }
        addChild(definition);
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
        segments.add(new PathSegment(getRmAttributeName(), null));
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

}
