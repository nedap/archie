package com.nedap.archie.aom.terminology;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pieter.bos on 15/10/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ARCHETYPE_TERMINOLOGY_TEST")
public class ArchetypeTerminology extends ArchetypeModelObject {

    private Boolean differential;
    @XmlElement(name="original_language")
    private String originalLanguage;
    @XmlElement(name="concept_code")
    private String conceptCode;
    @XmlTransient//TODO!
    //@XmlElement(name="term_definitions")
    private Map<String, Map<String, ArchetypeTerm>> termDefinitions = new ConcurrentHashMap<>();
    //@XmlElement(name="term_bindings")
    @XmlTransient//TODO!
    private Map<String, Map<String, URI>> termBindings = new ConcurrentHashMap<>();
    //@XmlElement(name="terminology_extracts")
    @XmlTransient//TODO!
    private Map<String, Map<String, ArchetypeTerm>> terminologyExtracts = new ConcurrentHashMap<>();
    @XmlElement(name="value_sets")
    private Map<String, ValueSet> valueSets = new ConcurrentHashMap<>();

    private transient Archetype parent;

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
