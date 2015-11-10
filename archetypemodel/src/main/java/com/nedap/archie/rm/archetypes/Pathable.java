package com.nedap.archie.rm.archetypes;

import com.nedap.archie.query.APathQuery;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class Pathable {

    //TODO: object should be something else i think. Perhaps RMModelObject?
    public Object itemAtPath(String s) {
        return new APathQuery(s).find(this);
    }
}
