package com.nedap.archie.rm.archetypes;

import com.nedap.archie.aom.ArchetypeID;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * TODO: templateId should be the class TemplateId. but that's not really well defined in the specs, so for now
 * left it as an ARchetypeId
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ARCHETYPED", propOrder = {
        "archetypeId",
        "templateId",
        "rmVersion"
})
public class Archetyped {


    private ArchetypeID archetypeId; //TODO: this is a different class in the RM. why?!
    @Nullable
    private ArchetypeID templateId; //not sure if this is still required in AOM/ADL 2
    private String rmVersion;

    @XmlElement(name="archetype_id")
    public ArchetypeID getArchetypeId() {
        return archetypeId;
    }

    public void setArchetypeId(ArchetypeID archetypeId) {
        this.archetypeId = archetypeId;
    }

    @Nullable
    @XmlElement(name="template_id")
    public ArchetypeID getTemplateId() {
        return templateId;
    }

    public void setTemplateId(@Nullable ArchetypeID templateId) {
        this.templateId = templateId;
    }

    @XmlElement(name="rm_version")
    public String getRmVersion() {
        return rmVersion;
    }

    public void setRmVersion(String rmVersion) {
        this.rmVersion = rmVersion;
    }
}
