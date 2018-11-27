package org.openehr.bmm.v2.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public final class PBmmPackage extends PBmmPackageContainer {

    private String documentation;
    private String name;
    private List<String> classes;

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
        if(classes == null) {
            classes = new ArrayList<>();
        }
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }


    public void setClassesAndPackagesFrom(PBmmPackage other) {
        setClasses(new ArrayList<>(other.getClasses()));
        TreeMap<String, PBmmPackage> packages = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        packages.putAll(other.getPackages());
        setPackages(packages);//TODO: CLONE TO PACKAGE OBJECTS!?
    }

    public String getDocumentation() {
        return documentation;
    }

    public void doRecursiveClasses(BiConsumer<PBmmPackage, String> action) {
        getClasses().forEach(bmmClass -> {
            action.accept(this, bmmClass);
        });
        getPackages().forEach((key, bmmPackage) -> {
            bmmPackage.doRecursiveClasses(action);
        });
    }
}

