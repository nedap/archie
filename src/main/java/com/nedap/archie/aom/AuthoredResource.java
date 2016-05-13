package com.nedap.archie.aom;

import com.nedap.archie.base.terminology.TerminologyCode;

import java.util.Map;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class AuthoredResource extends ArchetypeModelObject{

    private Boolean controlled;
    private String uid;
    private ResourceDescription description;

    private LanguageSection content = new LanguageSection();

    private ResourceAnnotations annotations;

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

    public ResourceDescription getDescription() {
        return description;
    }

    public void setDescription(ResourceDescription description) {
        this.description = description;
    }

    public LanguageSection getContent() {
        return content;
    }

    public void setContent(LanguageSection content) {
        this.content = content;
    }

    public TerminologyCode getOriginalLanguage() {
        return content.getOriginalLanguage();
    }

    public void setOriginalLanguage(TerminologyCode originalLanguage) {
        content.setOriginalLanguage(originalLanguage);
    }

    public Map<String, TranslationDetails> getTranslations() {
        return content.getTranslations();
    }

    public void setTranslations(Map<String, TranslationDetails> translations) {
        content.setTranslations(translations);
    }

    public ResourceAnnotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(ResourceAnnotations annotations) {
        this.annotations = annotations;
    }

    /** Not in archetype object model specs, but this is the full content of the language section.
     *   All methods of this languageSection class are directly exposed by methods of AuthoredResource
     */
    public LanguageSection getAuthoredResourceContent() {
        return content;
    }


    public void setAuthoredResourceContent(LanguageSection content) {
        this.content = content;
    }
}
