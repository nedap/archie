package com.nedap.archie.rm.composition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "SECTION", propOrder = {
        "items"
})
public class Section extends ContentItem {

    private List<ContentItem> items = new ArrayList<>();

    public List<ContentItem> getItems() {
        return items;
    }

    public void setItems(List<ContentItem> items) {
        this.items = items;
        for(ContentItem item:items) {
            setThisAsParent(item, "item");
        }
    }

    public void addItem(ContentItem item) {
        this.items.add(item);
        setThisAsParent(item, "item");
    }
}
