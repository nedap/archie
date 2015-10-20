package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CComplexObject extends CDefinedObject<ArchetypeModelObject> {

    private List<CAttribute> attributes = new ArrayList();

    private List<CAttributeTuple> attributeTuples = new ArrayList();

    /**
     * get attribute by name.
     * TODO: although this really belongs in complexobject, add convenience method in CObject, always returning null
     *       saves lots of code in treewalking/path finding
     * @param name
     * @return
     */
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
}
