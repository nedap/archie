package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class AuthoredResource {
    private String originalLanguage;
    private Boolean controlled;
    private String uid;

    private List<TranslationDetails> translations = new ArrayList<>();
    private ResourceDescription description;


    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Boolean getControlled() {
        return controlled;
    }

    public void setControlled(Boolean controlled) {
        this.controlled = controlled;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<TranslationDetails> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationDetails> translations) {
        this.translations = translations;
    }

    public void addTranslation(TranslationDetails translation) {
        this.translations.add(translation);
    }


    public ResourceDescription getDescription() {
        return description;
    }

    public void setDescription(ResourceDescription description) {
        this.description = description;
    }
}
