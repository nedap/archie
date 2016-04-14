package com.nedap.archie.rules.evaluation;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rm.archetypes.Pathable;
import com.nedap.archie.rm.composition.Observation;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import com.nedap.archie.testutil.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pieter.bos on 01/04/16.
 */
public class ParsedRulesEvaluationTest {

    ADLParser parser;
    Archetype archetype;

    TestUtil testUtil;

    @Before
    public void setup() {
        testUtil = new TestUtil();
        parser = ADLParser.withRMConstraintsImposer();
    }

    @Test
    public void simpleArithmetic() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("simplearithmetic.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);
        Observation root = new Observation();
        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(19l, ruleEvaluation.getVariableMap().get("arithmetic_test").get(0));
        assertEquals(false, ruleEvaluation.getVariableMap().get("boolean_false_test").get(0));
        assertEquals(true, ruleEvaluation.getVariableMap().get("boolean_true_test").get(0));
        assertEquals(true, ruleEvaluation.getVariableMap().get("boolean_extended_test").get(0));
        assertEquals(true, ruleEvaluation.getVariableMap().get("not_false").get(0));
        assertEquals(false, ruleEvaluation.getVariableMap().get("not_not_not_true").get(0));
        assertEquals(4l, ruleEvaluation.getVariableMap().get("variable_reference").get(0));
    }


    @Test
    public void modelReferences() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("modelreferences.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

        DvQuantity quantity = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
        quantity.setMagnitude(65d);

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());

        assertEquals(65d, (Double) ruleEvaluation.getVariableMap().get("arithmetic_test").get(0), 0.001d);

        List<AssertionResult> assertionResults = ruleEvaluation.getAssertionResults();
        assertEquals("one assertion should have been checked", 1, assertionResults.size());
        AssertionResult result = assertionResults.get(0);

        assertEquals("the assertion should have succeeded", true, result.getResult());
        assertEquals("the assertion tag should be correct", "blood_pressure_valid", result.getTag());
    }

    @Test
    public void booleanConstraint() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("matches.adls"));

        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

        DvQuantity quantity = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
        quantity.setMagnitude(40d);

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(false, ruleEvaluation.getVariableMap().get("extended_validity").get(0));

        quantity.setMagnitude(20d);

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(true, ruleEvaluation.getVariableMap().get("extended_validity").get(0));

    }

    @Test
    public void multiValuedExpressions() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("multiplicity.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

        {
            DvQuantity systolic = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
            systolic.setMagnitude(76d);
            DvQuantity diastolic = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id6]/value[id14]");
            diastolic.setMagnitude(80d);
            //this is fine, because "blood_pressure_valid: $systolic > $diastolic - 5"
        }


        //add a second event
        {
            Pathable root2 = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

            DvQuantity systolic = (DvQuantity) root2.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
            systolic.setMagnitude(60d);
            DvQuantity diastolic = (DvQuantity) root2.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id6]/value[id14]");
            diastolic.setMagnitude(80d);
            Observation observation = (Observation) root;
            Observation observation2 = (Observation) root2;
            observation.getData().addEvent(observation2.getData().getEvents().get(0));
            //a strange reading that will lead to a failure
        }

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());

        List<AssertionResult> assertionResults = ruleEvaluation.getAssertionResults();
        assertEquals("one assertion should have been checked", 1, assertionResults.size());
        AssertionResult result = assertionResults.get(0);

        assertEquals("the assertion should not have succeeded", false, result.getResult());
        assertEquals("the assertion tag should be correct", "blood_pressure_valid", result.getTag());
        ValueList rawResult = result.getRawResult();
        assertEquals("two checks have been done", 2, rawResult.size());
        assertTrue(rawResult.getValues().contains(true));
        assertTrue(rawResult.getValues().contains(false));
    }




}
