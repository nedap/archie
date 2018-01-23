package com.nedap.archie.adlparser;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ComponentTerminologiesParseTest {

    @Test
    public void shortForm() throws Exception {
        ADLParser parser = new ADLParser();
        parser.parse(getClass().getResourceAsStream("openEHR-EHR-ELEMENT.component_terminologies_1.v1.0.0.adls"));
        assertTrue(parser.getErrors().hasNoErrors());
    }

    @Test
    public void extendedForm() throws Exception {
        ADLParser parser = new ADLParser();
        parser.parse(getClass().getResourceAsStream("openEHR-EHR-ELEMENT.component_terminologies_2.v1.0.0.adls"));
        assertTrue(parser.getErrors().hasNoErrors());
    }
}
