package org.openehr.bmm.v2.persistence;

import java.util.List;
import java.util.Map;

public class PBmmClass extends PBmmBase {

    private String documentation;//from P_BMM_MODEL_ELEMENT
    private String name;
    private List<String> ancestors;
    private Map<String, PBmmProperty> properties;
    private boolean isAbstract;
    private boolean isOverride;
    private Map<String, PBmmGenericParameter> genericParameterDefs;

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAncestors() {
        return ancestors;
    }

    public void setAncestors(List<String> ancestors) {
        this.ancestors = ancestors;
    }

    public Map<String, PBmmProperty> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, PBmmProperty> properties) {
        this.properties = properties;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }

    public boolean isOverride() {
        return isOverride;
    }

    public void setOverride(boolean override) {
        isOverride = override;
    }

    public Map<String, PBmmGenericParameter> getGenericParameterDefs() {
        return genericParameterDefs;
    }

    public void setGenericParameterDefs(Map<String, PBmmGenericParameter> genericParameterDefs) {
        this.genericParameterDefs = genericParameterDefs;
    }
}
