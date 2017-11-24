package com.nedap.archie.rules.evaluation;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.composition.Observation;
import com.nedap.archie.rm.datastructures.Item;
import com.nedap.archie.rm.datastructures.ItemTree;
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

    @Before
    public void setup() {
        parser = ADLParser.withRMConstraintsImposer();
    }

    @Test
    public void min() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("functions.adls"));
        assertTrue(parser.getErrors().hasNoErrors());
        System.out.println(archetype);
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);
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
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);
        Observation root = new Observation();
        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        ValueList min = ruleEvaluation.getVariableMap().get("max");
        assertEquals("max should work", 2.0, (double) min.getValues().get(0).getValue(), 0.001);
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

    @Test
    public void round() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("functions.adls"));
        assertTrue(parser.getErrors().hasNoErrors());

        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);
        Observation root = new Observation();
        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        ValueList roundedUp = ruleEvaluation.getVariableMap().get("round_up");
        ValueList roundedDown = ruleEvaluation.getVariableMap().get("round_down");
        assertEquals("round should round up", 2L, (long) roundedUp.getValues().get(0).getValue());
        assertEquals("round should round down", 1L, (long) roundedDown.getValues().get(0).getValue());
    }

    @Test
    public void roundMultiple() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("functions.adls"));
        assertTrue(parser.getErrors().hasNoErrors());

        Locatable rmObject = (Locatable) new TestUtil().constructEmptyRMObject(archetype.getDefinition());
        ItemTree itemTree = (ItemTree) rmObject.itemAtPath("/data[id2]/events[id3]/data[id4]");
        Item item = (Item) itemTree.itemAtPath("/items[id5]");
        itemTree.addItem( (Item) item.clone() );

        DvQuantity quantity1 = (DvQuantity) itemTree.itemAtPath("/items[id5,1]/value");
        DvQuantity quantity2 = (DvQuantity) itemTree.itemAtPath("/items[id5,4]/value");
        quantity1.setMagnitude(65.4);
        quantity2.setMagnitude(45.6);

        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);
        ruleEvaluation.evaluate(rmObject, archetype.getRules().getRules());
        ValueList roundMultiple = ruleEvaluation.getVariableMap().get("round_multiple");
        assertEquals("rounds each value in a value list", 2, roundMultiple.getValues().size());
        assertEquals("first value is rounded", 65L, (long) roundMultiple.getValues().get(0).getValue());
        assertEquals("second value is rounded", 46L, (long) roundMultiple.getValues().get(1).getValue());
    }
}
