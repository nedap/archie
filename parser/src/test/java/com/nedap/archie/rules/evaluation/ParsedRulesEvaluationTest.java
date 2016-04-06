package com.nedap.archie.rules.evaluation;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.creation.RMObjectCreator;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.archetypes.Pathable;
import com.nedap.archie.rm.composition.Observation;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.testutil.TestUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        parser = new ADLParser();
    }

    @Test
    public void simpleArithmetic() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("simplearithmetic.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);
        Observation root = new Observation();
        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(19l, ruleEvaluation.getVariableMap().get("arithmetic_test").getValue());
        assertEquals(false, ruleEvaluation.getVariableMap().get("boolean_false_test").getValue());
        assertEquals(true, ruleEvaluation.getVariableMap().get("boolean_true_test").getValue());
        assertEquals(true, ruleEvaluation.getVariableMap().get("boolean_extended_test").getValue());
        assertEquals(true, ruleEvaluation.getVariableMap().get("not_false").getValue());
        assertEquals(false, ruleEvaluation.getVariableMap().get("not_not_not_true").getValue());
        assertEquals(4l, ruleEvaluation.getVariableMap().get("variable_reference").getValue());
    }


    @Test
    public void modelReferences() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("modelreferences.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

        DvQuantity quantity = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
        quantity.setMagnitude(65d);

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());

        assertEquals(65d, (Double) ruleEvaluation.getVariableMap().get("arithmetic_test").getValue(), 0.001d);

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
        assertEquals(false, ruleEvaluation.getVariableMap().get("extended_validity").getValue());

        quantity.setMagnitude(20d);

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(true, ruleEvaluation.getVariableMap().get("extended_validity").getValue());


    }


}
