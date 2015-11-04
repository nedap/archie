package com.nedap.archie.rm.archetypes;

import com.nedap.archie.rm.datatypes.UIDBasedId;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class Locatable extends Pathable {

    private String name;
    private String archetypeNodeId;
    @Nullable
    private UIDBasedId uid;

    @Nullable
    private Archetyped archetypeDetails;

    private List<Link> links = new ArrayList<>();



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}

