package org.openehr.bmm.v2.persistence;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

public class PBmmPackageContainer extends PBmmBase {

    private Map<String, PBmmPackage> packages = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public Map<String, PBmmPackage> getPackages() {
        if(packages == null) {
            packages = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        }

        return packages;
    }

    public void setPackages(Map<String, PBmmPackage> packages) {
        this.packages = packages;
    }

    /**
     * recursively execute `action' procedure, taking package as argument
     * @param agent
     */
    public void doRecursivePackages(Consumer<PBmmPackage> agent) {
        getPackages().forEach((packageName, packageItem) -> {
            agent.accept(packageItem);
            packageItem.doRecursivePackages(agent);
        });
    }

}
