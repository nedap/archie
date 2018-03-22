package com.nedap.archie.aom;

import com.google.common.collect.Sets;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArchetypeTest {

    @Test
    public void testGetUsedIdCodes() {
        Archetype archetype = createLevel0Archetype();
        assertEquals(Sets.newHashSet("id1", "id2"), archetype.getUsedIdCodes());
    }

    @Test
    public void testLevel0GenerateIdCode() {
        Archetype archetype = createLevel0Archetype();
        assertEquals("id3", archetype.generateNextIdCode());
    }

    @Test
    public void testLevel1GenerateIdCode() {
        Archetype archetype = createLevel1Archetype();
        assertEquals("id0.3", archetype.generateNextIdCode());

    }

    private Archetype createLevel0Archetype() {
        Archetype archetype = new Archetype();
        CComplexObject definition = new CComplexObject();
        definition.setRmTypeName("CLUSTER");
        definition.setNodeId("id1");
        CAttribute attribute1 = new CAttribute("items");
        definition.addAttribute(attribute1);
        CComplexObject element = new CComplexObject();
        element.setRmTypeName("ELEMENT");
        element.setNodeId("id2");
        attribute1.addChild(element);
        archetype.setDefinition(definition);
        return archetype;
    }

    private Archetype createLevel1Archetype() {
        Archetype archetype = new Archetype();
        CComplexObject definition = new CComplexObject();
        definition.setRmTypeName("CLUSTER");
        definition.setNodeId("id1.1");
        CAttribute attribute1 = new CAttribute("items");
        definition.addAttribute(attribute1);
        CComplexObject element = new CComplexObject();
        element.setRmTypeName("ELEMENT");
        element.setNodeId("id0.2");
        attribute1.addChild(element);
        archetype.setDefinition(definition);
        return archetype;
    }
}
