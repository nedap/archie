package com.nedap.archie.rm.archetypes;

import com.nedap.archie.aom.ArchetypeHRID;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class Archetyped {

    private ArchetypeHRID archetypeId; //TODO: this is a different class in the RM. why?!
    @Nullable
    private ArchetypeHRID templateId; //not sure if this is still required in AOM/ADL 2
    private String rmVersion;
}
