package com.nedap.archie.treewalker;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by pieter.bos on 20/10/15.
 */
public class RMArchetypeTreeListenerTest {

    Archetype archetype;

    boolean archetypeBegan = false;
    boolean quantityFound = false;

    @Before
    public void setup() throws Exception {
        archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));
    }

    @Test
    public void testTreeWalker() {

        RMArchetypeTreeListener listener = new BaseRMArchetypeTreeListener() {
            @Override
            public void beginArchetype(Archetype archetype) {
                archetypeBegan = true;
                assertEquals("openEHR-EHR-COMPOSITION.annotations_rm_path.v1.0.0", archetype.getArchetypeId().getFullId());
            }

            @Override
            public void endArchetype(Archetype archetype) {

            }

            @Override
            public void quantity(CComplexObject parent, CComplexObject elementConstraint) {
                //TODO: check tuple constraint present
                if(elementConstraint.getNodeId().equals("id16")) {
                    quantityFound = true;
                    assertEquals(1, elementConstraint.getAttributeTuples().size());
                    assertEquals("id7", ((CObject) elementConstraint.getParent().getParent()).getNodeId());
                } else {
                    fail("unknown quantity id: " + elementConstraint.getNodeId());
                }
            }
        };
        listener.walk(archetype);
        assertTrue(archetypeBegan);
        assertTrue(quantityFound);
    }



}
