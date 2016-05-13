package com.nedap.archie.rm.datastructures;

import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDuration;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "INTERVAL_EVENT", propOrder = {
        "width",
        "sampleCount",
        "mathFunction"
})
public class IntervalEvent<Type extends ItemStructure> extends Event<Type> {

    private DvDuration width;
    @Nullable

    private Long sampleCount;

    private DvCodedText mathFunction;

    public DvDuration getWidth() {
        return width;
    }

    public void setWidth(DvDuration width) {
        this.width = width;
    }

    @Nullable
    @XmlElement(name = "sample_count")
    public Long getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(@Nullable Long sampleCount) {
        this.sampleCount = sampleCount;
    }

    @XmlElement(name = "math_function", required = true)
    public DvCodedText getMathFunction() {
        return mathFunction;
    }

    public void setMathFunction(DvCodedText mathFunction) {
        this.mathFunction = mathFunction;
    }
}
