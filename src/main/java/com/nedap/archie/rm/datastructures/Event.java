package com.nedap.archie.rm.datastructures;

import com.nedap.archie.rm.archetypes.Locatable;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDuration;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.time.Duration;
import java.time.OffsetDateTime;

/**
 * Created by pieter.bos on 03/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "EVENT", propOrder = {
        "time",
        "data",
        "state"
})
public abstract class Event<Type extends ItemStructure> extends Locatable {

    private DvDateTime time;
    @Nullable
    private Type state;

    private Type data;

    public DvDateTime getTime() {
        return time;
    }

    public void setTime(DvDateTime time) {
        this.time = time;
    }

    @Nullable
    public Type getState() {
        return state;
    }

    public void setState(@Nullable Type state) {
        this.state = state;
        setThisAsParent(state, "state");
    }

    public Type getData() {
        return data;
    }

    public void setData(Type data) {
        this.data = data;
        setThisAsParent(data, "data");
    }

    public DvDuration offset() {
        DvDuration result = new DvDuration();
        Duration duration = Duration.between(OffsetDateTime.from(((History) getParent()).getOrigin().getValue()), OffsetDateTime.from(time.getValue()));
        result.setValue(duration);
        //would be even better if we could set the accurary too. Let's not for now
        return result;

    }
}
