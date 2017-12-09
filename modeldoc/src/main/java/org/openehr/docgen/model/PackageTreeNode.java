package org.openehr.docgen.model;

import java.util.ArrayList;
import java.util.List;

public class PackageTreeNode {
    private PackageListItem packageItem;
    private List<PackageTreeNode> childrenNodes = new ArrayList<>();

    public PackageListItem getPackageItem() {
        return packageItem;
    }

    public void setPackageItem(PackageListItem packageItem) {
        this.packageItem = packageItem;
    }

    public List<PackageTreeNode> getChildrenNodes() {
        return childrenNodes;
    }

    public void setChildrenNodes(List<PackageTreeNode> childrenNodes) {
        this.childrenNodes = childrenNodes;
    }

    public void addChildNode(PackageTreeNode child) {
        childrenNodes.add(child);
    }
}
