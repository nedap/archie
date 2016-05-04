package com.nedap.archie.rm.datastructures;

import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDuration;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "INTERVAL_EVENT", propOrder = {
        "width",
        "sampleCount",
        "mathFunction"
})
public class IntervalEvent<Type extends ItemStructure> extends Event<Type> {

    private DvDuration width;
    @Nullable
    @XmlElement(name = "sample_count")
    private Long sampleCount;
    @XmlElement(name = "math_function", required = true)
    private DvCodedText mathFunction;

    public DvDuration getWidth() {
        return width;
    }

    public void setWidth(DvDuration width) {
        this.width = width;
    }

    @Nullable
    public Long getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(@Nullable Long sampleCount) {
        this.sampleCount = sampleCount;
    }

    public DvCodedText getMathFunction() {
        return mathFunction;
    }

    public void setMathFunction(DvCodedText mathFunction) {
        this.mathFunction = mathFunction;
    }
}
