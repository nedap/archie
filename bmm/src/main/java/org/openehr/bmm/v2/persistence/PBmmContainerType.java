package org.openehr.bmm.v2.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Formal name of the type for display.
     *
     * @return
     */
    @Override
    public String asTypeString() {
        return containerType + "<" + getTypeRef().asTypeString() + ">";
    }

    @Override
    public List<String> flattenedTypeList() {
        List<String> retVal = new ArrayList<>();
        retVal.add(containerType);
        if(getTypeRef() != null) {
            retVal.addAll(getTypeRef().flattenedTypeList());
        }
        return retVal;
    }

    /**
     * Get the type reference to the contained type
     * @return
     */
    @JsonIgnore
    public PBmmBaseType getTypeRef() {
        if(typeDef == null && type != null) {
            if(type.length() == 1) {//TODO!!!!: ?!?!?! Probably a parameter such as "T"
                return new PBmmOpenType(type);
            } else {
                return new PBmmSimpleType(type);
            }
        }
        return typeDef;
    }


}
