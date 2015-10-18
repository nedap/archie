package com.nedap.archie.aom;

import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CPrimitiveTuple {
    private ArchetypeModelObject assumedValue;
    private List<CPrimitiveObject> members;

    public ArchetypeModelObject getAssumedValue() {
        return assumedValue;
    }

    public void setAssumedValue(ArchetypeModelObject assumedValue) {
        this.assumedValue = assumedValue;
    }

    public List<CPrimitiveObject> getMembers() {
        return members;
    }

    public void setMembers(List<CPrimitiveObject> members) {
        this.members = members;
    }

    public void addMember(CPrimitiveObject member) {
        members.add(member);
    }
}
