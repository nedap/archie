package org.openehr.bmm.v2.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class PBmmProperty<T extends PBmmType>  extends PBmmBase {

    private String documentation;
    private String name;
    private Boolean isMandatory;
    private Boolean isComputed;
    private Boolean isImInfrastructure;
    private Boolean isImRuntime;
    private T typeDef;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value = "is_mandatory")
    public Boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(Boolean mandatory) {
        isMandatory = mandatory;
    }

    @JsonProperty(value = "is_computed")
    public Boolean isComputed() {
        return isComputed;
    }

    public void setComputed(Boolean computed) {
        isComputed = computed;
    }

    @JsonProperty(value = "is_im_infrastructure")
    public Boolean isImInfrastructure() {
        return isImInfrastructure;
    }

    public void setImInfrastructure(Boolean imInfrastructure) {
        isImInfrastructure = imInfrastructure;
    }

    @JsonProperty(value = "is_im_runtime")
    public Boolean isImRuntime() {
        return isImRuntime;
    }

    public void setImRuntime(Boolean imRuntime) {
        isImRuntime = imRuntime;
    }

    public T getTypeDef() {
        return typeDef;
    }

    public void setTypeDef(T typeDef) {
        this.typeDef = typeDef;
    }

    /**
     * Calculate typeDef and return. Always returns a type, even if typeDef in the persisted schema is not set
     * In that case, using the other type attributes from this property
     * @return
     */
    public T getTypeRef() {
        return typeDef;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }
}
