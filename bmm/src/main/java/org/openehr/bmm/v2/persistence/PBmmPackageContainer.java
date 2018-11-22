package org.openehr.bmm.v2.persistence;

import java.util.Map;
import java.util.function.Consumer;

public class PBmmPackageContainer extends PBmmBase {

    private Map<String, PBmmPackage> packages;

    public Map<String, PBmmPackage> getPackages() {
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
