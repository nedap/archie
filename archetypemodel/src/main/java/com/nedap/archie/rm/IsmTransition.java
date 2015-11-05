package com.nedap.archie.rm;

import com.nedap.archie.rm.datavalues.DvCodedText;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class IsmTransition {

    private DvCodedText currentState;
    private DvCodedText transition;
    private DvCodedText careflowStep;

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
}
