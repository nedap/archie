package com.nedap.archie.aom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nedap.archie.base.terminology.TerminologyCode;

import java.util.Map;

/**
 * Authored Resource.
 *
 * Contains a small deviation from the spec that should not be noticed during use:
 * All methods, including get and set methods from the AOM spec are present.
 * However, where normally every attribute is directly mapped to a field, the field from the language section of an
 * archetype in ADL are mapped to a LanguageSection object. This is done to enable odin parsing directly from odin to
 * objects, instead of steps in between.
 *
 * Created by pieter.bos on 15/10/15.
 */
public class AuthoredResource extends ArchetypeModelObject {

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

    /**
     * Not in archetype object model specs, but this is the full content of the language section.
     *   All methods of this languageSection class are directly exposed by methods of AuthoredResource
     */
    @JsonIgnore
    public LanguageSection getAuthoredResourceContent() {
        return content;
    }


    public void setAuthoredResourceContent(LanguageSection content) {
        this.content = content;
    }
}
