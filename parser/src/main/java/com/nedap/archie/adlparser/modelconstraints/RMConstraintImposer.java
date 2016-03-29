package com.nedap.archie.adlparser.modelconstraints;

/**
 * Constraints imposer for the Archie reference model implementation.
 *
 * Created by pieter.bos on 04/11/15.
 */
public class RMConstraintImposer extends ReflectionConstraintImposer {

    public RMConstraintImposer() {
        super(new ArchieRMInfoLookup());
    }

}
