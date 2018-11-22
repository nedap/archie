package org.openehr.bmm.v2.persistence;

public class PBmmContainerType extends PBmmType {
    private String containerType;
    private PBmmBaseType typeDef;
    private String type;

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public PBmmBaseType getTypeDef() {
        return typeDef;
    }

    public void setTypeDef(PBmmBaseType typeDef) {
        this.typeDef = typeDef;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
