package com.nedap.archie.aom;

import com.esotericsoftware.kryo.Kryo;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Note: this Archetype does not conform to the UML model completely:
 * - it extends AuthoredResource - needed because otherwise we would have multiple inheritance
 * - it contains fields from AuthoredArchetype - needed because it saves complicated casting in java to call these methods otherwise
 *
 * Created by pieter.bos on 15/10/15.
 */
public class Archetype extends AuthoredResource implements Cloneable {

    private String parentArchetypeId;
    private boolean differential = false;
    private ArchetypeHRID archetypeId;

    private CComplexObject definition;
    private ArchetypeTerminology terminology;
    private RuleStatement rules = null;//TODO: this should be a list when we correctly parse the rules. However, getting this as a string is very nice alternative for many use cases

    private String adlVersion;
    private String buildUid;
    private String rmRelease;
    private Boolean generated;

    public String getParentArchetypeId() {
        return parentArchetypeId;
    }

    public void setParentArchetypeId(String parentArchetypeId) {
        this.parentArchetypeId = parentArchetypeId;
    }

    public boolean isDifferential() {
        return differential;
    }

    public void setDifferential(boolean differential) {
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
        definition.setArchetype(this);
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

    private Map<String, String> otherMetaData = new ConcurrentHashMap<>();

    public String getAdlVersion() {
        return adlVersion;
    }

    public void setAdlVersion(String adlVersion) {
        this.adlVersion = adlVersion;
    }

    public String getBuildUid() {
        return buildUid;
    }

    public void setBuildUid(String buildUid) {
        this.buildUid = buildUid;
    }

    public String getRmRelease() {
        return rmRelease;
    }

    public void setRmRelease(String rmRelease) {
        this.rmRelease = rmRelease;
    }

    public Boolean getGenerated() {
        return generated;
    }

    public void setGenerated(Boolean generated) {
        this.generated = generated;
    }

    public Map<String, String> getOtherMetaData() {
        return otherMetaData;
    }

    public void setOtherMetaData(HashMap<String, String> otherMetaData) {
        this.otherMetaData = otherMetaData;
    }

    public void addOtherMetadata(String text, String value) {
        if(value != null) {
            otherMetaData.put(text, value);
        }//TODO: just a value is possible according to grammar. But no in a ConcurrentHashMap. Change to different map implementation?
    }

    public Archetype clone() {
        return new Kryo().copy(this);
    }
}
