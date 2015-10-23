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
    private Map<String, ArchetypeTerminology> terminologyExtracts = new ConcurrentHashMap<>();//TODO: is this correct?
    private Map<String, ArchetypeTerminology> componentTerminologies = new ConcurrentHashMap<>();



    public Map<String, ArchetypeTerminology> getTerminologyExtracts() {
        return terminologyExtracts;
    }

    public void setTerminologyExtracts(Map<String, ArchetypeTerminology> terminologyExtracts) {
        this.terminologyExtracts = terminologyExtracts;
    }

    public void addTerminologyExtract(String nodeId, ArchetypeTerminology terminology) {
        terminologyExtracts.put(nodeId, terminology);
    }

    public Map<String, ArchetypeTerminology> getComponentTerminologies() {
        return componentTerminologies;
    }

    public void setComponentTerminologies(Map<String, ArchetypeTerminology> componentTerminologies) {
        this.componentTerminologies = componentTerminologies;
    }

    public void addComponentTerminology(String nodeId, ArchetypeTerminology terminology) {
        componentTerminologies.put(nodeId, terminology);
    }
}

