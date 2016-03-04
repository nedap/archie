package com.nedap.archie.rm.datastructures;

import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDuration;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class History<Type extends ItemStructure> extends DataStructure {

    private DvDateTime origin;
    @Nullable
    private DvDuration period;
    @Nullable
    private DvDuration duration;

    @Nullable
    private Type summary;

    private List<Event<Type>> events = new ArrayList<>();

    public DvDateTime getOrigin() {
        return origin;
    }

    public void setOrigin(DvDateTime origin) {
        this.origin = origin;
    }

    @Nullable
    public DvDuration getPeriod() {
        return period;
    }

    public void setPeriod(@Nullable DvDuration period) {
        this.period = period;
    }

    @Nullable
    public DvDuration getDuration() {
        return duration;
    }

    public void setDuration(@Nullable DvDuration duration) {
        this.duration = duration;
    }

    public List<Event<Type>> getEvents() {
        return events;
    }

    public void setEvents(List<Event<Type>> events) {
        this.events = events;
        for(Event event:events) {
            event.setParent(this);
        }
    }

    public void addEvent(Event<Type> event) {
        events.add(event);
        event.setParent(this);
    }

    @Nullable
    public Type getSummary() {
        return summary;
    }

    public void setSummary(@Nullable Type summary) {
        this.summary = summary;
        summary.setParent(this);
    }
}
