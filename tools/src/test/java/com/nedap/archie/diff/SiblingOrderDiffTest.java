package com.nedap.archie.diff;

import com.nedap.archie.diff.Differentiator;
import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.SiblingOrder;
import com.nedap.archie.archetypevalidator.ValidationResult;
import com.nedap.archie.flattener.InMemoryFullArchetypeRepository;
import com.nedap.archie.serializer.adl.ADLArchetypeSerializer;
import org.junit.Before;
import org.junit.Test;
import org.openehr.referencemodels.BuiltinReferenceModels;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class SiblingOrderDiffTest {

    @Before
    public void setup() throws Exception {

    }

    private Archetype parse(String filename) throws IOException {
        try(InputStream stream = getClass().getResourceAsStream( filename))  {
            ADLParser parser = new ADLParser();
            Archetype archetype = parser.parse(stream);
            if(parser.getErrors().hasErrors()) {
                throw new RuntimeException(parser.getErrors().toString());
            }
            return archetype;
        }
    }

    @Test
    public void testAfterSiblingOrder() throws Exception {
        Archetype archetype = parse("openEHR-EHR-CLUSTER.siblingorderparent.v0.0.1.adls");
        Archetype specializedArchetype = parse("openEHR-EHR-CLUSTER.siblingorderchild1.v0.0.1.adls");
        InMemoryFullArchetypeRepository repo = new InMemoryFullArchetypeRepository();
        repo.addArchetype(archetype);
        repo.addArchetype(specializedArchetype);
        repo.compile(BuiltinReferenceModels.getMetaModels());
        ValidationResult flatChild = repo.getValidationResult("openEHR-EHR-CLUSTER.siblingorderchild1.v0.0.1");
        ValidationResult flatParent = repo.getValidationResult("openEHR-EHR-CLUSTER.siblingorderparent.v0.0.1");
        assertTrue(flatParent.getErrors().toString(), flatParent.getErrors().isEmpty());
        assertTrue(flatChild.getErrors().toString(), flatChild.getErrors().isEmpty());

        Archetype result = new Differentiator(BuiltinReferenceModels.getMetaModels()).differentiate(flatChild.getFlattened(), flatParent.getFlattened());

        System.out.println(ADLArchetypeSerializer.serialize(result));

        CAttribute items = result.getDefinition().getAttribute("items");
        assertEquals(1, items.getChildren().size());
        CObject cObject = items.getChildren().get(0);
        assertEquals("id4.1", cObject.getNodeId());
        assertEquals(SiblingOrder.createAfter("id6"), cObject.getSiblingOrder());


    }

    @Test
    public void testAnchor() throws Exception {
        Archetype archetype = parse("openEHR-EHR-CLUSTER.order-parent.v1.0.0.adls");
        Archetype specializedArchetype = parse("openEHR-EHR-CLUSTER.test_anchoring.v1.0.0.adls");
        InMemoryFullArchetypeRepository repo = new InMemoryFullArchetypeRepository();
        repo.addArchetype(archetype);
        repo.addArchetype(specializedArchetype);
        repo.compile(BuiltinReferenceModels.getMetaModels());
        ValidationResult flatChild = repo.getValidationResult("openEHR-EHR-CLUSTER.test_anchoring.v1.0.0");
        ValidationResult flatParent = repo.getValidationResult("openEHR-EHR-CLUSTER.order-parent.v1.0.0");
        assertTrue(flatParent.getErrors().toString(), flatParent.getErrors().isEmpty());
        assertTrue(flatChild.getErrors().toString(), flatChild.getErrors().isEmpty());

        Archetype result = new Differentiator(BuiltinReferenceModels.getMetaModels()).differentiate(flatChild.getFlattened(), flatParent.getFlattened());

        System.out.println(ADLArchetypeSerializer.serialize(result));

        CAttribute items = result.getDefinition().getAttribute("items");
        assertEquals(4, items.getChildren().size());
        CObject cObject = items.getChildren().get(0);
        assertEquals("id0.1", cObject.getNodeId());
        assertEquals(SiblingOrder.createAfter("id3"), cObject.getSiblingOrder());

        cObject = items.getChildren().get(1);
        assertEquals("id0.2", cObject.getNodeId());
        assertNull(cObject.getSiblingOrder());

        cObject = items.getChildren().get(2);
        assertEquals("id0.3", cObject.getNodeId());
        assertEquals(SiblingOrder.createAfter("id4"), cObject.getSiblingOrder());

        cObject = items.getChildren().get(3);
        assertEquals("id0.4", cObject.getNodeId());
        assertNull(cObject.getSiblingOrder());



    }
}
