package com.nedap.archie.rm.datavalues.quantity;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 */
public abstract class DvAmount<MagnitudeType extends Comparable> extends DvQuantified<Double, MagnitudeType>{
    @Nullable
    private Boolean accuracyIsPercent;


}
