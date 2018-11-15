package com.nedap.archie.rm.support.identification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by pieter.bos on 15/10/15.
 */
public class ArchetypeID extends ObjectId {

    private String qualifiedRmEntity;
    private String domainConcept;
    private String rmOriginator;
    private String rmName;
    private String rmEntity;
    private String specialisation;
    private String versionId;

    public ArchetypeID() {
    }

    /**
     * Parse the Archetype id from a string
     * @param value
     */
    @JsonCreator(mode= JsonCreator.Mode.DELEGATING)
    public ArchetypeID(String value) {

        Pattern p = Pattern.compile("((?<namespace>.*)::)?(?<publisher>.*)-(?<package>.*)-(?<class>.*)\\.(?<concept>.*)(-(?<specialisation>.*))?\\.v(?<version>.*)");
        Matcher m = p.matcher(value);

        if(!m.matches()) {
            throw new IllegalArgumentException(value + " is not a valid archetype human readable id");
        }
        rmOriginator = m.group("publisher");
        rmName = m.group("package");
        rmEntity = m.group("class");

        specialisation = m.group("specialisation");

        domainConcept = m.group("concept");
        versionId = m.group("version");
    }

    /**
     * Constructor for creating the archetype id based on all fields separate in json
     * @param qualifiedRmEntity
     * @param domainConcept
     * @param rmOriginator
     * @param rmName
     * @param rmEntity
     * @param specialisation
     * @param versionId
     */
    @JsonCreator
    public ArchetypeID(@JsonProperty("qualified_rm_entity") String qualifiedRmEntity,
                       @JsonProperty("domain_concept")String domainConcept,
                       @JsonProperty("rm_originator") String rmOriginator,
                       @JsonProperty("rm_name") String rmName,
                       @JsonProperty("rm_entity") String rmEntity,
                       @JsonProperty("specialisation") String specialisation,
                       @JsonProperty("versionId") String versionId) {
        this.qualifiedRmEntity = qualifiedRmEntity;
        this.domainConcept = domainConcept;
        this.rmOriginator = rmOriginator;
        this.rmName = rmName;
        this.rmEntity = rmEntity;
        this.specialisation = specialisation;
        this.versionId = versionId;
    }

    public String getFullId() {
        StringBuilder result = new StringBuilder(30);
        result.append(rmOriginator);
        result.append("-");
        result.append(rmName);
        result.append("-");
        result.append(rmEntity);
        result.append(".");
        result.append(domainConcept);
        if(versionId.startsWith("v")) {
            result.append(".");
        } else {
            result.append(".v");
        }
        result.append(versionId);
        return result.toString();
    }

    public String getSemanticId() {
        StringBuilder result = new StringBuilder();
        result.append(rmOriginator);
        result.append("-");
        result.append(rmName);
        result.append("-");
        result.append(rmEntity);
        result.append(".");
        result.append(domainConcept);
        result.append(".v");
        result.append(versionId.split("\\.")[0]);
        return result.toString();

    }

    public String getQualifiedRmEntity() {
        return qualifiedRmEntity;
    }

    public void setQualifiedRmEntity(String qualifiedRmEntity) {
        this.qualifiedRmEntity = qualifiedRmEntity;
    }

    public String getDomainConcept() {
        return domainConcept;
    }

    public void setDomainConcept(String domainConcept) {
        this.domainConcept = domainConcept;
    }

    public String getRmOriginator() {
        return rmOriginator;
    }

    public void setRmOriginator(String rmOriginator) {
        this.rmOriginator = rmOriginator;
    }

    public String getRmName() {
        return rmName;
    }

    public void setRmName(String rmName) {
        this.rmName = rmName;
    }

    public String getRmEntity() {
        return rmEntity;
    }

    public void setRmEntity(String rmEntity) {
        this.rmEntity = rmEntity;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    @Override
    public String toString() {
        return getFullId();
    }
}
