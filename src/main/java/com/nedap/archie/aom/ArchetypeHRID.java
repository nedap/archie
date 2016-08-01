package com.nedap.archie.aom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by pieter.bos on 15/10/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ARCHETYPE_HRID", propOrder = {
        "conceptId",
        "namespace",
        "rmPublisher",
        "rmPackage",
        "rmClass",
        "releaseVersion",
        "versionStatus",
        "buildCount"
})
public class ArchetypeHRID extends ArchetypeModelObject {

    private String namespace;
    @XmlAttribute(name="rm_publisher")
    private String rmPublisher;
    @XmlAttribute(name="rm_package")
    private String rmPackage;
    @XmlAttribute(name="rm_class")
    private String rmClass;
    @XmlAttribute(name="concept_id")
    private String conceptId;
    @XmlAttribute(name="release_version")
    private String releaseVersion;
    @XmlAttribute(name="version_status")
    private String versionStatus;
    @XmlAttribute(name="build_count")
    private String buildCount;
    //TODO: XML attribute 'physical id', which is the full id


    public ArchetypeHRID() {

    }

    @JsonCreator
    public ArchetypeHRID(String value) {

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

    @JsonCreator
    public ArchetypeHRID(@JsonProperty("namespace") String namespace,
                         @JsonProperty("publisher") String rmPublisher,
                         @JsonProperty("rm_package") String rmPackage,
                         @JsonProperty("rm_class") String rmClass,
                         @JsonProperty("concept_id") String conceptId,
                         @JsonProperty("release_version") String releaseVersion,
                         @JsonProperty("version_status") String versionStatus,
                         @JsonProperty("build_count") String buildCount) {
        this.namespace = namespace;
        this.rmPublisher = rmPublisher;
        this.rmPackage = rmPackage;
        this.rmClass = rmClass;
        this.conceptId = conceptId;
        this.releaseVersion = releaseVersion;
        this.versionStatus = versionStatus;
        this.buildCount = buildCount;
    }

    public String getFullId() {
        StringBuilder result = new StringBuilder(30);
        if(!Strings.isNullOrEmpty(namespace)) {
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
        if(releaseVersion.startsWith("v")) {
            result.append(".");
        } else {
            result.append(".v");
        }
        result.append(releaseVersion);
        return result.toString();
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

    @Override
    public String toString() {
        return getFullId();
    }
}
