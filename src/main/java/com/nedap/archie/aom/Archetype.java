package com.nedap.archie.aom;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class Archetype {

    private String parentArchetypeId;
    private Boolean differential;
    private ArchetypeHRID archetypeId;

    private CComplexObject definition;
    private RuleStatement rules;
    private ArchetypeTerminology terminology;

    public String getParentArchetypeId() {
        return parentArchetypeId;
    }

    public void setParentArchetypeId(String parentArchetypeId) {
        this.parentArchetypeId = parentArchetypeId;
    }

    public Boolean getDifferential() {
        return differential;
    }

    public void setDifferential(Boolean differential) {
        this.differential = differential;
    }

    public ArchetypeHRID getArchetypeId() {
        return archetypeId;
    }

    public void setArchetypeId(ArchetypeHRID archetypeId) {
        this.archetypeId = archetypeId;
    }

    public CComplexObject getDefinition() {
        return definition;
    }

    public void setDefinition(CComplexObject definition) {
        this.definition = definition;
    }

    public RuleStatement getRules() {
        return rules;
    }

    public void setRules(RuleStatement rules) {
        this.rules = rules;
    }

    public ArchetypeTerminology getTerminology() {
        return terminology;
    }

    public void setTerminology(ArchetypeTerminology terminology) {
        this.terminology = terminology;
    }
}
