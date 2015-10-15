package com.nedap.archie.aom;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ArchetypeHRID {
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
