package com.nedap.archie.rm.datastructures;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ITEM_LIST", propOrder = {
        "items"
})
public class ItemList extends ItemStructure<Element> {

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
                setThisAsParent(item, "item");
        }
}
