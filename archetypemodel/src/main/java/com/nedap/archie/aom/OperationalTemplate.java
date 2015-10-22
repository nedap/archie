package com.nedap.archie.aom;

import com.nedap.archie.aom.terminology.ArchetypeTerminology;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class OperationalTemplate extends AuthoredArchetype {

    /**
     * terminology extracts from subarchetypes, for example snomed codes, multiple choice thingies, etc
     */
    private Map<String, String> terminologyExtracts = new ConcurrentHashMap<>();//TODO: is this correct?

    private Map<String, ArchetypeTerminology> componentTerminologies = new ConcurrentHashMap<>();

    public Map<String, String> getTerminologyExtracts() {
        return terminologyExtracts;
    }

    public void setTerminologyExtracts(Map<String, String> terminologyExtracts) {
        this.terminologyExtracts = terminologyExtracts;
    }

    public Map<String, ArchetypeTerminology> getComponentTerminologies() {
        return componentTerminologies;
    }

    public void setComponentTerminologies(Map<String, ArchetypeTerminology> componentTerminologies) {
        this.componentTerminologies = componentTerminologies;
    }

}

