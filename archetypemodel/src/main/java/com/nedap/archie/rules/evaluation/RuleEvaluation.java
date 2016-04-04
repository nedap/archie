package com.nedap.archie.rules.evaluation;

import com.google.common.collect.ImmutableRangeMap;
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

    private SymbolMap variables;

    private List<Evaluator> evaluators = new ArrayList<>();

    private HashMap<Class, Evaluator> classToEvaluator = new HashMap<>();

    public RuleEvaluation() {
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

    public void evaluation(List<RuleStatement> rules) {
        variables = new SymbolMap();
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

    public SymbolMap getVariableMap() {
        return variables;
    }

}
