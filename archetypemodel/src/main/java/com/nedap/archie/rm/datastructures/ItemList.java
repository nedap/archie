package com.nedap.archie.rm.datastructures;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "ITEM_LIST", propOrder = {
        "items"
})
public class ItemList extends ItemStructure<Element> {
}
