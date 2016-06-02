package com.nedap.archie.rm.archetypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nedap.archie.paths.PathSegment;
import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datatypes.UIDBasedId;
import com.nedap.archie.rm.datavalues.DvText;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "LOCATABLE", propOrder = {
        "name",
        "uid",
        "links",
        "archetypeDetails"
        //,"feederAudit"
})
public class Locatable extends Pathable {

    private DvText name;
    private String archetypeNodeId;
    @Nullable
    private UIDBasedId uid;

    @Nullable
    private Archetyped archetypeDetails;

    private List<Link> links = new ArrayList<>();
    private String nameAsString;

    @XmlElement
    public DvText getName() {
        return name;
    }

    public void setName(DvText name) {
        this.name = name;
    }

    /** convenience method*/
    @XmlTransient
    public void setName(String name) {
        this.name = new DvText(name);
    }

    @XmlAttribute(name = "archetype_node_id")
    public String getArchetypeNodeId() {
        return archetypeNodeId;
    }

    public void setArchetypeNodeId(String archetypeNodeId) {
        this.archetypeNodeId = archetypeNodeId;
    }

    public UIDBasedId getUid() {
        return uid;
    }

    public void setUid(UIDBasedId uid) {
        this.uid = uid;
    }

    @XmlElement(name = "archetype_details")
    public Archetyped getArchetypeDetails() {
        return archetypeDetails;
    }

    public void setArchetypeDetails(Archetyped archetypeDetails) {
        this.archetypeDetails = archetypeDetails;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> linked) {
        this.links = linked;
    }

    public void addLink(Link link) {
        this.links.add(link);
    }

    @Override
    @JsonIgnore
    public List<PathSegment> getPathSegments() {
        Pathable parent = getParent();
        if(parent == null) {
            return new ArrayList<>();
        }

        List<PathSegment> segments = parent.getPathSegments();
        segments.add(new PathSegment(getParentAttributeName(), archetypeNodeId));
        return segments;
    }

    @XmlTransient
    public String getNameAsString() {
        return name == null ? null : name.getValue();
    }
}

