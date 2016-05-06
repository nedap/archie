package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.Activity;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.encapsulated.DvParsable;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 03/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "INSTRUCTION", propOrder = {
        "narrative",
        "expiryTime",
        "wfDefinition",
        "activities"
})
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
    @XmlElement(name = "expiry_time")
    public DvDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(@Nullable DvDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Nullable
    @XmlElement(name = "wf_definition")
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
        for(Activity activity:activities) {
            setThisAsParent(activity, "activity");
        }
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
        setThisAsParent(activity, "activity");
    }
}
