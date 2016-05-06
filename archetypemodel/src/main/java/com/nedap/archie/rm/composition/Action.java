package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.IsmTransition;
import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ACTION", propOrder = {
        "time",
        "description",
        "ismTransition",
        "instructionDetails"
})
public class Action extends CareEntry {

    private DvDateTime time;
    private ItemStructure description;
    private IsmTransition ismTransition;
    private InstructionDetails instructionDetails;


    public DvDateTime getTime() {
        return time;
    }

    public void setTime(DvDateTime time) {
        this.time = time;
    }

    public ItemStructure getDescription() {
        return description;
    }

    public void setDescription(ItemStructure description) {
        this.description = description;
        setThisAsParent(description, "description");
    }

    @XmlElement(name="ism_transition")
    public IsmTransition getIsmTransition() {
        return ismTransition;
    }

    public void setIsmTransition(IsmTransition ismTransition) {
        this.ismTransition = ismTransition;
        setThisAsParent(ismTransition, "ism_transition");
    }

    @XmlElement(name="instruction_details")
    public InstructionDetails getInstructionDetails() {
        return instructionDetails;
    }

    public void setInstructionDetails(InstructionDetails instructionDetails) {
        this.instructionDetails = instructionDetails;
    }
}
