package com.nedap.archie.aom.terminology;

import com.nedap.archie.aom.Archetype;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ArchetypeTerminology {

    private Boolean differential;
    private String originalLanguage;
    private String conceptCode;
    private Map<String, Map<String, String>> termDefinitions = new ConcurrentHashMap<>();
    private Map<String, Map<URI, String>> termBindings = new ConcurrentHashMap<>();
    private Map<String, Map<String, String>> terminologyExtracts = new ConcurrentHashMap<>();

    private Archetype parent;
    //TODO:
}
