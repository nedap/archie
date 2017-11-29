package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;

/**
 * Interface for an archetype repository that can have archetypes added to it
 */
public interface MutableArchetypeRepository {
    void addArchetype(Archetype archetype);
}
