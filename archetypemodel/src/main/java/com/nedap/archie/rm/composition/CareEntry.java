package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.datatypes.ObjectRef;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class CareEntry extends Entry {

    @Nullable
    private ItemStructure protocol;
    @Nullable
    private ObjectRef guidelineId;

    @Nullable
    public ItemStructure getProtocol() {
        return protocol;
    }

    public void setProtocol(@Nullable ItemStructure protocol) {
        this.protocol = protocol;
        setThisAsParent(protocol);
    }

    @Nullable
    public ObjectRef getGuidelineId() {
        return guidelineId;
    }

    public void setGuidelineId(@Nullable ObjectRef guidelineId) {
        this.guidelineId = guidelineId;
    }
}
