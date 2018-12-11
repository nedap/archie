package org.openehr.bmm.v2.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PBmmClass extends PBmmBase {

    private String documentation;//from P_BMM_MODEL_ELEMENT
    private String name;
    private List<String> ancestors;
    private Map<String, PBmmType> ancestorDefs;
    private Map<String, PBmmProperty> properties;
    private Boolean isAbstract;
    private Boolean isOverride;
    private Map<String, PBmmGenericParameter> genericParameterDefs;

    private transient String sourceSchemaId;

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
        if(ancestors == null) {
            ancestors = new ArrayList<>();
        }
        return ancestors;
    }

    public void setAncestors(List<String> ancestors) {
        this.ancestors = ancestors;
    }

    public Map<String, PBmmProperty> getProperties() {
        if(properties == null) {
            properties = new LinkedHashMap<>();
        }
        return properties;
    }

    public void setProperties(Map<String, PBmmProperty> properties) {
        this.properties = properties;
    }

    @JsonProperty(value = "is_abstract")
    public Boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(Boolean anAbstract) {
        isAbstract = anAbstract;
    }

    @JsonProperty(value = "is_override")
    public Boolean isOverride() {
        return isOverride;
    }

    public void setOverride(Boolean override) {
        isOverride = override;
    }

    public Map<String, PBmmGenericParameter> getGenericParameterDefs() {
        if(genericParameterDefs == null) {
            genericParameterDefs = new LinkedHashMap<>();
        }
        return genericParameterDefs;
    }

    public void setGenericParameterDefs(Map<String, PBmmGenericParameter> genericParameterDefs) {
        this.genericParameterDefs = genericParameterDefs;
    }

    /**
     * True if this class is a generic class.
     *
     * @return
     */
    @JsonIgnore
    public boolean isGeneric() {
        return this.getGenericParameterDefs() != null && this.getGenericParameterDefs().size() > 0;
    }

    @JsonIgnore
    public String getSourceSchemaId() {
        return sourceSchemaId;
    }

    public void setSourceSchemaId(String sourceSchemaId) {
        this.sourceSchemaId = sourceSchemaId;
    }

    public Map<String, PBmmType> getAncestorDefs() {
        return ancestorDefs;
    }

    public void setAncestorDefs(Map<String, PBmmType> ancestorDefs) {
        this.ancestorDefs = ancestorDefs;
    }
}
