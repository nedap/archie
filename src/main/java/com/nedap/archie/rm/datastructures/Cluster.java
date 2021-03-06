package com.nedap.archie.rm.datastructures;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CLUSTER", propOrder = {
        "items"
})
@XmlRootElement
public class Cluster<Type extends Item> extends Item {

    private List<Type> items = new ArrayList<>();

    public List<Type> getItems() {
        return items;
    }

    public void setItems(List<Type> items) {
        this.items = items;

        setThisAsParent(items, "items");

    }

    public void addItem(Type item) {
        items.add(item);
        setThisAsParent(item, "items");
    }
}
