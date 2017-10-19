package com.nedap.archie.rm.datastructures;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ITEM_TABLE", propOrder = {
        "rows"
})
public class ItemTable extends ItemStructure<Element> {

    @Nullable
    private List<Cluster> rows = new ArrayList<>();

    public List<Cluster> getRows() {
        return rows;
    }

    public void setRows(List<Cluster> rows) {
        this.rows = rows;
        setThisAsParent(rows, "rows");
    }

    public void addItem(Cluster row) {
        this.rows.add(row);
        setThisAsParent(row, "rows");
    }

    /** This is a bit of a strange one - returns all elements present in the table. Use getRows instead*/
    @Override
    public List<Element> getItems() {
        if(rows == null) {
            return null;
        }
        List<Element> result = new ArrayList<>();
        for(Cluster<Element> row:rows) {
            for(Element element:row.getItems()) {
                result.add(element);
            }
        }
        return result;
    }
}
