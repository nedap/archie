package com.nedap.archie.rules.evaluation;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rm.archetyped.Pathable;
import com.nedap.archie.rm.composition.Observation;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import com.nedap.archie.rules.Assertion;
import com.nedap.archie.rules.BinaryOperator;
import com.nedap.archie.rules.ExpressionVariable;
import com.nedap.archie.rules.RuleStatement;
import com.nedap.archie.rules.VariableDeclaration;
import com.nedap.archie.testutil.TestUtil;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by pieter.bos on 01/04/16.
 */
public class ParsedRulesEvaluationTest {

    private ADLParser parser;
    private Archetype archetype;

    private TestUtil testUtil;

    @Before
    public void setup() {
        testUtil = new TestUtil();
        parser = ADLParser.withRMConstraintsImposer();
    }

    @Test
    public void precedenceOverride() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("simplearithmetic.adls"));

        ExpressionVariable booleanExtendedTest = (ExpressionVariable) getVariableDeclarationByName(archetype, "boolean_extended_test");
        BinaryOperator operator = (BinaryOperator) booleanExtendedTest.getExpression();
        assertTrue(operator.getLeftOperand().isPrecedenceOverriden());
        assertFalse(operator.getRightOperand().isPrecedenceOverriden());

        ExpressionVariable arithmeticParentheses = (ExpressionVariable) getVariableDeclarationByName(archetype, "arithmetic_parentheses");
        BinaryOperator arithmeticOperator = (BinaryOperator) arithmeticParentheses.getExpression();
        assertTrue(arithmeticOperator.getLeftOperand().isPrecedenceOverriden());
        assertFalse(arithmeticOperator.getRightOperand().isPrecedenceOverriden());

    }

    @Test
    public void simpleArithmetic() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("simplearithmetic.adls"));
        assertTrue(parser.getErrors().hasNoMessages());
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);
        Observation root = new Observation();
        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        VariableMap variables = ruleEvaluation.getVariableMap();
        assertEquals(8l, variables.get("arithmetic_test").getObject(0));
        assertTrue(variables.get("arithmetic_test").getPaths(0).isEmpty());
        assertEquals(false, variables.get("boolean_false_test").getObject(0));
        assertTrue(variables.get("boolean_false_test").getPaths(0).isEmpty());
        assertEquals(true, variables.get("boolean_true_test").getObject(0));
        assertTrue(variables.get("boolean_true_test").getPaths(0).isEmpty());
        assertEquals(true, variables.get("boolean_extended_test").getObject(0));
        assertTrue(variables.get("boolean_extended_test").getPaths(0).isEmpty());
        assertEquals(true, variables.get("not_false").getObject(0));
        assertTrue(variables.get("not_false").getPaths(0).isEmpty());
        assertEquals(false, variables.get("not_not_not_true").getObject(0));
        assertTrue(variables.get("not_not_not_true").getPaths(0).isEmpty());
        assertEquals(3l, variables.get("variable_reference").getObject(0));
        assertTrue(variables.get("variable_reference").getPaths(0).isEmpty());
        assertEquals(25l, variables.get("arithmetic_parentheses").getObject(0));
        assertTrue(variables.get("arithmetic_parentheses").getPaths(0).isEmpty());

    }

    private VariableDeclaration getVariableDeclarationByName(Archetype archetype, String name) {
        for(RuleStatement statement:archetype.getRules().getRules()) {
            if(statement instanceof VariableDeclaration) {
                VariableDeclaration variable = (VariableDeclaration) statement;
                if(Objects.equals(variable.getName(), name)) {
                    return variable;
                }
            }
        }
        return null;
    }

    private Assertion getAssertionByTag(Archetype archetype, String tag) {
        for(RuleStatement statement:archetype.getRules().getRules()) {
            if(statement instanceof Assertion) {
                Assertion assertion = (Assertion) statement;
                if(Objects.equals(assertion.getTag(), tag)) {
                    return assertion;
                }
            }
        }
        return null;
    }



    @Test
    public void modelReferences() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("modelreferences.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

        DvQuantity quantity = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
        quantity.setMagnitude(65d);

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());

        assertEquals(65d, (Double) ruleEvaluation.getVariableMap().get("arithmetic_test").getObject(0), 0.001d);

        List<AssertionResult> assertionResults = ruleEvaluation.getEvaluationResult().getAssertionResults();
        assertEquals("one assertion should have been checked", 1, assertionResults.size());
        AssertionResult result = assertionResults.get(0);

        assertEquals("the assertion should have succeeded", true, result.getResult());
        assertEquals("the assertion tag should be correct", "blood_pressure_valid", result.getTag());
        assertEquals(1, result.getRawResult().getPaths(0).size());
        assertEquals("/data[id2, 1]/events[id3, 1]/data[id4, 1]/items[id5, 1]/value[1]/magnitude[1]", result.getRawResult().getPaths(0).get(0));

    }

    @Test
    public void booleanConstraint() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("matches.adls"));

        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

        DvQuantity quantity = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
        quantity.setMagnitude(40d);

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(false, ruleEvaluation.getVariableMap().get("extended_validity").getObject(0));
        ValueList extendedValidity = ruleEvaluation.getVariableMap().get("extended_validity");
        assertEquals("/data[id2, 1]/events[id3, 1]/data[id4, 1]/items[id5, 1]/value[1]/magnitude[1]", extendedValidity.getPaths(0).get(0));
        quantity.setMagnitude(20d);

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        extendedValidity = ruleEvaluation.getVariableMap().get("extended_validity");
        assertEquals(true, extendedValidity.getObject(0));
        assertEquals("/data[id2, 1]/events[id3, 1]/data[id4, 1]/items[id5, 1]/value[1]/magnitude[1]", extendedValidity.getPaths(0).get(0));

    }

    @Test
    public void multiValuedExpressions() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("multiplicity.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = constructTwoBloodPressureObservations();

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());

        List<AssertionResult> assertionResults = ruleEvaluation.getEvaluationResult().getAssertionResults();
        assertEquals("one assertion should have been checked", 1, assertionResults.size());
        AssertionResult result = assertionResults.get(0);

        assertEquals("the assertion should have succeeded", true, result.getResult());
        assertEquals("the assertion tag should be correct", "blood_pressure_valid", result.getTag());
    }

    @Test
    public void forAllExpression() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("for_all.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = constructTwoBloodPressureObservations();

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());

        List<AssertionResult> assertionResults = ruleEvaluation.getEvaluationResult().getAssertionResults();
        assertEquals("one assertion should have been checked", 1, assertionResults.size());
        AssertionResult result = assertionResults.get(0);

        assertEquals("the assertion should have succeeded", false, result.getResult());
        assertEquals("the assertion tag should be correct", "blood_pressure_valid", result.getTag());
    }

    @NotNull
    public Pathable constructTwoBloodPressureObservations() {
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
        return root;
    }

    @NotNull
    public Pathable constructTwoBloodPressureObservationsOneEmptySystolic() {
        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

        {
            DvQuantity systolic = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
            systolic.setMagnitude(120d);
            DvQuantity diastolic = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id6]/value[id14]");
            diastolic.setMagnitude(80d);
        }


        {
            Pathable root2 = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

            DvQuantity systolic = (DvQuantity) root2.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
            //systolic.setMagnitude(60d);
            DvQuantity diastolic = (DvQuantity) root2.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id6]/value[id14]");
            diastolic.setMagnitude(80d);
            Observation observation = (Observation) root;
            Observation observation2 = (Observation) root2;
            observation.getData().addEvent(observation2.getData().getEvents().get(0));
        }
        return root;
    }

    @NotNull
    public Pathable constructTwoBloodPressureObservationsOneEmptySystolicNoDiastolic() {
        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

        {
            DvQuantity systolic = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
            systolic.setMagnitude(120d);
            DvQuantity diastolic = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id6]/value[id14]");
        }


        //add a second event
        {
            Pathable root2 = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

            DvQuantity systolic = (DvQuantity) root2.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
            //systolic.setMagnitude(60d);
            DvQuantity diastolic = (DvQuantity) root2.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id6]/value[id14]");
            Observation observation = (Observation) root;
            Observation observation2 = (Observation) root2;
            observation.getData().addEvent(observation2.getData().getEvents().get(0));
        }
        return root;
    }



    @Test
    public void calculatedPathValues() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("calculated_path_values.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());
        DvQuantity systolic = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
        systolic.setMagnitude(100d);
        DvQuantity diastolic = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id6]/value[id14]");
        diastolic.setMagnitude(80d);

        EvaluationResult evaluationResult = ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(1, evaluationResult.getAssertionResults().size());
        assertFalse(evaluationResult.getAssertionResults().get(0).getResult());
        assertEquals(1, evaluationResult.getSetPathValues().size());
        assertEquals(20.0d, (Double) evaluationResult.getSetPathValues().values().iterator().next().getValue(), 0.0001d);
    }

    @Test
    public void forAllCalculatedValues() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("for_all_calculated_path_values.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = constructTwoBloodPressureObservations();

        //-4 and -20 (sorry about the strange input)

        EvaluationResult evaluationResult = ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(1, evaluationResult.getAssertionResults().size());
        assertFalse(evaluationResult.getAssertionResults().get(0).getResult());
        assertEquals(2, evaluationResult.getSetPathValues().size());
        Iterator<Value> setValuesIterator = evaluationResult.getSetPathValues().values().iterator();
        assertEquals(-4.0d, (Double) setValuesIterator.next().getValue(), 0.0001d);
        assertEquals(-20.0d, (Double) setValuesIterator.next().getValue(), 0.0001d);
    }

    @Test
    public void existsSucceeded() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("exists.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = constructTwoBloodPressureObservations();

        EvaluationResult evaluationResult = ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(3, evaluationResult.getAssertionResults().size());
        for(AssertionResult assertionResult:evaluationResult.getAssertionResults()) {
            assertTrue(assertionResult.getResult());//TODO: check paths that caused this
        }

        assertEquals(4, evaluationResult.getPathsThatMustExist().size());
        assertEquals(0, evaluationResult.getPathsThatMustNotExist().size());
        assertEquals(0, evaluationResult.getSetPathValues().size());

    }


    @Test
    public void existsFailed() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("exists.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

        EvaluationResult evaluationResult = ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(3, evaluationResult.getAssertionResults().size());
        for(AssertionResult assertionResult:evaluationResult.getAssertionResults()) {
            assertFalse(assertionResult.getResult());
        }

        assertEquals(3, evaluationResult.getPathsThatMustExist().size());
        assertEquals("/data[id2]/events[id3]/data[id4]/items[id5]/value/magnitude", evaluationResult.getPathsThatMustExist().get(0));
        assertEquals("/data[id2]/events[id3]/data[id4]/items[id6]/value/magnitude", evaluationResult.getPathsThatMustExist().get(1));
        assertEquals("/data[id2, 1]/events[id3, 1]/data[id4]/items[id5]/value/magnitude", evaluationResult.getPathsThatMustExist().get(2));
        assertEquals(0, evaluationResult.getPathsThatMustNotExist().size());
        assertEquals(0, evaluationResult.getSetPathValues().size());

    }

    @Test
    public void notExistsSucceeded() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("not_exists.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) testUtil.constructEmptyRMObject(archetype.getDefinition());

        EvaluationResult evaluationResult = ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(3, evaluationResult.getAssertionResults().size());
        for(AssertionResult assertionResult:evaluationResult.getAssertionResults()) {
            assertTrue(assertionResult.getResult());
        }

        assertEquals(0, evaluationResult.getPathsThatMustExist().size());
        //the paths should not be creatable, so they should still be present in the result
        assertEquals(3, evaluationResult.getPathsThatMustNotExist().size());
        assertEquals(0, evaluationResult.getSetPathValues().size());

    }

    @Test
    public void notExistsFailed() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("not_exists.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = constructTwoBloodPressureObservations();

        EvaluationResult evaluationResult = ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(3, evaluationResult.getAssertionResults().size());
        for(AssertionResult assertionResult:evaluationResult.getAssertionResults()) {
            assertFalse(assertionResult.getResult());
        }

        assertEquals(0, evaluationResult.getPathsThatMustExist().size());
        assertEquals(6, evaluationResult.getPathsThatMustNotExist().size());
        assertEquals(0, evaluationResult.getSetPathValues().size());

    }

    @Test
    public void existsMixed() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("exists.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = constructTwoBloodPressureObservationsOneEmptySystolic();

        EvaluationResult evaluationResult = ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(3, evaluationResult.getAssertionResults().size());
        assertTrue(evaluationResult.getAssertionResults().get(0).getResult());//exists systolic. At least one true means true, so true
        assertTrue(evaluationResult.getAssertionResults().get(1).getResult()); //exists diastolic. This is always true
        assertFalse(evaluationResult.getAssertionResults().get(2).getResult()); //for all exists systolic - this is not true, so failed


        //four paths evaluated to exist, so ALL of them will be added to the list
        assertEquals(4, evaluationResult.getPathsThatMustExist().size());


        assertEquals(0, evaluationResult.getPathsThatMustNotExist().size());
        //this is the most specific path we can construct to the missing node, using the for_all variable context
        //or should this actually be /data[id4,1]/items[id5,1]/value[1]/magnitude, because the structure does exist?
        //that last thing might be hard to do.
        assertTrue(evaluationResult.getPathsThatMustExist().contains("/data[id2, 1]/events[id3, 2]/data[id4]/items[id5]/value/magnitude"));
        assertEquals(0, evaluationResult.getSetPathValues().size());

    }

    @Test
    public void notExistsMixed() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("not_exists.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = constructTwoBloodPressureObservationsOneEmptySystolic();

        EvaluationResult evaluationResult = ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(3, evaluationResult.getAssertionResults().size());
        for(AssertionResult assertionResult:evaluationResult.getAssertionResults()) {
            assertFalse(assertionResult.getResult());
        }

        assertEquals(0, evaluationResult.getPathsThatMustExist().size());
        assertEquals(5, evaluationResult.getPathsThatMustNotExist().size());
        assertTrue(evaluationResult.getPathsThatMustNotExist().contains("/data[id2, 1]/events[id3, 1]/data[id4, 1]/items[id5, 1]/value[1]/magnitude[1]"));
        assertTrue(evaluationResult.getPathsThatMustNotExist().contains("/data[id2, 1]/events[id3, 2]/data[id4, 1]/items[id6, 2]/value[1]/magnitude[1]"));
        assertEquals(0, evaluationResult.getSetPathValues().size());

    }

    @Test
    public void implies() throws Exception {
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("implies.adls"));
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = constructTwoBloodPressureObservationsOneEmptySystolicNoDiastolic();

        EvaluationResult evaluationResult = ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(2, evaluationResult.getAssertionResults().size());
        for(AssertionResult assertionResult:evaluationResult.getAssertionResults()) {
            assertFalse(assertionResult.getResult());
        }

        assertEquals(2, evaluationResult.getPathsThatMustExist().size());//one from the non-specific exists operator, one from the for_all that is very specific indeed
        assertTrue(evaluationResult.getPathsThatMustExist().contains("/data[id2, 1]/events[id3, 1]/data[id4]/items[id6]/value/magnitude"));
        assertTrue(evaluationResult.getPathsThatMustExist().contains("/data[id2]/events[id3]/data[id4]/items[id6]/value/magnitude"));
        assertEquals(0, evaluationResult.getPathsThatMustNotExist().size());
        assertEquals(0, evaluationResult.getSetPathValues().size());

    }


}
