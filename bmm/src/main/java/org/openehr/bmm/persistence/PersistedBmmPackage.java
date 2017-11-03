package org.openehr.bmm.persistence;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017  Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 * Author: Claude Nanjo
 */

import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmPackage;
import org.openehr.utils.common.CloneUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Persisted form of a package as a tree structure whose nodes can contain more packages and/or classes.
 *
 * Created by cnanjo on 4/11/16.
 */
public class PersistedBmmPackage extends PersistedBmmPackageContainer implements Serializable {

    /**
     * Name of the package from schema; this name may be qualified if it is a top-level package within the schema,
     * or unqualified. Persistent attribute.
     *
     */
    private String name;

    /**
     * List of classes in this package. Persistent attribute.
     */
    private List<String> classes;

    private BmmPackage bmmPackageDefinition;

    /**
     * No-arg constructor
     */
    public PersistedBmmPackage() {
        super();
        classes = new ArrayList<>();
    }

    /**
     * Constructor initializing the package name
     *
     * @param name
     */
    public PersistedBmmPackage(String name) {
        this();
        this.name = name;
    }

    /**
     * Method configures a persisted form of a package to be configured from an in-memory package object.
     *
     * @param bmmPackage
     */
    public void configureFrom(BmmPackage bmmPackage) {
        setName(bmmPackage.getName());
        bmmPackage.getClasses().forEach( bmmClass -> {
            addClass(bmmClass.getName());
        });
        bmmPackage.getPackages().values().forEach( childBmmPackage -> {
            PersistedBmmPackage pBmmPackage = new PersistedBmmPackage();
            pBmmPackage.configureFrom(childBmmPackage);
            addPackage(pBmmPackage);
        });
    }
    /**
     * Returns the name of the package from schema; this name may be qualified if it is a top-level package within the
     * schema, or unqualified.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the package from schema; this name may be qualified if it is a top-level package within the
     * schema, or unqualified.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the list of classes in this package.
     *
     * @return
     */
    public List<String> getClasses() {
        List<String> classes = new ArrayList<>();
        classes.addAll(this.classes);
        return classes;
    }

    /**
     * Set the list of classes in this package.
     *
     * @param classes
     */
    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    /**
     * Adds class name to package.
     *
     * @param className
     */
    public void addClass(String className) {
        classes.add(className);
    }

    public BmmPackage getBmmPackageDefinition() {
        return bmmPackageDefinition;
    }

    public void setBmmPackageDefinition(BmmPackage bmmPackageDefinition) {
        this.bmmPackageDefinition = bmmPackageDefinition;
    }

    public void merge(PersistedBmmPackage other) {
        classes.addAll(other.classes);
        other.getPackages().values().forEach(p -> {
            PersistedBmmPackage sourcePackage = getPackages().get(p.getName());
            if(sourcePackage != null) {
                sourcePackage.merge(p);
            } else {
                addPackage(p.deepClone());
            }
        });
    }

    public void createBmmPackageDefinition(PersistedBmmPackage parent) {
        bmmPackageDefinition = new BmmPackage(name);
        bmmPackageDefinition.setDocumentation(getDocumentation());
        if(parent != null) {
            bmmPackageDefinition.appendToPath(parent.getName());
            bmmPackageDefinition.setParent(parent.bmmPackageDefinition);
        }
        bmmPackageDefinition.appendToPath(name);
        getPackages().values().forEach(p -> {
            p.createBmmPackageDefinition(this);
            if(p.getBmmPackageDefinition() != null) {
                bmmPackageDefinition.addPackage(p.getBmmPackageDefinition());
            }
        });
    }


    /**
     * Convenience method that takes a list of BMM classes and returns a list of
     * class names.
     *
     * @param classes
     * @return
     */
    private List<String> getClassNameList(List<BmmClass> classes) {
        List<String> names = new ArrayList<String>();
        for(BmmClass clazz:classes) {
            names.add(clazz.getName());
        }
        return names;
    }

    public void doRecursiveClasses(BiConsumer<PersistedBmmPackage, String> action) {
        classes.forEach(bmmClass -> {
            action.accept(this, bmmClass);
        });
        getPackages().forEach((key, bmmPackage) -> {
            bmmPackage.doRecursiveClasses(action);
        });
    }

    /**
     * make this package with `packages' and `classes' references to those parts of `other_pkg'
     * but keeping its own name.
     * @param other
     */
    public void makeFromOther(PersistedBmmPackage other) {
        classes = other.getClasses();
        setPackages(other.getPackages());
    }

    public PersistedBmmPackage deepClone() {
        return (PersistedBmmPackage)CloneUtils.deepClone(this);
    }

}
