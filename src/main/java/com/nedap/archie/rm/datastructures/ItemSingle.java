package com.nedap.archie.rm.datastructures;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * added constraint is that this contains only one item
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ITEM_SINGLE", propOrder = {
        "item"
})
public class ItemSingle extends ItemStructure<Item> {

    private Item item;

    public Item getItem() {
        return item;
    }

    public void setITem(Item item) {
        this.item = item;
    }

    @Override
    public List<Item> getItems() {
        return Lists.newArrayList(item);
    }
}
