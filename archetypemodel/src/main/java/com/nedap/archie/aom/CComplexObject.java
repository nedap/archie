package com.nedap.archie.aom;

import com.nedap.archie.query.APathQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CComplexObject extends CDefinedObject<ArchetypeModelObject> {

    private List<CAttribute> attributes = new ArrayList();

    private List<CAttributeTuple> attributeTuples = new ArrayList();

    /**
     * If the parent of this is an archetype, this will contain a pointer to it.
     */
    private Archetype archetype;

    /**
     * get attribute by name.
     * @param name
     * @return
     */
    @Override
    public CAttribute getAttribute(String name) {
        for(CAttribute attribute:attributes) {
            if(attribute.getRmAttributeName().equals(name)) {
                return attribute;
            }
        }
        return null;
    }

    public List<CAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<CAttribute> attributes) { //TODO: do we want a setter for a list? or just an addAttribute/removeAttribute thing that's actually threadsafe?
        this.attributes = attributes;
    }

    public void addAttribute(CAttribute attribute) {
        attribute.setParent(this);
        attributes.add(attribute);
    }

    public List<CAttributeTuple> getAttributeTuples() {
        return attributeTuples;
    }

    public void setAttributeTuples(List<CAttributeTuple> attributeTuples) {
        this.attributeTuples = attributeTuples;
    }

    public void addAttributeTuple(CAttributeTuple tuple) {
        this.attributeTuples.add(tuple);
    }

    public boolean isAnyAllowed() {
        return attributes.isEmpty();
    }

    @Override
    public void setParent(ArchetypeConstraint parent) {
        archetype = null;
        super.setParent(parent);
    }

    public Archetype getArchetype() {
        if(archetype == null) {
            return getParent().getArchetype();
        }
        return archetype;
    }

    @Override
    public boolean isRootNode() {
        return this.getParent() == null && this.archetype != null;
    }

    /* set the archetype this is used in. Only set for root nodes! */
    public void setArchetype(Archetype archetype) {
        this.archetype = archetype;
    }

    @Override
    public String toString() {
        return "CComplexObject: " + getRmTypeName() + "[" + getNodeId() + "]";
    }

    /** TODO: should this only be on complex objects? */
    public <T extends ArchetypeModelObject> T itemAtPath(String path) {
        return new APathQuery(path).find(this);
    }
}
