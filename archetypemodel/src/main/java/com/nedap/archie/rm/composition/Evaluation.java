package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.datastructures.ItemStructure;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class Evaluation extends CareEntry {
    private ItemStructure data;

    public ItemStructure getData() {
        return data;
    }

    public void setData(ItemStructure data) {
        this.data = data;
    }
}
