package com.nedap.archie.rm.datastructures;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "POINT_EVENT")
public class PointEvent<Type extends ItemStructure> extends Event<Type> {
}
