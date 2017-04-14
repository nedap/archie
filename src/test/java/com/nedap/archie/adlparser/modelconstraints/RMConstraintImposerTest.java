package com.nedap.archie.adlparser.modelconstraints;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.Cardinality;
import com.nedap.archie.base.MultiplicityInterval;
import com.nedap.archie.query.APathQuery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class RMConstraintImposerTest {

    private Archetype archetype;

    @Before
    public void setup() throws Exception {
        archetype = ADLParser.withRMConstraintsImposer().parse(getClass().getResourceAsStream("/adl2-tests/features/alternatives/openEHR-EHR-ADMIN_ENTRY.dependency_choice.v1.adls"));
    }

    @Test
    public void singleValuedRequired() {
        CAttribute attribute = archetype.getDefinition().getAttribute("data");
        Cardinality cardinality = attribute.getCardinality();
        assertNull(cardinality);
        MultiplicityInterval existence = attribute.getExistence();
        assertEquals(new Integer(1), existence.getLower());
        assertEquals(new Integer(1), existence.getUpper());

        assertFalse(attribute.isMultiple());
    }

    @Test
    public void multiValuedOptional() throws Exception {
        //ITEM_LIST.items is an optional field
        CAttribute attribute = archetype.getDefinition().getAttribute("data").getChild("id2").getAttribute("items");
        Cardinality cardinality = attribute.getCardinality();
        assertNotNull(cardinality);
        assertEquals(new Integer(0), cardinality.getInterval().getLower());
        assertTrue(cardinality.getInterval().isLowerIncluded());
        assertTrue(cardinality.getInterval().isUpperIncluded());
        assertTrue(attribute.isMultiple());

        MultiplicityInterval existence = attribute.getExistence();
        assertEquals(new Integer(0), existence.getLower());
        assertEquals(new Integer(1), existence.getUpper());
    }

    @Test
    public void multiValuedRequired() throws Exception {
        //CLUSTER.items is a required field

        CAttribute attribute = archetype.getDefinition().getAttribute("data").getChild("id2").getAttribute("items").getChild("id3").getAttribute("items");
        Cardinality cardinality = attribute.getCardinality();
        assertNotNull(cardinality);
        assertEquals(new Integer(0), cardinality.getInterval().getLower());
        assertTrue(cardinality.getInterval().isLowerIncluded());
        assertTrue(cardinality.getInterval().isUpperIncluded());
        assertTrue(attribute.isMultiple());

        MultiplicityInterval existence = attribute.getExistence();
        assertEquals(new Integer(1), existence.getLower());
        assertEquals(new Integer(1), existence.getUpper());
    }


    @Test
    public void singleValuedOptional() {
        CAttribute attribute = new APathQuery("/data[id2]/items[id3]/items[id4]/value").find(archetype.getDefinition());
        Cardinality cardinality = attribute.getCardinality();
        assertNull(cardinality);
        MultiplicityInterval existence = attribute.getExistence();
        assertEquals(new Integer(0), existence.getLower());
        assertEquals(new Integer(1), existence.getUpper());

        assertFalse(attribute.isMultiple());
    }
}
