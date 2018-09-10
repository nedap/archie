package com.nedap.archie.rm.datastructures;

import javax.annotation.Nullable;
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
@XmlType(name = "ITEM_LIST", propOrder = {
    "items"
})
@XmlRootElement(name="item_list")
public class ItemList extends ItemStructure<Element> {

    @Nullable
    private List<Element> items = new ArrayList<>();

    public List<Element> getItems() {
return items;
}

    public void setItems(List<Element> items) {
        this.items = items;
        setThisAsParent(items, "items");
    }

    public void addItem(Element item) {
        this.items.add(item);
        setThisAsParent(item, "items");
    }
}
