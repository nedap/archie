package com.nedap.archie.aom;

import com.nedap.archie.base.terminology.TerminologyCode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pieter.bos on 02/11/15.
 */
public class LanguageSection {

    private TerminologyCode originalLanguage;
    private Map<String, TranslationDetails> translations = new ConcurrentHashMap<>();


    public TerminologyCode getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(TerminologyCode originalLanguage) {
        this.originalLanguage = originalLanguage;
    }


    public Map<String, TranslationDetails> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, TranslationDetails> translations) {
        this.translations = translations;
    }

}
