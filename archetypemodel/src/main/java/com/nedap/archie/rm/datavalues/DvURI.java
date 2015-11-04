package com.nedap.archie.rm.datavalues;

import java.net.URI;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class DvURI implements SingleValuedDataValue<URI>{

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
