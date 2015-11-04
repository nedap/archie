package com.nedap.archie.rm.datastructures;

import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDuration;


import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class IntervalEvent<Type extends ItemStructure> extends Event<Type> {

    private DvDuration width;
    @Nullable
    private Integer sampleCount;
    private DvCodedText mathFunction;

    public DvDuration getWidth() {
        return width;
    }

    public void setWidth(DvDuration width) {
        this.width = width;
    }

    @Nullable
    public Integer getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(@Nullable Integer sampleCount) {
        this.sampleCount = sampleCount;
    }

    public DvCodedText getMathFunction() {
        return mathFunction;
    }

    public void setMathFunction(DvCodedText mathFunction) {
        this.mathFunction = mathFunction;
    }
}
