package org.openehr.odin.jackson.testclasses;

import java.util.ArrayList;
import java.util.List;

public class ContainerWithList {
    private String someField;
    private List<TestObject> list = new ArrayList<>();

    public List<TestObject> getList() {
        return list;
    }

    public void setList(List<TestObject> list) {
        this.list = list;
    }

    public void addListItem(TestObject item) {
        this.list.add(item);
    }

    public String getSomeField() {
        return someField;
    }

    public void setSomeField(String someField) {
        this.someField = someField;
    }
}
