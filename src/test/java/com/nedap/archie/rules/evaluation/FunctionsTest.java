package com.nedap.archie.rules.evaluation;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.testutil.TestUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by pieter.bos on 06/04/2017.
 */
public class FunctionsTest {

    private ADLParser parser;
    private Archetype archetype;

    private TestUtil testUtil;

    @Before
    public void setup() {
        testUtil = new TestUtil();
        parser = ADLParser.withRMConstraintsImposer();
    }

    @Test
    public void minmax() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("functions.adls"));
        assertTrue(parser.getErrors().hasNoErrors());
        System.out.println(archetype);
    }
}
