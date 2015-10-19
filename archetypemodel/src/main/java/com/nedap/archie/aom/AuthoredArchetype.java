package com.nedap.archie.aom;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class AuthoredArchetype extends Archetype {

    private String adlVersion;
    private String buildUid;
    private String rmRelease;
    private Boolean generated;
    private Map<String, String> otherMetaData = new ConcurrentHashMap<>();

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

    public Map<String, String> getOtherMetaData() {
        return otherMetaData;
    }

    public void setOtherMetaData(HashMap<String, String> otherMetaData) {
        this.otherMetaData = otherMetaData;
    }

    public void addOtherMetadata(String text, String value) {
        if(value != null) {
            otherMetaData.put(text, value);
        }//TODO: just a value is possible according to grammar. But no in a ConcurrentHashMap. Change to different map implementation?

    }
}
