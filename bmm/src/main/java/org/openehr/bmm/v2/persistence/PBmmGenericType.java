package org.openehr.bmm.v2.persistence;

import java.util.List;

public final class PBmmGenericType extends PBmmBaseType {
    private String rootType;
    private List<PBmmType> genericParamaterDefs;
    private List<String> genericParameters;


    public String getRootType() {
        return rootType;
    }

    public void setRootType(String rootType) {
        this.rootType = rootType;
    }

    public List<PBmmType> getGenericParamaterDefs() {
        return genericParamaterDefs;
    }

    public void setGenericParamaterDefs(List<PBmmType> genericParamaterDefs) {
        this.genericParamaterDefs = genericParamaterDefs;
    }

    public List<String> getGenericParameters() {
        return genericParameters;
    }

    public void setGenericParameters(List<String> genericParameters) {
        this.genericParameters = genericParameters;
    }
}
