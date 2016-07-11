package com.nedap.archie.rm.datavalues.timespecification;

import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.encapsulated.DvParsable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 08/07/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_TIME_SPECIFICATION")
public abstract class DvTimeSpecification extends DataValue {

    private DvParsable value;

    public DvParsable getValue() {
        return value;
    }

    public void setValue(DvParsable value) {
        this.value = value;
    }
}
