package com.nedap.archie.rm.datavalues;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class DvState extends DataValue implements SingleValuedDataValue<DvCodedText> {

    private boolean terminal;
    private DvCodedText value;

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    public DvCodedText getValue() {
        return value;
    }

    public void setValue(DvCodedText value) {
        this.value = value;
    }
}
