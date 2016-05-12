package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.datatypes.ObjectRef;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "CARE_ENTRY", propOrder = {
        "protocol",
        "guidelineId"
})
public class CareEntry extends Entry {

    @Nullable
    private ItemStructure protocol;
    @Nullable

    private ObjectRef guidelineId;

    @Nullable
    public ItemStructure getProtocol() {
        return protocol;
    }

    public void setProtocol(@Nullable ItemStructure protocol) {
        this.protocol = protocol;
        setThisAsParent(protocol, "protocol");
    }

    @Nullable
    @XmlElement(name = "guideline_id")
    public ObjectRef getGuidelineId() {
        return guidelineId;
    }

    public void setGuidelineId(@Nullable ObjectRef guidelineId) {
        this.guidelineId = guidelineId;
    }
}
