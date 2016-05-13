package com.nedap.archie.aom;

import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import com.nedap.archie.paths.PathSegment;

import java.util.Collections;
import java.util.List;
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

    private String getChildArchetypeId(CObject object) {
        //optimization possible: walk back the tree until you find a node used in an archetype, instead of
        //getting the entire path
        List<PathSegment> pathSegments = object.getPathSegments();
        Collections.reverse(pathSegments);
        for(PathSegment segment:pathSegments) {
            if(segment.hasArchetypeRef()) {
                //this is [archetypeId] instead of [idcode]
                return segment.getNodeId();
            }
        }
        return null;
    }

    /**
     * Get the ArchetypeTerm for the given CObject. This gets the term from the ComponentTerminologies if required.
     * @param object {@inheritDoc}
     * @param language {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public ArchetypeTerm getTerm(CObject object, String language) {
        return getTerm(object, object.getNodeId(), language);
    }

    @Override
    public ArchetypeTerm getTerm(CObject object, String code, String language) {
        String archetypeId = getChildArchetypeId(object);
        if(archetypeId == null) {
            return super.getTerm(object, code, language);
        } else {
            ArchetypeTerminology terminology = getComponentTerminologies().get(archetypeId);
            if(terminology != null) {
                return terminology.getTermDefinition(language, code);
            } else {
                //TODO: check if we should do this or just return null
                throw new IllegalStateException("Expected an archetype terminology for archetype id " + archetypeId);
            }
        }
    }


    public ArchetypeTerminology getTerminology(CObject object) {
        String archetypeId = getChildArchetypeId(object);
        if(archetypeId == null) {
            return getTerminology();
        } else {
            return getComponentTerminologies().get(archetypeId);
        }
    }
}

