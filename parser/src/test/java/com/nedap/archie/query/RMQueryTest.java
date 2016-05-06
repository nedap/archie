package com.nedap.archie.query;

import com.google.common.collect.Lists;
import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.archetypes.Pathable;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.testutil.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by pieter.bos on 03/05/16.
 */
public class RMQueryTest {

    TestUtil testUtil;
    Archetype archetype;
    Pathable root;

    @Before
    public void setup() throws Exception {
        archetype = ADLParser.withRMConstraintsImposer().parse(getClass().getResourceAsStream("/basic.adl"));
        testUtil = new TestUtil();
    }

    @Test
    public void simpleTest() throws Exception {
        root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());
        Composition composition = (Composition) root;

        RMQuery queryContext = new RMQuery(root);
        assertEquals(Lists.newArrayList(composition.getContext()), queryContext.findList("/context"));
        DvText text = queryContext.find("/context/other_context[id2]/items[id3]/items[id5]/value");
        assertNotNull(text);
    }

    @Test
    public void multipleItems() throws Exception {
        root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());
        Composition composition = (Composition) root;

        {
            //add another cluster to the RM Object, with the same archetype id (very possible!)
            Composition composition2 = (Composition) testUtil.constructEmptyRMObject(archetype.getDefinition());
            composition.getContext().getOtherContext().getItems().addAll(composition2.getContext().getOtherContext().getItems());
        }

        RMQuery queryContext = new RMQuery(root);

        ModelInfoLookup modelInfoLookup = ArchieRMInfoLookup.getInstance();

        List<RMObjectWithPath> context = new APathQuery("/context")
                .findList(modelInfoLookup, composition);
        assertEquals(1, context.size());
        assertEquals("/context", context.get(0).getPath());

        //now check that retrieving this retrieves more than one, even with the same ID.
        List items = queryContext.findList("/context/other_context[id2]/items");
        assertEquals(2, items.size());


        //and check that retrieving a sub-element also retrieves more than one element
        List<RMObjectWithPath> values = queryContext.findList("/context/other_context[id2]/items[id3]/items[id5]/value");
        assertEquals(2, values.size());
    }

    @Test
    public void withSimplePaths() throws Exception {
        root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());
        Composition composition = (Composition) root;

        {
            //add another cluster to the RM Object, with the same archetype id (very possible!)
            Composition composition2 = (Composition) testUtil.constructEmptyRMObject(archetype.getDefinition());
            composition.getContext().getOtherContext().getItems().addAll(composition2.getContext().getOtherContext().getItems());
        }

        RMQuery queryContext = new RMQuery(root);

        ModelInfoLookup modelInfoLookup = ArchieRMInfoLookup.getInstance();

        List<RMObjectWithPath> context = new APathQuery("/context")
                .findList(modelInfoLookup, composition);
        assertEquals(1, context.size());
        assertEquals("/context", context.get(0).getPath());

        //now check that retrieving this retrieves more than one, even with the same ID.
        List<RMObjectWithPath> items = queryContext.findListWithPaths("/context/other_context[id2]/items");
        assertEquals(2, items.size());
        assertEquals("/context[1]/other_context[1]/items[1]", items.get(0).getPath());
        assertEquals("/context[1]/other_context[1]/items[2]", items.get(1).getPath());


        //and check that retrieving a sub-element also retrieves more than one element
        List<RMObjectWithPath> values = queryContext.findListWithPaths("/context/other_context[id2]/items[id3]/items[id5]/value");
        assertEquals("/context[1]/other_context[1]/items[1]/items[2]/value[1]", values.get(0).getPath());
        assertEquals("/context[1]/other_context[1]/items[2]/items[2]/value[1]", values.get(1).getPath());
        assertEquals(2, values.size());
    }
}
