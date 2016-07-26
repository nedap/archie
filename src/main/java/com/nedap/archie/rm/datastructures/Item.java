package com.nedap.archie.rm.datastructures;

import com.nedap.archie.rm.archetyped.Locatable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ITEM")
public abstract class Item extends Locatable {
}
