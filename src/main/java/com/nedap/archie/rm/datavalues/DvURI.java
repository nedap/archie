package com.nedap.archie.rm.datavalues;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DV_URI", propOrder = {
        "value"
})
public class DvURI extends DataValue implements SingleValuedDataValue<String> {

    private URI value; //supposed to be a string, but this is better. Let's see how far we get with this

    @Override
    public String getValue() {
        return value.toString();
    }

    @Override
    public void setValue(String value) {
        this.value = URI.create(value);
    }
}
