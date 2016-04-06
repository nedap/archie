package com.nedap.archie.rules.evaluation;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rm.archetypes.Pathable;
import com.nedap.archie.rules.*;
import com.nedap.archie.rules.evaluation.evaluators.AssertionEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.BinaryOperatorEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.ConstantEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.ModelReferenceEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.UnaryOperatorEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.VariableDeclarationEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.VariableReferenceEvaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pieter.bos on 31/03/16.
 */
public class RuleEvaluation {

    private Archetype archetype;
    private List<Evaluator> evaluators = new ArrayList<>();
    private HashMap<Class, Evaluator> classToEvaluator = new HashMap<>();

    //evaluation state
    private Pathable root;
    private VariableMap variables;
    private List<AssertionResult> assertionResults;



    public RuleEvaluation(Archetype archetype) {
        this.archetype = archetype;
        add(new VariableDeclarationEvaluator());
        add(new ConstantEvaluator());
        add(new AssertionEvaluator());
        add(new BinaryOperatorEvaluator());
        add(new UnaryOperatorEvaluator());
        add(new VariableReferenceEvaluator());
        add(new ModelReferenceEvaluator());
    }

    private void add(Evaluator evaluator) {
        evaluators.add(evaluator);
        for(Object clazz: evaluator.getSupportedClasses()) {
            classToEvaluator.put((Class) clazz, evaluator);
        }
    }

    public void evaluate(Pathable root, List<RuleStatement> rules) {
        this.root = root;

        variables = new VariableMap();
        assertionResults = new ArrayList<>();
        for(RuleStatement rule:rules) {
            evaluate(rule);
        }
    }

    public Value evaluate(RuleElement rule) {
        Evaluator evaluator = classToEvaluator.get(rule.getClass());
        if(evaluator != null) {
            Value value = evaluator.evaluate(this, rule);
            System.out.println(value);
            return value;
        }
        return null;
//        if(rule instanceof Assertion) {
//
//        } else if (rule instanceof VariableDeclaration) {
//
//        }
    }

    public Pathable getRMRoot() {
        return root;
    }

    public VariableMap getVariableMap() {
        return variables;
    }

    /**
     * Callback: an assertion has been evaluated with the given result
     */
    public void assertionEvaluated(String tag, Expression expression, Value value) {
        AssertionResult assertionResult = new AssertionResult();
        assertionResult.setTag(tag);
        assertionResult.setAssertion(expression);

        boolean result = true;
        for(Object singleResult:value.getValues()) {
            Boolean singleBoolean = (Boolean) singleResult;
            if(!singleBoolean) {
                result = false;
            }
        }
        assertionResult.setResult(result);
        assertionResults.add(assertionResult);
        //TODO: If expression matches:
        //1. path = expression: set path value to value
        //2. exists path: mark existence in form (Only for usage in implies ...)
        //3. not exists path: mark existence in form (Only for usage in implies ...)

        //before re-evaluation, reset any overridden existence from evaluation?
    }

    public List<AssertionResult> getAssertionResults() {
        return assertionResults;
    }
}
