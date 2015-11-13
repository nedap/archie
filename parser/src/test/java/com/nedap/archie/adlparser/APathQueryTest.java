package com.nedap.archie.adlparser;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.query.APathQuery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by pieter.bos on 20/10/15.
 */
public class APathQueryTest {

    Archetype archetype;

    @Before
    public void setup() throws Exception {
        archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));
    }


    @Test
    public void basicPaths() throws Exception {

        APathQuery query = new APathQuery("/context[id11]");
        ArchetypeModelObject archetypeModelObject = query.find(archetype.getDefinition());
        assertEquals("EVENT_CONTEXT", ((CComplexObject) archetypeModelObject).getRmTypeName());

        //"/context[id11]/other_context[id2]/items[id3]/items[id4]/value
        query = new APathQuery("/context[id11]/other_context");
        archetypeModelObject = query.find(archetype.getDefinition());
        assertEquals("other_context", ((CAttribute) archetypeModelObject).getRmAttributeName());

        query = new APathQuery("/context[id11]/other_context[id2]");
        archetypeModelObject = query.find(archetype.getDefinition());
        assertEquals("ITEM_TREE", ((CComplexObject) archetypeModelObject).getRmTypeName());
    }

    @Test
    public void nameAttributeIgnoredForNow() throws Exception {
        APathQuery query = new APathQuery("/context[id11 and name=\"ignored\"]");
        ArchetypeModelObject archetypeModelObject = query.find(archetype.getDefinition());
        assertEquals("EVENT_CONTEXT", ((CComplexObject) archetypeModelObject).getRmTypeName());

    }

    @Test
    public void logicalPaths() throws Exception {
        APathQuery query = new APathQuery("/context[id11]/other_context[id2]/items[qualification]/items[orderid]");
        ArchetypeModelObject archetypeModelObject = query.find(archetype.getDefinition());
        assertNotNull(archetypeModelObject);
        assertEquals("id4", ((CComplexObject) archetypeModelObject).getNodeId());

    }


    @Test
    public void indexedPath() throws Exception {
        APathQuery query = new APathQuery("/context[id11]/other_context[id2]/items[qualification]/items[2]");
        ArchetypeModelObject archetypeModelObject = query.find(archetype.getDefinition());
        assertNotNull(archetypeModelObject);
        assertEquals("id5", ((CComplexObject) archetypeModelObject).getNodeId());

    }
}
