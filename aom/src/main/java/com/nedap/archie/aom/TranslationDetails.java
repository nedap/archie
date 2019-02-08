package com.nedap.archie.aom;

import com.nedap.archie.base.terminology.TerminologyCode;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class TranslationDetails extends ArchetypeModelObject {
    private TerminologyCode language;
    private Map<String, String> author = new ConcurrentHashMap<>();
    @Nullable
    private String accreditation;
    @Nullable
    private Map<String, String> otherDetails = new ConcurrentHashMap<>();
    @Nullable
    private String versionLastTranslated;

    public TerminologyCode getLanguage() {
        return language;
    }

    public void setLanguage(TerminologyCode language) {
        this.language = language;
    }

    public Map<String, String> getAuthor() {
        return author;
    }

    public void setAuthor(Map<String, String> author) {
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
