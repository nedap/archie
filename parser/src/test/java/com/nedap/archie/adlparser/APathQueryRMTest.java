package com.nedap.archie.adlparser;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.query.APathQuery;
import com.nedap.archie.rm.archetypes.Pathable;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.testutil.TestUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test APath query with RM Objects
 * Created by pieter.bos on 06/04/16.
 */
public class APathQueryRMTest {


    TestUtil testUtil;
    Archetype archetype;
    Pathable root;

    @Before
    public void setup() throws Exception {
        archetype = ADLParser.withRMConstraintsImposer().parse(getClass().getResourceAsStream("/basic.adl"));
        testUtil = new TestUtil();
    }

    @Test
    public void simpleTest() {
        root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());
        Composition composition = (Composition) root;

        assertEquals(composition.getContext(), new APathQuery("/context")
                .find(ArchieRMInfoLookup.getInstance(), composition));
//        EVENT_CONTEXT[id11] matches {
//            other_context matches {
//                ITEM_TREE[id2] matches {
//                    items matches {
//                        CLUSTER[id3] occurrences matches {0..*} matches {	-- Qualification
//                            items matches {
//                                ELEMENT[id4] occurrences matches {0..1} matches {	-- OrderID
        assertEquals(composition.getContext().getOtherContext().getItems(),
                new APathQuery("/context[id11]/other_context[id2]/items")
                        .find(ArchieRMInfoLookup.getInstance(), composition));
                //"/context[id2]/items[id3]/items[id4]"//should be one item

        DvText text = new APathQuery("/context[id11]/other_context[id2]/items[id3]/items[id5]/value")
                .find(ArchieRMInfoLookup.getInstance(), composition);
        assertNotNull(text);
    }

    @Test
    public void multipleItems() {
        root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());
        Composition composition = (Composition) root;

        {
            //add another cluster to the RM Object, with the same archetype id (very possible!)
            Composition composition2 = (Composition) testUtil.constructEmptyRMObject(archetype.getDefinition());
            composition.getContext().getOtherContext().getItems().addAll(composition2.getContext().getOtherContext().getItems());
        }

        assertEquals(1,
                new APathQuery("/context")
                        .findList(ArchieRMInfoLookup.getInstance(), composition).size());

        //now check that retrieving this retrieves more than one, even with the same ID. Should we always return a list of objects?
        assertEquals(2,
                new APathQuery("/context[id11]/other_context[id2]/items")
                        .findList(ArchieRMInfoLookup.getInstance(), composition).size());
        assertEquals(2,
                new APathQuery("/context[id11]/other_context[id2]/items[id3]")
                        .findList(ArchieRMInfoLookup.getInstance(), composition).size());

        //and check that retrieving a sub-element also retrieves more than one element
        assertEquals(2,
                new APathQuery("/context[id11]/other_context[id2]/items[id3]/items[id5]/value")
                        .findList(ArchieRMInfoLookup.getInstance(), composition).size());
    }


}
