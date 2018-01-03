package org.openehr.odin;

import java.util.List;

import java.util.ArrayList;

public class OdinReferenceObject extends OdinObject {
    private List<String> paths = new ArrayList<>();

    public OdinReferenceObject() {

    }

    public OdinReferenceObject(List<String> paths) {
        this.paths = paths;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPath(List<String> paths) {
        this.paths = paths;
    }
}
