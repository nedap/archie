package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ArchetypeSlot extends CObject {

    private List<Assertion> includes = new ArrayList<>();
    private List<Assertion> excludes = new ArrayList<>();
    private Boolean closed;

    public List<Assertion> getIncludes() {
        return includes;
    }

    public void setIncludes(List<Assertion> includes) {
        this.includes = includes;
    }

    public List<Assertion> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<Assertion> excludes) {
        this.excludes = excludes;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}
