package org.openehr.bmm.v2.persistence;

public abstract class PBmmProperty<T extends PBmmType>  extends PBmmBase {
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

    public Boolean getMandatory() {
        return isMandatory;
    }

    public void setMandatory(Boolean mandatory) {
        isMandatory = mandatory;
    }

    public Boolean getComputed() {
        return isComputed;
    }

    public void setComputed(Boolean computed) {
        isComputed = computed;
    }

    public Boolean getImInfrastructure() {
        return isImInfrastructure;
    }

    public void setImInfrastructure(Boolean imInfrastructure) {
        isImInfrastructure = imInfrastructure;
    }

    public Boolean getImRuntime() {
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
}
