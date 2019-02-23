package com.nedap.archie.rm.generic;

import com.nedap.archie.rm.RMObject;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 08/07/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="REVISION_HISTORY")
public class RevisionHistory extends RMObject {

    private List<RevisionHistoryItem> items = new ArrayList<>();

    public List<RevisionHistoryItem> getItems() {
        return items;
    }

    public void setItems(List<RevisionHistoryItem> items) {
        this.items = items;
    }

    public void addItem(RevisionHistoryItem item) {
        this.items.add(item);
    }
}
