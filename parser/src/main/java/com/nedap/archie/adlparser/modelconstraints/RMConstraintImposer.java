package com.nedap.archie.adlparser.modelconstraints;

/**
 * Constraints imposer for the Archie reference model implementation.
 *
 * Created by pieter.bos on 04/11/15.
 */
public class RMConstraintImposer extends ReflectionConstraintImposer {
    public RMConstraintImposer() {
        super("com.nedap.archie.rm");
    }

    protected String getRmTypeName(Class clazz) {
        String name = clazz.getSimpleName();
        switch(clazz.getSimpleName()) {
            //some overrides to get every name right. The DV_URI is not really required i think, just to be sure
            case "DvURI":
                return "DV_URI";
            case "DvEHRURI":
                return "DV_EHR_URI";
            case "UIDBasedId":
                return "UID_BASED_ID";
            default:

        }
        return super.getRmTypeName(clazz);
    }
}
