package com.nedap.archie.rm.datavalues.quantity;

import com.nedap.archie.base.Interval;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_INTERVAL")
public class DvInterval<Type extends DvOrdered> extends Interval<DvOrdered> {

}
