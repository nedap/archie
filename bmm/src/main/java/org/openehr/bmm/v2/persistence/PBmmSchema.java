package org.openehr.bmm.v2.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.openehr.bmm.persistence.PersistedBmmPackage;
import org.openehr.bmm.persistence.validation.BmmDefinitions;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class PBmmSchema extends PBmmPackageContainer {

    private Map<String, PBmmClass> primitiveTypes;
    private Map<String, PBmmClass> classDefinitions;
    private Map<String, BmmIncludeSpec> includes;

    private String rmPublisher;
    private String rmRelease;
    private String bmmVersion;
    private String modelName;
    private String schemaName;
    private String schemaRevision;
    private String schemaLifecycleState;
    private String schemaAuthor;
    private String schemaDescription;
    private List<String> schemaContributors;

    /** this is present in older BMM files, so must be supported here */
    @Deprecated
    private List<String> archetypeRmClosurePackages;

    //TODO: this looks like archetype profile?!
    @Deprecated
    private String archetypeParentClass;
    @Deprecated
    private String archetypeDataValueParentClass;
    @Deprecated
    private String archetypeVisualizeDescendantsOf;

    public Map<String, PBmmClass> getPrimitiveTypes() {
        return primitiveTypes;
    }

    public void setPrimitiveTypes(Map<String, PBmmClass> primitiveTypes) {
        this.primitiveTypes = primitiveTypes;
    }

    public Map<String, PBmmClass> getClassDefinitions() {
        return classDefinitions;
    }

    public void setClassDefinitions(Map<String, PBmmClass> classDefinitions) {
        this.classDefinitions = classDefinitions;
    }

    public Map<String, BmmIncludeSpec> getIncludes() {
        return includes;
    }

    public void setIncludes(Map<String, BmmIncludeSpec> includes) {
        this.includes = includes;
    }

    public String getBmmVersion() {
        return bmmVersion;
    }

    public void setBmmVersion(String bmmVersion) {
        this.bmmVersion = bmmVersion;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaRevision() {
        return schemaRevision;
    }

    public void setSchemaRevision(String schemaRevision) {
        this.schemaRevision = schemaRevision;
    }

    public String getSchemaLifecycleState() {
        return schemaLifecycleState;
    }

    public void setSchemaLifecycleState(String schemaLifecycleState) {
        this.schemaLifecycleState = schemaLifecycleState;
    }

    public String getSchemaAuthor() {
        return schemaAuthor;
    }

    public void setSchemaAuthor(String schemaAuthor) {
        this.schemaAuthor = schemaAuthor;
    }

    public String getSchemaDescription() {
        return schemaDescription;
    }

    public void setSchemaDescription(String schemaDescription) {
        this.schemaDescription = schemaDescription;
    }

    public List<String> getSchemaContributors() {
        return schemaContributors;
    }

    public void setSchemaContributors(List<String> schemaContributors) {
        this.schemaContributors = schemaContributors;
    }

    public String getRmPublisher() {
        return rmPublisher;
    }

    public void setRmPublisher(String rmPublisher) {
        this.rmPublisher = rmPublisher;
    }

    public String getRmRelease() {
        return rmRelease;
    }

    public void setRmRelease(String rmRelease) {
        this.rmRelease = rmRelease;
    }

    @Deprecated
    public List<String> getArchetypeRmClosurePackages() {
        return archetypeRmClosurePackages;
    }

    @Deprecated
    public void setArchetypeRmClosurePackages(List<String> archetypeRmClosurePackages) {
        this.archetypeRmClosurePackages = archetypeRmClosurePackages;
    }

    public String getArchetypeParentClass() {
        return archetypeParentClass;
    }

    public void setArchetypeParentClass(String archetypeParentClass) {
        this.archetypeParentClass = archetypeParentClass;
    }

    public String getArchetypeDataValueParentClass() {
        return archetypeDataValueParentClass;
    }

    public void setArchetypeDataValueParentClass(String archetypeDataValueParentClass) {
        this.archetypeDataValueParentClass = archetypeDataValueParentClass;
    }

    public String getArchetypeVisualizeDescendantsOf() {
        return archetypeVisualizeDescendantsOf;
    }

    public void setArchetypeVisualizeDescendantsOf(String archetypeVisualizeDescendantsOf) {
        this.archetypeVisualizeDescendantsOf = archetypeVisualizeDescendantsOf;
    }

    @JsonIgnore
    public String getSchemaId() {
        return BmmDefinitions.createSchemaId(getRmPublisher(), getSchemaName(), getRmRelease());
    }

    public boolean hasClassOrPrimitiveDefinition(Object persistedBmmClass) {
        return false;
    }

}
