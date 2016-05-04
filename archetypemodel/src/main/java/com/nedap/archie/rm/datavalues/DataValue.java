package com.nedap.archie.rm.datavalues;

import com.nedap.archie.rm.RMObject;

import javax.xml.bind.annotation.XmlType;

/**
 * TODO: check if we can add common fields here for convenience, for example value and magnitude
 *
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "DATA_VALUE")
public abstract class DataValue extends RMObject {
}
