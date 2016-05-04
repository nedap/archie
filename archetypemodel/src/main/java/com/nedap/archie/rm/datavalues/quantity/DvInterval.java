package com.nedap.archie.rm.datavalues.quantity;

import com.nedap.archie.base.Interval;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "DV_INTERVAL", propOrder = {
        "lower",
        "upper",
        "lowerIncluded",
        "upperIncluded",
        "lowerUnbounded",
        "upperUnbounded"
})
public class DvInterval<Type extends DvOrdered> extends Interval<DvOrdered> {

}
