package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeHRID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In memory ConcurrentHashMap-based archetyperepository. Thread-safe
 * Created by pieter.bos on 21/10/15.
 */
public class SimpleArchetypeRepository implements ArchetypeRepository, MutableArchetypeRepository {

    private Map<String, Archetype> archetypes = new ConcurrentHashMap<>();

    @Override
    public Archetype getArchetype(String archetypeId) {
        return archetypes.get(new ArchetypeHRID(archetypeId).getSemanticId());
    }

    @Override
    public void addArchetype(Archetype archetype) {
        archetypes.put(archetype.getArchetypeId().getSemanticId(), archetype);
    }

    public List<Archetype> getAllArchetypes() {
        return new ArrayList<>(archetypes.values());
    }
}
