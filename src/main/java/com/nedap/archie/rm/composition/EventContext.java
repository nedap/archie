package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.archetyped.Pathable;
import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EVENT_CONTEXT", propOrder = {
        "startTime",
        "endTime",
        "location",
        "setting",
        "otherContext",
       // "healthCareFacility",
       // "participations"
})
public class EventContext extends Pathable {

    @XmlElement(name = "start_time")
    private DvDateTime startTime;
    @Nullable

    @XmlElement(name = "end_time")
    private DvDateTime endTime;
    @Nullable
    private String location;
    private DvCodedText setting;
    @XmlElement(name = "other_context")
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
