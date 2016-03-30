package com.nedap.archie.rminfo;

/**
 * Created by pieter.bos on 02/02/16.
 */
public class ArchieRMInfoLookup extends ModelInfoLookup {

    private static ArchieRMInfoLookup instance;

    public ArchieRMInfoLookup() {
        super(new ArchieRMNamingStrategy(), "com.nedap.archie.rm");
    }

    public static ArchieRMInfoLookup getInstance() {
        if(instance == null) {
            instance = new ArchieRMInfoLookup();
        }
        return instance;
    }

}
