package com.nedap.archie.rules.evaluation;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pieter.bos on 01/04/16.
 */
public class ParsedRulesEvaluationTest {

    ADLParser parser;
    Archetype archetype;

    @Test
    public void simpleArithmetic() throws Exception {
        parser = new ADLParser();
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("simplearithmetic.adls"));
        System.out.println(archetype);
        RuleEvaluation ruleEvaluation = new RuleEvaluation();
        ruleEvaluation.evaluation(archetype.getRules().getRules());
        assertEquals(19l, ruleEvaluation.getVariableMap().get("arithmetic_test").getValue());
        assertEquals(false, ruleEvaluation.getVariableMap().get("boolean_false_test").getValue());
        assertEquals(true, ruleEvaluation.getVariableMap().get("boolean_true_test").getValue());
        assertEquals(true, ruleEvaluation.getVariableMap().get("boolean_extended_test").getValue());
        assertEquals(true, ruleEvaluation.getVariableMap().get("not_false").getValue());
        assertEquals(false, ruleEvaluation.getVariableMap().get("not_not_not_true").getValue());
        assertEquals(4l, ruleEvaluation.getVariableMap().get("variable_reference").getValue());
    }
}
