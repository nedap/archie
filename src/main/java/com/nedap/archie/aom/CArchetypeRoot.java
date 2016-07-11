package com.nedap.archie.aom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 15/10/15.
 */
@XmlType(name="C_ARCHETYPE_ROOT")
public class CArchetypeRoot extends CComplexObject {
    @XmlElement(name="archetype_ref")
    private String archetypeRef;

    public String getArchetypeRef() {
        return archetypeRef;
    }

    public void setArchetypeRef(String archetypeRef) {
        this.archetypeRef = archetypeRef;
    }
}
