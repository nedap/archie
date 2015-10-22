package com.nedap.archie.aom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ArchetypeHRID extends ArchetypeModelObject {

    private String idValue;

    private String namespace;
    private String rmPublisher;
    private String rmPackage;
    private String rmClass;
    private String conceptId;
    private String releaseVersion;
    private String versionStatus;
    private String buildCount;


    public ArchetypeHRID() {

    }

    public ArchetypeHRID(String value) {
        this.idValue = value;

        Pattern p = Pattern.compile("((?<namespace>.*)::)?(?<publisher>.*)-(?<package>.*)-(?<class>.*)\\.(?<concept>.*)\\.v(?<version>.*)");
        Matcher m = p.matcher(value);

        if(!m.matches()) {
            throw new IllegalArgumentException(value + " is not a valid archetype human readable id");
        }
        namespace = m.group("namespace");
        rmPublisher = m.group("publisher");
        rmPackage = m.group("package");
        rmClass = m.group("class");


        conceptId = m.group("concept");
        releaseVersion = m.group("version");
        //TODO: versionStatus and build count
    }

    public String getFullId() {
        return idValue;
    }

    public String getSemanticId() {
        StringBuilder result = new StringBuilder();
        if(namespace != null) {
            result.append(namespace);
            result.append("::");
        }
        result.append(rmPublisher);
        result.append("-");
        result.append(rmPackage);
        result.append("-");
        result.append(rmClass);
        result.append(".");
        result.append(conceptId);
        result.append(".v");
        result.append(releaseVersion.split("\\.")[0]);
        return result.toString();

    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getRmPublisher() {
        return rmPublisher;
    }

    public void setRmPublisher(String rmPublisher) {
        this.rmPublisher = rmPublisher;
    }

    public String getRmPackage() {
        return rmPackage;
    }

    public void setRmPackage(String rmPackage) {
        this.rmPackage = rmPackage;
    }

    public String getRmClass() {
        return rmClass;
    }

    public void setRmClass(String rmClass) {
        this.rmClass = rmClass;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public String getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(String versionStatus) {
        this.versionStatus = versionStatus;
    }

    public String getBuildCount() {
        return buildCount;
    }

    public void setBuildCount(String buildCount) {
        this.buildCount = buildCount;
    }
}
