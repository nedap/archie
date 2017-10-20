package com.nedap.archie.aom;

import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import com.nedap.archie.query.AOMPathQuery;
import com.nedap.archie.query.APathQuery;
import com.nedap.archie.xml.adapters.ArchetypeTerminologyAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Note: this Archetype does not conform to the UML model completely:
 * - it extends AuthoredResource - needed because otherwise we would have multiple inheritance
 * - it contains fields from AuthoredArchetype - needed because it saves complicated casting in java to call these methods otherwise
 *
 * Created by pieter.bos on 15/10/15.
 */
@XmlRootElement(name="archetype")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ARCHETYPE", propOrder = {
        "archetypeId",
        //"differential",
        "parentArchetypeId",
        "definition",
        "terminology",
        "rules",
        "buildUid",
        "rmRelease",
        "generated",
        "otherMetaData"
})
public class Archetype extends AuthoredResource {

    @XmlElement(name="parent_archetype_id")
    private String parentArchetypeId;
    @XmlAttribute(name="is_differential")
    private boolean differential = false;
    @XmlElement(name = "archetype_id")
    private ArchetypeHRID archetypeId;

    private CComplexObject definition;
    @XmlJavaTypeAdapter(ArchetypeTerminologyAdapter.class)
    private ArchetypeTerminology terminology;
    private RulesSection rules = null;

    @XmlAttribute(name="adl_version")
    private String adlVersion;
    @XmlElement(name="build_uid")
    private String buildUid;
    @XmlAttribute(name="rm_release")
    private String rmRelease;
    @XmlAttribute(name="is_generated")
    private Boolean generated;

    @XmlElement(name="other_meta_data")
    //TODO: this probably requires a custom XmlAdapter
    private Map<String, String> otherMetaData = new LinkedHashMap<>();

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

    public RulesSection getRules() {
        return rules;
    }

    public void setRules(RulesSection rules) {
        this.rules = rules;
    }

    public ArchetypeTerminology getTerminology() {
        return terminology;
    }

    public void setTerminology(ArchetypeTerminology terminology) {
        this.terminology = terminology;
    }

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

    public void setOtherMetaData(Map<String, String> otherMetaData) {
        this.otherMetaData = otherMetaData;
    }

    public void addOtherMetadata(String text, String value) {
        if (value != null) {
            otherMetaData.put(text, value);
        }
    }

    /**
     * Translation helper function. To be overriden by Operational Templates
     *
     * @param object   the object to get the term definition for
     * @param language the language to get the term definition for
     * @return the ArchetypeTerm corresponding to the given CObject in the given language
     */
    public ArchetypeTerm getTerm(CObject object, String language) {
        return getTerminology().getTermDefinition(language, object.getNodeId());
    }

    /**
     * Get the terminology definition for a certain code used in a certain path in a terminology. Use this instead of
     * the ArchetypeTerminology and things work in Operation Templates out of the box. Overridden in OperationalTemplate
     *
     * @param object   the object to get the term definition for
     * @param code     the object to get the term definition for. Usually an ac- or at-code
     * @param language the language to get the term definition for
     * @return the ArchetypeTerm corresponding to the given CObject in the given language
     */
    public ArchetypeTerm getTerm(CObject object, String code, String language) {
        return getTerminology().getTermDefinition(language, code);
    }


    public ArchetypeTerminology getTerminology(CObject object) {
        return getTerminology();
    }

    public Archetype clone() {
        return (Archetype) super.clone();
    }

    /** TODO: should this only be on complex objects? */
    public <T extends ArchetypeModelObject> T itemAtPath(String path) {
        return new AOMPathQuery(path).find(getDefinition());
    }

    public List<ArchetypeModelObject> itemsAtPath(String path) {
        return new AOMPathQuery(path).findList(getDefinition());
    }

    @Override
    public String toString() {
        return"archetype: " + getArchetypeId();
    }

    }
