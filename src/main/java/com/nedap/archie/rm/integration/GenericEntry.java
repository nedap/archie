package com.nedap.archie.rm.integration;

import com.nedap.archie.rm.composition.ContentItem;
import com.nedap.archie.rm.datastructures.ItemTree;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 21/06/16.
 */
@XmlType(name = "GENERIC_ENTRY", propOrder = {
        "data"
})

public class GenericEntry extends ContentItem {
    private ItemTree data;

    public ItemTree getData() {
        return data;
    }

    public void setData(ItemTree data) {
        this.data = data;
        setThisAsParent(data, "data");
    }
}
