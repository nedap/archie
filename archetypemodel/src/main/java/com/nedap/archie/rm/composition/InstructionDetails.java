package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.archetypes.Pathable;
import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.datatypes.LocatableRef;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "INSTRUCTION_DETAILS", propOrder = {
        "instructionId",
        "activityId",
        "wfDetails"
})
public class InstructionDetails extends Pathable {

    @XmlElement(name = "instruction_id")
    private LocatableRef instructionId;
    @XmlElement(name = "activity_id")
    private String activityId;
    @Nullable
    @XmlElement(name = "wf_details")
    private ItemStructure wfDetails;

    public LocatableRef getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(LocatableRef instructionId) {
        this.instructionId = instructionId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public ItemStructure getWfDetails() {
        return wfDetails;
    }

    public void setWfDetails(ItemStructure wfDetails) {
        this.wfDetails = wfDetails;
        setThisAsParent(wfDetails, "wf_details");
    }
}
