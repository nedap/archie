package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.archetypes.Pathable;
import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class EventContext extends Pathable {

    private DvDateTime startTime;
    @Nullable
    private DvDateTime endTime;
    @Nullable
    private String location;
    private DvCodedText setting;
    @Nullable
    private ItemStructure otherContext;

    public DvDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DvDateTime startTime) {
        this.startTime = startTime;
    }

    public DvDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DvDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DvCodedText getSetting() {
        return setting;
    }

    public void setSetting(DvCodedText setting) {
        this.setting = setting;
    }

    public ItemStructure getOtherContext() {
        return otherContext;
    }

    public void setOtherContext(ItemStructure otherContext) {
        this.otherContext = otherContext;
        setThisAsParent(otherContext, "other_context");
    }
}
