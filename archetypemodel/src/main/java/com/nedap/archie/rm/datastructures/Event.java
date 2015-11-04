package com.nedap.archie.rm.datastructures;

import com.nedap.archie.rm.archetypes.Locatable;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 03/11/15.
 */
public class Event<Type extends ItemStructure> extends Locatable {

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
    }

    public Type getData() {
        return data;
    }

    public void setData(Type data) {
        this.data = data;
    }
}
