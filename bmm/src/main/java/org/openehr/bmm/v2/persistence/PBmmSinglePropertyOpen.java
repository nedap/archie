package org.openehr.bmm.v2.persistence;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.openehr.bmm.core.BmmSimpleType;

public final class PBmmSinglePropertyOpen extends PBmmProperty<PBmmOpenType> {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    @Override
    public PBmmOpenType getTypeRef() {
        if(getTypeDef() == null) {
            return new PBmmOpenType(type);
        }
        return getTypeDef();
    }

}
