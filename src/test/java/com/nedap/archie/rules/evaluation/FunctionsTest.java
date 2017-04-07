package com.nedap.archie.rules.evaluation;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.composition.Observation;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import com.nedap.archie.testutil.TestUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void valueWhenUndefined() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("functions.adls"));
        assertTrue(parser.getErrors().hasNoErrors());

        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);
        Observation root = new Observation();
        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        ValueList valueWhenUndefined = ruleEvaluation.getVariableMap().get("value_when_undefined");
        assertEquals("value when undefined should be set", 10.0d, (double) valueWhenUndefined.getObject(0), 0.00001d);
    }

    @Test
    public void valueWhenUndefinedWithValue() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("functions.adls"));
        assertTrue(parser.getErrors().hasNoErrors());

        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);
        Locatable rmObject = (Locatable) new TestUtil().constructEmptyRMObject(archetype.getDefinition());
        DvQuantity quantity = (DvQuantity) rmObject.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value");
        quantity.setMagnitude(65d);

        ruleEvaluation.evaluate(rmObject, archetype.getRules().getRules());
        ValueList valueWhenUndefined = ruleEvaluation.getVariableMap().get("value_when_undefined");
        assertEquals("value when undefined should be set to original value", 65d, (double) valueWhenUndefined.getObject(0), 0.00001d);
    }
}
