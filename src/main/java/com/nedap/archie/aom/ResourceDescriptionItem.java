package com.nedap.archie.aom;

import com.nedap.archie.base.OpenEHRBase;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by pieter.bos on 01/11/15.
 */
public class ResourceDescriptionItem extends ArchetypeModelObject {
    String language;
    String purpose;
    List<String> keywords;
    String use;
    String misuse;
    String copyright;
    String licence;
    Map<String, URI> originalResourceUri;
    Map<String, String> otherDetails;//TODO: string -> object?


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getMisuse() {
        return misuse;
    }

    public void setMisuse(String misuse) {
        this.misuse = misuse;
    }

    public Map<String, URI> getOriginalResourceUri() {
        return originalResourceUri;
    }

    public void setOriginalResourceUri(Map<String, URI> originalResourceUri) {
        this.originalResourceUri = originalResourceUri;
    }

    public Map<String, String> getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(Map<String, String> otherDetails) {
        this.otherDetails = otherDetails;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
