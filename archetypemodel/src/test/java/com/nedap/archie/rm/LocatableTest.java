package com.nedap.archie.rm;

import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.composition.ContentItem;
import com.nedap.archie.rm.composition.Observation;
import com.nedap.archie.rm.datastructures.Cluster;
import com.nedap.archie.rm.datastructures.Element;
import com.nedap.archie.rm.datastructures.History;
import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.datastructures.ItemTree;
import com.nedap.archie.rm.datastructures.PointEvent;
import com.nedap.archie.rm.datavalues.DvText;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pieter.bos on 10/11/15.
 */
public class LocatableTest {

    @Test
    public void testItemAtPath() {
        Composition composition = new Composition();
        Observation item = new Observation();
        composition.addContent(item);
        item.setArchetypeNodeId("id1");
        History<ItemStructure> data = new History<>();

        item.setData(data);

        data.setArchetypeNodeId("id2");
        PointEvent event = new PointEvent<>();
        data.addEvent(event);
        event.setArchetypeNodeId("id3");
        ItemTree itemTree = new ItemTree();
        event.setData(itemTree);
        itemTree.setArchetypeNodeId("id4");

        Element element = new Element();
        itemTree.addItem(element);
        element.setArchetypeNodeId("id5");
        DvText text = new DvText();
        text.setValue("test");
        element.setValue(text);


        History<ItemStructure> state = new History<>();
        item.setState(state);

        assertEquals(data, composition.itemAtPath("/content[id1]/data[id2]"));
        assertEquals(event, composition.itemAtPath("/content[id1]/data[id2]/events[id3]"));
        assertEquals(itemTree, composition.itemAtPath("/content[id1]/data[id2]/events[id3]/data[id4]"));
        assertEquals(element, composition.itemAtPath("/content[id1]/data[id2]/events[id3]/data[id4]/items[id5]"));
        assertEquals(text, composition.itemAtPath("/content[id1]/data[id2]/events[id3]/data[id4]/items[id5]/value"));

//        state.setArchetypeNodeId("id4");
    }
}
