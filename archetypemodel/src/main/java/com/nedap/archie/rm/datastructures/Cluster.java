package com.nedap.archie.rm.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class Cluster extends Item {

    private List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
