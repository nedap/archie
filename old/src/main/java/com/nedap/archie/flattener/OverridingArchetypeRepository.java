package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;

/**
 * Created by pieter.bos on 21/10/15.
 */
public class OverridingArchetypeRepository implements ArchetypeRepository {

    private ArchetypeRepository root;
    private SimpleArchetypeRepository overrides;

    public OverridingArchetypeRepository(ArchetypeRepository root) {
        this.root = root;
        overrides = new SimpleArchetypeRepository();
    }

    public void addArchetype(Archetype archetype) {
        overrides.addArchetype(archetype);
    }

    @Override
    public Archetype getArchetype(String archetypeId) {
        Archetype result = overrides.getArchetype(archetypeId);
        if(result == null) {
            result = root.getArchetype(archetypeId);
        }
        return result;
    }
}
