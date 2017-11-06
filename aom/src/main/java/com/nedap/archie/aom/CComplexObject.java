package com.nedap.archie.aom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nedap.archie.query.AOMPathQuery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
@JsonPropertyOrder({"@type", "rm_type_name", "node_id", "path", "logical_path", "term", "required", "allowed", "any_allowed", "occurrences", "root_node", "attributes", "attribute_tuples"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="C_COMPLEX_OBJECT", propOrder = {
        "attributes",
        "attributeTuples"
})
public class CComplexObject extends CDefinedObject<ArchetypeModelObject> {

    private List<CAttribute> attributes = new ArrayList();

    @XmlElement(name="attributeTuples")
    private List<CAttributeTuple> attributeTuples = new ArrayList();

    @JsonIgnore
    protected transient Archetype archetype;

    public boolean isAnyAllowed() {
        return attributes == null || attributes.isEmpty();
    }

    @Override
    public void setParent(ArchetypeConstraint parent) {
        super.setParent(parent);
        archetype = null;
    }


    @Override
    public boolean isRootNode() {
        return this.getParent() == null && this.archetype != null;
    }

    @Override
    public Archetype getArchetype() {
        if(archetype == null) {
            return getParent().getArchetype();
        }
        return archetype;
    }

    /** set the archetype this is used in. Only set for root nodes! */
    public void setArchetype(Archetype archetype) {
        this.archetype = archetype;
    }

    @Override
    public String toString() {
        return "CComplexObject: " + getRmTypeName() + "[" + getNodeId() + "]";
    }

    /** TODO: should this only be on complex objects? */
    public <T extends ArchetypeModelObject> T itemAtPath(String path) {
        return new AOMPathQuery(path).find(this);
    }

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

    public void setAttributes(List<CAttribute> attributes) {
        if(attributes == null) {
            this.attributes = new ArrayList<>();
        } else {
            this.attributes = attributes;
            for(CAttribute attribute:attributes) {
                attribute.setParent(this);
            }
        }
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


    /**
     * Replace the existing attribute with the same name as newAttribute with the newAttribute
     * useful in flattening
     * If no attribute with the given name exists, add the new attribute 
     *
     * @param newAttribute the attribute to replace the old attribute with.
     */
    public void replaceAttribute(CAttribute newAttribute) {
        CAttribute oldAttribute = getAttribute(newAttribute.getRmAttributeName());
        if(oldAttribute != null) {
            int index = attributes.indexOf(oldAttribute);
            attributes.set(index, newAttribute);
            newAttribute.setParent(this);
        } else {
            ///...
            addAttribute(newAttribute);
        }

    }

    @Override
    public boolean isLeaf() {
        return (attributes == null || attributes.isEmpty()) && (attributeTuples == null || attributeTuples.isEmpty());
    }
}
