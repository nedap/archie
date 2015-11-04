package com.nedap.archie.rm;

import com.nedap.archie.rm.archetypes.Locatable;
import com.nedap.archie.rm.datavalues.encapsulated.DvParsable;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class Activity extends Locatable {

    private DvParsable timing;
    @Nullable
    private String actionArchetypeId;

    public DvParsable getTiming() {
        return timing;
    }

    public void setTiming(DvParsable timing) {
        this.timing = timing;
    }

    @Nullable
    public String getActionArchetypeId() {
        return actionArchetypeId;
    }

    public void setActionArchetypeId(@Nullable String actionArchetypeId) {
        this.actionArchetypeId = actionArchetypeId;
    }
}
