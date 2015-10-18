package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CSecondOrder {
    private List<ArchetypeConstraint> members = new ArrayList<>();

    public List<ArchetypeConstraint> getMembers() {
        return members;
    }

    public void setMembers(List<ArchetypeConstraint> members) {
        this.members = members;
    }

    public void addMember(ArchetypeConstraint member) {
        members.add(member);
    }
}
