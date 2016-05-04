package com.nedap.archie.rm.datastructures;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "ITEM_STRUCTURE")
public class ItemStructure<Type extends Item> extends DataStructure {
    private List<Type> items = new ArrayList<>();

    public List<Type> getItems() {
        return items;
    }

    public void setItems(List<Type> items) {
        this.items = items;
        for(Type item:items) {
            setThisAsParent(item, "item");
        }
    }

    public void addItem(Type item) {
        this.items.add(item);
        setThisAsParent(item, "item");
    }
}
