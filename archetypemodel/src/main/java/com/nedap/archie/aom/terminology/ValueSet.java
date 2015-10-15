package com.nedap.archie.aom.terminology;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ValueSet {
    private String id;
    private List<String> members = new ArrayList<>();//TODO: use something concurrent instead

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
