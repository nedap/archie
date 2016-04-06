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

    RMObjectCreator creator;

    @Test
    public void simpleArithmetic() throws Exception {
        parser = new ADLParser();
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
        parser = new ADLParser();
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("modelreferences.adls"));
        creator = new RMObjectCreator(ArchieRMInfoLookup.getInstance());
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) constructEmptyRMObject(archetype.getDefinition());

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
        parser = new ADLParser();
        archetype = parser.parse(ParsedRulesEvaluationTest.class.getResourceAsStream("matches.adls"));
        creator = new RMObjectCreator(ArchieRMInfoLookup.getInstance());
        RuleEvaluation ruleEvaluation = new RuleEvaluation(archetype);

        Pathable root = (Pathable) constructEmptyRMObject(archetype.getDefinition());

        DvQuantity quantity = (DvQuantity) root.itemAtPath("/data[id2]/events[id3]/data[id4]/items[id5]/value[id13]");
        quantity.setMagnitude(40d);

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(false, ruleEvaluation.getVariableMap().get("extended_validity").getValue());

        quantity.setMagnitude(20d);

        ruleEvaluation.evaluate(root, archetype.getRules().getRules());
        assertEquals(true, ruleEvaluation.getVariableMap().get("extended_validity").getValue());


    }

    /**
     * Creates an empty RM Object, fully nested, one object per CObject found.
     * For those familiar to the old java libs: this is a simple skeleton generator.
     *
     * Perhaps this should be moved to a utility class. However, it is more of an example:
     * in a real system you would want user input/a parameter map. Plus just creating every CObject will
     * introduce cardinality/multiplicity problems in many case.
     *
     * @param object
     * @return
     */
    private RMObject constructEmptyRMObject(CObject object) {
        RMObject result = creator.create(object);
        for(CAttribute attribute: object.getAttributes()) {
            List<Object> children = new ArrayList<>();
            for(CObject childConstraint:attribute.getChildren()) {
                if(childConstraint instanceof CComplexObject) {
                    RMObject childObject = constructEmptyRMObject(childConstraint);
                    children.add(childObject);
//                    if(childConstraint.getRmTypeName().equals("EVENT")) {
//                        childObject = constructEmptyRMObject(childConstraint);
//                        children.add(childObject);
//                    }
                }
            }
            if(!children.isEmpty()) {
                creator.set(result, attribute.getRmAttributeName(), children);
            }
        }
        return result;
    }
}
