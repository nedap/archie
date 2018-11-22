package org.openehr.bmm.v2.persistence;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class PBmmPackage extends PBmmPackageContainer {

    private String name;
    private List<String> classes;
    private Map<String, PBmmPackage> packages;

    public PBmmPackage() {

    }

    public PBmmPackage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public Map<String, PBmmPackage> getPackages() {
        return packages;
    }

    public void setPackages(Map<String, PBmmPackage> packages) {
        this.packages = packages;
    }


    public void setClassesAndPackagesFrom(PBmmPackage other) {
        setClasses(new ArrayList<>(other.getClasses()));
        setPackages(new LinkedHashMap<>(other.getPackages()));//TODO: CLONE TO PACKAGE OBJECTS!?
    }
}

