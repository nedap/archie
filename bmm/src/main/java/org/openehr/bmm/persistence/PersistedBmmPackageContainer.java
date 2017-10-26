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

import org.openehr.bmm.core.IBmmPackageContainer;

import java.util.*;
import java.util.function.Consumer;

/**
 * Abstraction of a BMM model component that contains packages and classes.
 *
 */
public abstract class PersistedBmmPackageContainer extends PersistedBmmModelElement implements IPersistedBmmPackageContainer {

    public static final String DEFAULT_PACKAGE_DELIMITER = ".";

    /**
     * Package structure as a hierarchy of packages each potentially containing names of classes in that package in the
     * original model.
     *
     */
    private Map<String, PersistedBmmPackage> packages;

    /**
     * No-op constructor
     */
    public PersistedBmmPackageContainer() {
        super();
        packages = new LinkedHashMap<String, PersistedBmmPackage>();
    }

    /**
     * Adds a package to this BMM Package Container. Package must have a name.
     *
     * @param bmmPackage
     */
    public void addPackage(PersistedBmmPackage bmmPackage) {
        packages.put(bmmPackage.getName().toUpperCase(), bmmPackage);
    }

    /**
     * Returns package with name argument or null if no package matches the query.
     *
     * @param packageName
     * @return
     */
    public PersistedBmmPackage getPackage(String packageName) {
        return packages.get(packageName.toUpperCase());
    }

    /**
     * Returns list of packages contained in this model as a map.
     *
     * @return
     */
    public Map<String, PersistedBmmPackage> getPackages() {
        return this.packages;
    }

    @Override
    public void setPackages(Map<String, PersistedBmmPackage> packages) {
        this.packages = packages;
    }

    public void setPackages(List<PersistedBmmPackage> packages) {
        packages.forEach(bmmPackage -> {
            this.packages.put(bmmPackage.getName().toUpperCase(), bmmPackage);
        });
    }

    public List<String> getPackagePaths() {
        List<String> paths = new ArrayList<>();
        packages.values().forEach(bmmPackage -> {
            StringBuilder builder = new StringBuilder(bmmPackage.getName());
            paths.add(builder.toString());
            getPackagePaths(bmmPackage, builder, paths);
        });
        return paths;
    }

    public void getPackagePaths(PersistedBmmPackage bmmPackage, StringBuilder builder, List<String> paths) {
        if(bmmPackage.getPackages() != null && bmmPackage.getPackages().size() > 0) {
            bmmPackage.getPackages().values().forEach( childPackage -> {
                StringBuilder childBuilder = new StringBuilder(builder.toString()).append(DEFAULT_PACKAGE_DELIMITER);
                childBuilder.append(childPackage.getName());
                paths.add(childBuilder.toString());
                getPackagePaths(childPackage, childBuilder, paths);
            });
        }
    }

    /**
     * recursively execute `action' procedure, taking package as argument
     * @param agent
     */
    public void doRecursivePackages(Consumer<PersistedBmmPackage> agent) {
        getPackages().forEach((packageName, packageItem) -> {
            agent.accept(packageItem);
            packageItem.doRecursivePackages(agent);
        });
    }

    /**
     *  Convert all keys to upper case to ensure case-insensitive matching
     */
    public void correctPackageKeys() {
        Map<String, PersistedBmmPackage> updatedPackages = new HashMap<>();
        packages.forEach((key,value) -> {
            updatedPackages.put(key.toUpperCase(), value);
        });
        packages = updatedPackages;
    }
}
