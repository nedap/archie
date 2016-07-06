package com.nedap.archie.rminfo;

import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.base.Interval;
import com.nedap.archie.base.terminology.TerminologyCode;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.datastructures.PointEvent;
import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DvCodedText;

/**
 * Created by pieter.bos on 06/07/16.
 */
public class ArchieAOMInfoLookup extends ModelInfoLookup {

    private static ArchieAOMInfoLookup instance;

    public ArchieAOMInfoLookup() {
        super(new ArchieRMNamingStrategy(), ArchetypeModelObject.class);
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
