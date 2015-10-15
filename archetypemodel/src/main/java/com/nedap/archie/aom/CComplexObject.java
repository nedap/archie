package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CComplexObject extends CObject {

    private List<CAttribute> attributes = new ArrayList();

    public List<CAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<CAttribute> attributes) { //TODO: do we want a setter for a list? or just an addAttribute/removeAttribute thing that's actually threadsafe?
        this.attributes = attributes;
    }

    public void addAttribute(CAttribute attribute) {
        attributes.add(attribute);
    }
}
