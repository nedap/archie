package com.nedap.archie.aom;

import com.nedap.archie.base.terminology.TerminologyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class TranslationDetails {
    private TerminologyCode language;
    private String author;
    private String accreditation;
    private Map<String, String> otherDetails = new ConcurrentHashMap<>();
    private String versionLastTranslated;

    public TerminologyCode getLanguage() {
        return language;
    }

    public void setLanguage(TerminologyCode language) {
        this.language = language;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAccreditation() {
        return accreditation;
    }

    public void setAccreditation(String accreditation) {
        this.accreditation = accreditation;
    }

    public Map<String, String> getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(Map<String, String> otherDetails) {
        this.otherDetails = otherDetails;
    }

    public String getVersionLastTranslated() {
        return versionLastTranslated;
    }

    public void setVersionLastTranslated(String versionLastTranslated) {
        this.versionLastTranslated = versionLastTranslated;
    }
}
