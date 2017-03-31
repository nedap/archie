package com.nedap.archie.archetypevalidator;

import com.nedap.archie.aom.Archetype;

import java.util.List;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public interface ArchetypeValidation {

    public List<ValidationMessage> validate(Archetype archetype);

}
