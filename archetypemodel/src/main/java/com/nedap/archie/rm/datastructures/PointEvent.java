package com.nedap.archie.rm.datastructures;


import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "POINT_EVENT")
public class PointEvent<Type extends ItemStructure> extends Event<Type> {
}
