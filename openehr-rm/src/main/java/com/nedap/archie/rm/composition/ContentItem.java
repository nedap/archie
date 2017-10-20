package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.archetyped.Locatable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Created by pieter.bos on 03/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONTENT_ITEM")
public abstract class ContentItem extends Locatable {

}
