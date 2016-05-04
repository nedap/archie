package com.nedap.archie.rm.datastructures;

import javax.xml.bind.annotation.XmlType;

/**
 * added constraint is that this contains only one item
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "ITEM_SINGLE", propOrder = {
        "item"
})
public class ItemSingle extends ItemStructure<Item> {

    private Item item;

    public Item getItem() {
        return getItems().isEmpty() ? null : getItems().get(0);
    }

    public void setITem(Item item) {
        getItems().set(0, item);
        this.item = item;
        setThisAsParent(item, "item");
    }
}
