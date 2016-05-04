package com.nedap.archie.rm.datavalues.quantity.datetime;

import com.nedap.archie.rm.datavalues.quantity.DvAbsoluteQuantity;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 01/03/16.
 */
@XmlType(name = "DV_TEMPORAL", propOrder = {
        "accuracy"
})
public class DvTemporal<MagnitudeType extends Comparable> extends DvAbsoluteQuantity<DvDuration, MagnitudeType> {
}
