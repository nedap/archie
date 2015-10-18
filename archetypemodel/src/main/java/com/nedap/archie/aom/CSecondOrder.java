package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CSecondOrder<T extends ArchetypeConstraint> {
    private List<T> members = new ArrayList<>();

    public List<T> getMembers() {
        return members;
    }

    public void setMembers(List<T> members) {
        this.members = members;
    }

    public void addMember(T member) {
        members.add(member);
    }
}
