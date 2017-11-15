package com.nedap.archie.adlparser;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.primitives.CDuration;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by pieter.bos on 31/10/15.
 */
public abstract class PrimitivesConstraintParserTest {

    protected ADLParser parser;
    protected Archetype archetype;

    @Before
    public void setup() throws Exception {
        System.out.println("IS THIS ON?");
        parser = new ADLParser();
        archetype = parser.parse(TemporalConstraintParserTest.class.getResourceAsStream("/adl2-tests/features/aom_structures/basic/openEHR-TEST_PKG-WHOLE.primitive_types.v1.0.0.adls"));
        parser.getErrors().logToLogger();
        assertFalse(parser.getErrors().toString(), parser.getErrors().hasErrors());
        assertNotNull(archetype);
        System.out.println("It IS ON");
    }

    public <T> T getAttribute(String attributeName) {
        return (T) archetype.getDefinition().getAttribute(attributeName).getChildren().get(0);
    }
}
