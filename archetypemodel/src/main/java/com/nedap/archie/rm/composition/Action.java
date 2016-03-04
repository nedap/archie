package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.IsmTransition;
import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;

/**
 * Created by pieter.bos on 04/11/15.
 */
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
        description.setParent(this);
    }

    public IsmTransition getIsmTransition() {
        return ismTransition;
    }

    public void setIsmTransition(IsmTransition ismTransition) {
        this.ismTransition = ismTransition;
        ismTransition.setParent(this);
    }
}
