package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.archetypes.Locatable;
import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datatypes.PartyProxy;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by pieter.bos on 03/11/15.
 */
public class Composition extends Locatable {

    private CodePhrase language;
    private CodePhrase territory;
    private CodePhrase category;

    private PartyProxy composer;

    @Nullable
    private EventContext context;

    private List<ContentItem> content = new ArrayList<>();

    public CodePhrase getLanguage() {
        return language;
    }

    public void setLanguage(CodePhrase language) {
        this.language = language;
    }

    public void setLanguage(String codePhrase) {
        this.language = new CodePhrase(codePhrase);
    }

    public CodePhrase getTerritory() {
        return territory;
    }

    public void setTerritory(CodePhrase territory) {
        this.territory = territory;
    }

    public void setTerritory(String codePhrase) {
        this.territory = new CodePhrase(codePhrase);
    }

    public CodePhrase getCategory() {
        return category;
    }

    public void setCategory(CodePhrase category) {
        this.category = category;
    }

    public void setCategory(String codePhrase) {
        this.category = new CodePhrase(codePhrase);
    }

    public PartyProxy getComposer() {
        return composer;
    }

    public void setComposer(PartyProxy composer) {
        this.composer = composer;
    }

    @Nullable
    public EventContext getContext() {
        return context;
    }

    public void setContext(@Nullable EventContext context) {
        this.context = context;
    }

    public List<ContentItem> getContent() {
        return content;
    }

    public void setContent(List<ContentItem> content) {
        this.content = content;
    }

    public void addContent(ContentItem item) {
        this.content.add(item);
    }


}
