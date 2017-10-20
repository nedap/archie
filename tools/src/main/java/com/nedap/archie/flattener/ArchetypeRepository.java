package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;

/**
 * Created by pieter.bos on 21/10/15.
 */
public interface ArchetypeRepository {

    /**
     * Get Archetype based on archetype reference.
     * Warning: conversion from HRID to reference needs to be done by implementer for now! (so match v1.0.0 to v1 and vice versa)
     * TODO: implement some default methods that already do this and create a getlatestBySemanticIc and getByFullArchetypeHRID
     * @param archetypeId
     * @return
     */
    Archetype getArchetype(String archetypeId);
}
