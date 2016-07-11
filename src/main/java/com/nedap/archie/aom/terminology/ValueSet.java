package com.nedap.archie.aom.terminology;

import com.nedap.archie.aom.ArchetypeModelObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by pieter.bos on 15/10/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="VALUE_SET")
public class ValueSet extends ArchetypeModelObject {
    private String id;
    private Set<String> members = new LinkedHashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Collection<String> members) {
        this.members = new LinkedHashSet<>(members);
    }

    public void addMember(String member) {
        this.members.add(member);
    }
}
