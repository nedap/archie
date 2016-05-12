package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.archetypes.Pathable;
import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.datatypes.LocatableRef;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "INSTRUCTION_DETAILS", propOrder = {
        "instructionId",
        "activityId",
        "wfDetails"
})
public class InstructionDetails extends Pathable {

    private LocatableRef instructionId;
    private String activityId;
    @Nullable
    private ItemStructure wfDetails;

    @XmlElement(name = "instruction_id")
    public LocatableRef getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(LocatableRef instructionId) {
        this.instructionId = instructionId;
    }

    @XmlElement(name = "activity_id")
    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @XmlElement(name = "wf_details")
    public ItemStructure getWfDetails() {
        return wfDetails;
    }

    public void setWfDetails(ItemStructure wfDetails) {
        this.wfDetails = wfDetails;
        setThisAsParent(wfDetails, "wf_details");
    }
}
