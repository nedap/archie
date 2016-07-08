package com.nedap.archie.rm.generic;

import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.quantity.DvInterval;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 08/07/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="PARTICIPATION")
public class Participation extends RMObject {

    private DvText function;
    @Nullable
    private DvCodedText mode;
    @Nullable
    private DvInterval<DvDateTime> time;
    private PartyProxy performer;

    public DvText getFunction() {
        return function;
    }

    public void setFunction(DvText function) {
        this.function = function;
    }

    @Nullable
    public DvCodedText getMode() {
        return mode;
    }

    public void setMode(@Nullable DvCodedText mode) {
        this.mode = mode;
    }

    @Nullable
    public DvInterval<DvDateTime> getTime() {
        return time;
    }

    public void setTime(@Nullable DvInterval<DvDateTime> time) {
        this.time = time;
    }

    public PartyProxy getPerformer() {
        return performer;
    }

    public void setPerformer(PartyProxy performer) {
        this.performer = performer;
    }
}
