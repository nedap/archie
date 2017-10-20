package com.nedap.archie.rules.evaluation;

import com.nedap.archie.aom.CObject;
import com.nedap.archie.creation.RMObjectCreator;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rminfo.ModelInfoLookup;

/**
 * This class is deprecated since the RMObjectCreator can do the same plus more
 * Created by pieter.bos on 04/04/2017.
 */
@Deprecated
class EmptyRMObjectConstructor {

    private RMObjectCreator creator;

    public EmptyRMObjectConstructor(ModelInfoLookup lookup) {
        creator = new RMObjectCreator(lookup);
    }
    /**
     * Creates an empty RM Object, fully nested, one object per CObject found.
     * For those familiar to the old java libs: this is a simple skeleton generator.
     *
     * Used for creating empty objects in fixing assertions for further calculation in the rules evaluator
     * you might not want to use this for other purposes :)
     *
     * Warning: this is NOT feature complete
     * @param object
     * @return
     */
    Object constructEmptyRMObject(CObject object) {
        return creator.create(object);
    }
}
