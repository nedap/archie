package com.nedap.archie.rm.datavalues.quantity;

import com.nedap.archie.rm.datavalues.DvText;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class ReferenceRange<T extends DvOrdered> {

    private DvInterval<T> range;
    private DvText meaning;
}
