package com.nedap.archie.query;

import com.google.common.collect.Lists;
import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rm.RMObject;
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
 * Created by pieter.bos on 03/05/16.
 */
public class RMQueryTest {

    TestUtil testUtil;
    Archetype archetype;
    Pathable root;

    @Before
    public void setup() throws Exception {
        RMObjectPointerFactory.register();
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
}
