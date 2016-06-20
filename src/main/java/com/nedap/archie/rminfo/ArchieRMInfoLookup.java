package com.nedap.archie.rminfo;

import com.nedap.archie.base.OpenEHRBase;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.datastructures.PointEvent;

/**
 * Created by pieter.bos on 02/02/16.
 */
public class ArchieRMInfoLookup extends ModelInfoLookup {

    private static ArchieRMInfoLookup instance;

    public ArchieRMInfoLookup() {
        super(new ArchieRMNamingStrategy(), RMObject.class);
    }

    public static ArchieRMInfoLookup getInstance() {
        if(instance == null) {
            instance = new ArchieRMInfoLookup();
        }
        return instance;
    }

    @Override
    public Class getClassToBeCreated(String rmTypename) {
        if(rmTypename.equals("EVENT")) {
            //this is an abstract class and cannot be created. Create point event instead
            return PointEvent.class;
        }
        return getClass(rmTypename);
    }

}
