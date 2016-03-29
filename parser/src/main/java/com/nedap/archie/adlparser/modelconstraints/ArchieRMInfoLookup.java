package com.nedap.archie.adlparser.modelconstraints;

/**
 * Created by pieter.bos on 02/02/16.
 */
public class ArchieRMInfoLookup extends ModelInfoLookup {

    public ArchieRMInfoLookup() {
        super(new ArchieRMNamingStrategy(), "com.nedap.archie.rm");
    }

}
