package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.datastructures.ItemStructure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "EVALUATION", propOrder = {
        "data"
})
public class Evaluation extends CareEntry {
    private ItemStructure data;

    public ItemStructure getData() {
        return data;
    }

    public void setData(ItemStructure data) {
        this.data = data;
        setThisAsParent(data, "data");
    }
}
