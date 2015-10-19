package com.nedap.archie.aom;

import java.util.HashMap;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class AuthoredArchetype extends ArchetypeModelObject{

    private String adlVersion;
    private String buildUid;
    private String rmRelease;
    private Boolean generated;
    private HashMap<String, String> otherMetaData = new HashMap<>();

    public String getAdlVersion() {
        return adlVersion;
    }

    public void setAdlVersion(String adlVersion) {
        this.adlVersion = adlVersion;
    }

    public String getBuildUid() {
        return buildUid;
    }

    public void setBuildUid(String buildUid) {
        this.buildUid = buildUid;
    }

    public String getRmRelease() {
        return rmRelease;
    }

    public void setRmRelease(String rmRelease) {
        this.rmRelease = rmRelease;
    }

    public Boolean getGenerated() {
        return generated;
    }

    public void setGenerated(Boolean generated) {
        this.generated = generated;
    }

    public HashMap<String, String> getOtherMetaData() {
        return otherMetaData;
    }

    public void setOtherMetaData(HashMap<String, String> otherMetaData) {
        this.otherMetaData = otherMetaData;
    }
}
