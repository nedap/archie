package com.nedap.archie.rm.datavalues;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DV_URI", propOrder = {
        "value"
})
public class DvURI extends DataValue implements SingleValuedDataValue<URI>{

    private URI value; //supposed to be a string, but this is better. Let's see how far we get with this

    @Override
    public URI getValue() {
        return value;
    }

    @Override
    public void setValue(URI value) {
        this.value = value;
    }
}
