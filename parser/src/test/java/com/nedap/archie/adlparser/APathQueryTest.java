package com.nedap.archie.adlparser;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.query.APathQuery;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by pieter.bos on 20/10/15.
 */
public class APathQueryTest {

    @Test
    public void basicPaths() throws Exception {
        Archetype archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));
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
        Archetype archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));
        APathQuery query = new APathQuery("/context[id11 and name=\"ignored\"]");
        ArchetypeModelObject archetypeModelObject = query.find(archetype.getDefinition());
        assertEquals("EVENT_CONTEXT", ((CComplexObject) archetypeModelObject).getRmTypeName());

    }

    @Test
    public void logicalPaths() throws Exception {
        Archetype archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));
        APathQuery query = new APathQuery("/context[id11]/other_context[id2]/items[qualification]/items[orderid]");
        ArchetypeModelObject archetypeModelObject = query.find(archetype.getDefinition());
        assertNotNull(archetypeModelObject);

    }
}
