package com.nedap.archie.aom.terminology;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.base.OpenEHRBase;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ArchetypeTerminology extends ArchetypeModelObject {

    private Boolean differential;
    private String originalLanguage;
    private String conceptCode;
    private Map<String, Map<String, ArchetypeTerm>> termDefinitions = new ConcurrentHashMap<>();
    private Map<String, Map<String, URI>> termBindings = new ConcurrentHashMap<>();
    private Map<String, Map<String, ArchetypeTerm>> terminologyExtracts = new ConcurrentHashMap<>();
    private Map<String, ValueSet> valueSets = new ConcurrentHashMap<>();

    private Archetype parent;


    public Boolean getDifferential() {
        return differential;
    }

    public void setDifferential(Boolean differential) {
        this.differential = differential;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getConceptCode() {
        return conceptCode;
    }

    public void setConceptCode(String conceptCode) {
        this.conceptCode = conceptCode;
    }

    public Map<String, Map<String, ArchetypeTerm>> getTermDefinitions() {
        return termDefinitions;
    }

    public void setTermDefinitions(Map<String, Map<String, ArchetypeTerm>> termDefinitions) {
        this.termDefinitions = termDefinitions;
    }

    public Map<String, Map<String, URI>> getTermBindings() {
        return termBindings;
    }

    public void setTermBindings(Map<String, Map<String, URI>> termBindings) {
        this.termBindings = termBindings;
    }

    public Map<String, Map<String, ArchetypeTerm>> getTerminologyExtracts() {
        return terminologyExtracts;
    }

    public void setTerminologyExtracts(Map<String, Map<String, ArchetypeTerm>> terminologyExtracts) {
        this.terminologyExtracts = terminologyExtracts;
    }

    public Archetype getParent() {
        return parent;
    }

    public void setParent(Archetype parent) {
        this.parent = parent;
    }

    public Map<String, ValueSet> getValueSets() {
        return valueSets;
    }

    public void setValueSets(Map<String, ValueSet> valueSets) {
        this.valueSets = valueSets;
    }

    public ArchetypeTerm getTermDefinition(String language, String code) {
        Map<String, ArchetypeTerm> translated = termDefinitions.get(language);
        if(translated == null) {
            return null;
        }
        return translated.get(code);
    }

    public URI getTermBinding(String terminologyId, String code) {
        Map<String, URI> translated = termBindings.get(terminologyId);
        if(translated == null) {
            return null;
        }
        return translated.get(code);
    }
}
