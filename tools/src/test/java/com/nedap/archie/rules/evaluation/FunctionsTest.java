package com.nedap.archie.rules.evaluation;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.adlparser.modelconstraints.RMConstraintImposer;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.composition.Observation;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
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

    @Before
    public void setup() {
        parser = new ADLParser(new RMConstraintImposer());
    }

    @Test
    public void min() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("functions.adls"));
        assertTrue(parser.getErrors().hasNoErrors());
        System.out.println(archetype);
        RuleEvaluation ruleEvaluation = new RuleEvaluation(ArchieRMInfoLookup.getInstance(), archetype);
        Observation root = new Observation();
        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        ValueList min = ruleEvaluation.getVariableMap().get("min");
        assertEquals("min should work", 3.0, (double) min.getValues().get(0).getValue(), 0.001);
    }

    @Test
    public void max() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("functions.adls"));
        assertTrue(parser.getErrors().hasNoErrors());
        System.out.println(archetype);
        RuleEvaluation ruleEvaluation = new RuleEvaluation(ArchieRMInfoLookup.getInstance(),archetype);
        Observation root = new Observation();
        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        ValueList min = ruleEvaluation.getVariableMap().get("max");
        assertEquals("max should work", 2.0, (double) min.getValues().get(0).getValue(), 0.001);
    }

    @Test
    public void valueWhenUndefined() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("functions.adls"));
        assertTrue(parser.getErrors().hasNoErrors());

        RuleEvaluation ruleEvaluation = new RuleEvaluation(ArchieRMInfoLookup.getInstance(),archetype);
        Observation root = new Observation();
        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        ValueList valueWhenUndefined = ruleEvaluation.getVariableMap().get("value_when_undefined");
        assertEquals("value when undefined should be set", 10.0d, (double) valueWhenUndefined.getObject(0), 0.00001d);
    }

    @Test
    public void valueWhenUndefinedWithValue() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("functions.adls"));
        assertTrue(parser.getErrors().hasNoErrors());

        RuleEvaluation ruleEvaluation = new RuleEvaluation(ArchieRMInfoLookup.getInstance(),archetype);
        Locatable rmObject = (Locatable) new TestUtil().constructEmptyRMObject(archetype.getDefinition());
        DvQuantity quantity = (DvQuantity) rmObject.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value");
        quantity.setMagnitude(65d);

        ruleEvaluation.evaluate(rmObject, archetype.getRules().getRules());
        ValueList valueWhenUndefined = ruleEvaluation.getVariableMap().get("value_when_undefined");
        assertEquals("value when undefined should be set to original value", 65d, (double) valueWhenUndefined.getObject(0), 0.00001d);
    }
}
