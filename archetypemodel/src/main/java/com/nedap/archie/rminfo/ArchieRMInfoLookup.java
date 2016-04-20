package com.nedap.archie.rminfo;

import com.nedap.archie.base.OpenEHRBase;
import com.nedap.archie.rm.RMObject;

/**
 * Created by pieter.bos on 02/02/16.
 */
public class ArchieRMInfoLookup extends ModelInfoLookup {

    private static ArchieRMInfoLookup instance;

    public ArchieRMInfoLookup() {
        super(new ArchieRMNamingStrategy(), "com.nedap.archie.rm", OpenEHRBase.class);
    }

    public static ArchieRMInfoLookup getInstance() {
        if(instance == null) {
            instance = new ArchieRMInfoLookup();
        }
        return instance;
    }

}
