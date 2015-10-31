package com.nedap.archie.adlparser;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.primitives.CDuration;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Created by pieter.bos on 31/10/15.
 */
public abstract class PrimitivesConstraintParserTest {

    ADLParser parser;
    Archetype archetype;

    @Before
    public void setup() throws Exception {
        parser = new ADLParser();
        archetype = parser.parse(TemporalConstraintParserTest.class.getResourceAsStream("/adl2-tests/features/aom_structures/basic/openEHR-TEST_PKG-WHOLE.primitive_types.v1.adls"));
    }

    public <T> T getAttribute(String attributeName) {
        return (T) archetype.getDefinition().getAttribute(attributeName).getChildren().get(0);
    }
}
