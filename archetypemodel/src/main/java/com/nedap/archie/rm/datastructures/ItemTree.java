package com.nedap.archie.rm.datastructures;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "ITEM_TREE", propOrder = {
        "items"
})
public class ItemTree extends ItemStructure<Item> {

}
