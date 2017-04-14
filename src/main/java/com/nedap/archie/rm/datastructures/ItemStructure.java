package com.nedap.archie.rm.datastructures;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ITEM_STRUCTURE")
public abstract class ItemStructure<Type extends Item> extends DataStructure {

    /** In the default model it's in the subclasses, but defined here as well because it has a lot of uses */
    public abstract List<Type> getItems();

}
