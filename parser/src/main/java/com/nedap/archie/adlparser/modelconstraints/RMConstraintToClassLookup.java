package com.nedap.archie.adlparser.modelconstraints;

/**
 * Created by pieter.bos on 02/02/16.
 */
public class RMConstraintToClassLookup extends ConstraintToClassLookup {

    public RMConstraintToClassLookup() {
        super("com.nedap.archie.rm");
    }

    @Override
    public String getRmTypeName(Class clazz) {
        String name = clazz.getSimpleName();
        switch(name) {
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
