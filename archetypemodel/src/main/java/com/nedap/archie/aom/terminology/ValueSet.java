package com.nedap.archie.aom.terminology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ValueSet {
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
