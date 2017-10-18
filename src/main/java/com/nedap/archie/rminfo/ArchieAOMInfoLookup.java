package com.nedap.archie.rminfo;

import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.base.Interval;

/**
 * Created by pieter.bos on 06/07/16.
 */
public class ArchieAOMInfoLookup extends ReflectionModelInfoLookup {

    private static ArchieAOMInfoLookup instance;

    public ArchieAOMInfoLookup() {
        super(new ArchieModelNamingStrategy(), ArchetypeModelObject.class);
        addSubtypesOf(Interval.class); //extra class from the base package. No RMObject because it is also used in the AOM

    }

    public static ArchieAOMInfoLookup getInstance() {
        if(instance == null) {
            instance = new ArchieAOMInfoLookup();
        }
        return instance;
    }

    @Override
    public Object convertToConstraintObject(Object object, CPrimitiveObject cPrimitiveObject) {
        throw new UnsupportedOperationException("not supported");//TODO: split this to different classes
    }

}
