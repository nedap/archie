package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;

/**
 * Created by pieter.bos on 21/10/15.
 */
public interface ArchetypeRepository {

    Archetype getArchetype(String archetypeId);
}
