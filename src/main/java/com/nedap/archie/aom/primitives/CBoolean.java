package com.nedap.archie.aom.primitives;

import com.nedap.archie.aom.CPrimitiveObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="C_BOOLEAN")
public class CBoolean extends CPrimitiveObject<Boolean, Boolean> {
    @XmlElement(name="assumed_value")
    private Boolean assumedValue;
    private List<Boolean> constraint = new ArrayList<>();

    @Override
    public Boolean getAssumedValue() {
        return assumedValue;
    }

    @Override
    public void setAssumedValue(Boolean assumedValue) {
        this.assumedValue = assumedValue;
    }

    @Override
    public List<Boolean> getConstraint() {
        return constraint;
    }

    @Override
    public void setConstraint(List<Boolean> constraint) {
        this.constraint = constraint;

    }

    @Override
    public void addConstraint(Boolean constraint) {
        this.constraint.add(constraint);
    }
}
