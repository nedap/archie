package com.nedap.archie.rm.support.identification;

import com.google.common.base.Strings;

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

    public ArchetypeID(String value) {

        Pattern p = Pattern.compile("((?<namespace>.*)::)?(?<publisher>.*)-(?<package>.*)-(?<class>.*)\\.(?<concept>.*)\\.v(?<version>.*)");
        Matcher m = p.matcher(value);

        if(!m.matches()) {
            throw new IllegalArgumentException(value + " is not a valid archetype human readable id");
        }
        rmOriginator = m.group("publisher");
        rmName = m.group("package");
        rmEntity = m.group("class");


        domainConcept = m.group("concept");
        versionId = m.group("version");
        //TODO: versionStatus and build count
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
