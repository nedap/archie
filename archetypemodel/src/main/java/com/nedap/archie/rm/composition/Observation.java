package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.datastructures.History;
import com.nedap.archie.rm.datastructures.ItemStructure;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 03/11/15.
 */
public class Observation extends CareEntry {

    @Nullable
    private History<ItemStructure> state;
    private History<ItemStructure> data;

    @Nullable
    public History<ItemStructure> getState() {
        return state;
    }

    public void setState(@Nullable History<ItemStructure> state) {
        this.state = state;
        setThisAsParent(state);
    }

    public History<ItemStructure> getData() {
        return data;
    }

    public void setData(History<ItemStructure> data) {
        this.data = data;
        setThisAsParent(data);
    }
}
