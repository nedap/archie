package com.nedap.archie.rm.directory;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.datatypes.ObjectRef;
import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 21/06/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FOLDER", propOrder = {
        "items",
        "folders"
})
public class Folder extends Locatable {
    @Nullable
    private List<ObjectRef> items = new ArrayList<>();
    @Nullable
    private List<Folder> folders = new ArrayList<>();

    @Nullable
    public List<ObjectRef> getItems() {
        return items;
    }

    public void setItems(@Nullable List<ObjectRef> items) {
        this.items = items;
    }

    public void addItem(ObjectRef item) {
        this.items.add(item);
    }

    @Nullable
    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(@Nullable List<Folder> folders) {
        this.folders = folders;
        setThisAsParent(folders, "folders");
    }

    public void addFolder(Folder folder) {
        this.folders.add(folder);
        this.setThisAsParent(folder, "folders");
    }
}
