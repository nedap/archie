package com.nedap.archie.rules.evaluation;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rm.archetyped.Pathable;
import com.nedap.archie.testutil.TestUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pieter.bos on 04/04/2017.
 */
public class FixableAssertionsCheckerTest {

    private ADLParser parser;
    private Archetype archetype;

    private TestUtil testUtil;

    @Before
    public void setup() {
        testUtil = new TestUtil();
        parser = ADLParser.withRMConstraintsImposer();
    }

    @Test
    public void fixableMatches() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("fixable_matches.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());
        EvaluationResult evaluate = ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals("there must be three values that must be set", 3, evaluate.getSetPathValues().size());

        //assert that paths must be set to specific values
        assertEquals("test string", evaluate.getSetPathValues().get("/data[id2]/events[id3]/data[id4]/items[id5]/value/value").getValue());
        assertEquals("at1", evaluate.getSetPathValues().get("/data[id2]/events[id3]/data[id4]/items[id6]/value/defining_code/code_string").getValue());
        assertEquals("at6", evaluate.getSetPathValues().get("/data[id2]/events[id3]/data[id4]/items[id7]/value/symbol/defining_code/code_string").getValue());

        //now assert that the RM Object cloned by rule evaluation has been modified with the new values for further evaluation
        assertEquals("test string", ruleEvaluation.getRMRoot().itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value/value"));
        assertEquals("at1", ruleEvaluation.getRMRoot().itemAtPath("/data[id2]/events[id3]/data[id4]/items[id6]/value/defining_code/code_string"));
        assertEquals("at6", ruleEvaluation.getRMRoot().itemAtPath("/data[id2]/events[id3]/data[id4]/items[id7]/value/symbol/defining_code/code_string"));

        //and of course the DV_ORDINAL and DV_CODED_TEXT should be constructed correctly, with the correct numeric respectively a textual value
        assertEquals(0l, ruleEvaluation.getRMRoot().itemAtPath("/data[id2]/events[id3]/data[id4]/items[id7]/value/value"));
        assertEquals("Option 1", ruleEvaluation.getRMRoot().itemAtPath("/data[id2]/events[id3]/data[id4]/items[id6]/value/value"));


        evaluate = ruleEvaluation.evaluate(ruleEvaluation.getRMRoot(), archetype.getRules().getRules());
        for(AssertionResult result:evaluate.getAssertionResults()) {
            assertTrue(result.getResult());
        }

    }

    @Test
    public void andExpression() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("and.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());
        EvaluationResult evaluate = ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals("there must be three values that must be set", 3, evaluate.getSetPathValues().size());

        //assert that paths must be set to specific values
        assertEquals("test string", evaluate.getSetPathValues().get("/data[id2]/events[id3]/data[id4]/items[id5]/value/value").getValue());
        assertEquals("at1", evaluate.getSetPathValues().get("/data[id2]/events[id3]/data[id4]/items[id6]/value/defining_code/code_string").getValue());
        assertEquals("at6", evaluate.getSetPathValues().get("/data[id2]/events[id3]/data[id4]/items[id7]/value/symbol/defining_code/code_string").getValue());

        //now assert that the RM Object cloned by rule evaluation has been modified with the new values for further evaluation
        assertEquals("test string", ruleEvaluation.getRMRoot().itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value/value"));
        assertEquals("at1", ruleEvaluation.getRMRoot().itemAtPath("/data[id2]/events[id3]/data[id4]/items[id6]/value/defining_code/code_string"));
        assertEquals("at6", ruleEvaluation.getRMRoot().itemAtPath("/data[id2]/events[id3]/data[id4]/items[id7]/value/symbol/defining_code/code_string"));

        //and of course the DV_ORDINAL and DV_CODED_TEXT should be constructed correctly, with the correct numeric respectively a textual value
        assertEquals(0l, ruleEvaluation.getRMRoot().itemAtPath("/data[id2]/events[id3]/data[id4]/items[id7]/value/value"));
        assertEquals("Option 1", ruleEvaluation.getRMRoot().itemAtPath("/data[id2]/events[id3]/data[id4]/items[id6]/value/value"));


        evaluate = ruleEvaluation.evaluate(ruleEvaluation.getRMRoot(), archetype.getRules().getRules());
        for(AssertionResult result:evaluate.getAssertionResults()) {
            assertTrue(result.getResult());
        }

    }

}
