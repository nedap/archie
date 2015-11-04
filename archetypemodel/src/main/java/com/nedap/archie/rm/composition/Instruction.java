package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.Activity;
import com.nedap.archie.rm.datavalues.DvDateTime;
import com.nedap.archie.rm.datavalues.DvParsable;
import com.nedap.archie.rm.datavalues.DvText;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 03/11/15.
 */
public class Instruction extends CareEntry {
    private DvText narrative;
    @Nullable
    private DvDateTime expiryTime;
    @Nullable
    private DvParsable wfDefinition;
    private List<Activity> activities = new ArrayList<>();

    public DvText getNarrative() {
        return narrative;
    }

    public void setNarrative(DvText narrative) {
        this.narrative = narrative;
    }

    @Nullable
    public DvDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(@Nullable DvDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Nullable
    public DvParsable getWfDefinition() {
        return wfDefinition;
    }

    public void setWfDefinition(@Nullable DvParsable wfDefinition) {
        this.wfDefinition = wfDefinition;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
