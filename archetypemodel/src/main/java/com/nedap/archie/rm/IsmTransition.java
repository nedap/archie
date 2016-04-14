package com.nedap.archie.rm;

import com.nedap.archie.rm.archetypes.Pathable;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.DvText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class IsmTransition extends Pathable {

    private DvCodedText currentState;
    private DvCodedText transition;
    private DvCodedText careflowStep;

    private List<DvText> reason = new ArrayList();

    public DvCodedText getCurrentState() {
        return currentState;
    }

    public void setCurrentState(DvCodedText currentState) {
        this.currentState = currentState;
    }

    public DvCodedText getTransition() {
        return transition;
    }

    public void setTransition(DvCodedText transition) {
        this.transition = transition;
    }

    public DvCodedText getCareflowStep() {
        return careflowStep;
    }

    public void setCareflowStep(DvCodedText careflowStep) {
        this.careflowStep = careflowStep;
    }

    public List<DvText> getReason() {
        return reason;
    }

    public void setReason(List<DvText> reason) {
        this.reason = reason;
    }

    public void addReason(DvText reason) {
        this.reason.add(reason);
    }
}
