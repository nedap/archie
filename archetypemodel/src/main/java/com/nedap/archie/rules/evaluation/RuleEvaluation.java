package com.nedap.archie.rules.evaluation;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rm.archetypes.Pathable;
import com.nedap.archie.rules.*;
import com.nedap.archie.rules.evaluation.evaluators.AssertionEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.BinaryOperatorEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.ConstantEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.ForAllEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.ModelReferenceEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.UnaryOperatorEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.VariableDeclarationEvaluator;
import com.nedap.archie.rules.evaluation.evaluators.VariableReferenceEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pieter.bos on 31/03/16.
 */
public class RuleEvaluation {

    private Logger logger = LoggerFactory.getLogger(RuleEvaluation.class);

    private Archetype archetype;
    private List<Evaluator> evaluators = new ArrayList<>();
    private HashMap<Class, Evaluator> classToEvaluator = new HashMap<>();

    //evaluation state
    private Pathable root;
    private VariableMap variables;
    EvaluationResult evaluationResult;
    private List<AssertionResult> assertionResults;

    Map<RuleElement, ValueList> ruleElementValues = new HashMap<>();



    public RuleEvaluation(Archetype archetype) {
        this.archetype = archetype;
        add(new VariableDeclarationEvaluator());
        add(new ConstantEvaluator());
        add(new AssertionEvaluator());
        add(new BinaryOperatorEvaluator());
        add(new UnaryOperatorEvaluator());
        add(new VariableReferenceEvaluator());
        add(new ModelReferenceEvaluator());
        add(new ForAllEvaluator());
    }

    private void add(Evaluator evaluator) {
        evaluators.add(evaluator);
        for(Object clazz: evaluator.getSupportedClasses()) {
            classToEvaluator.put((Class) clazz, evaluator);
        }
    }

    public EvaluationResult evaluate(Pathable root, List<RuleStatement> rules) {
        this.root = root;

        ruleElementValues = new HashMap<>();
        variables = new VariableMap();
        assertionResults = new ArrayList<>();
        evaluationResult = new EvaluationResult();
        for(RuleStatement rule:rules) {
            evaluate(rule);
        }
        return evaluationResult;

    }

    public ValueList evaluate(RuleElement rule) {
        Evaluator evaluator = classToEvaluator.get(rule.getClass());
        if(evaluator != null) {
            ValueList valueList = evaluator.evaluate(this, rule);
            ruleElementValueSet(rule, valueList);
            logger.info("evaluated rule: {}", valueList);
            return valueList;
        }
        throw new UnsupportedOperationException("no evaluator present for rule type " + rule.getClass().getSimpleName());
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

    private void ruleElementValueSet(RuleElement expression, ValueList values) {
        ValueList previousValue = ruleElementValues.get(expression);
        if(previousValue == null) {
            ruleElementValues.put(expression, values);
        } else {
            //in the case of a for_all, the same expression gets evaluated multiple times and we want to store all the results
            previousValue.addValues(values);
        }

    }

    /**
     * Callback: an assertion has been evaluated with the given result
     */
    public void assertionEvaluated(String tag, Expression expression, ValueList valueList) {
        AssertionResult assertionResult = new AssertionResult();
        assertionResult.setTag(tag);
        assertionResult.setAssertion(expression);

        boolean result = true;
        for(Object singleResult: valueList.getValueObjects()) {
            Boolean singleBoolean = (Boolean) singleResult;
            if(singleBoolean != null && !singleBoolean) {
                result = false;
            }
        }
        assertionResult.setResult(result);
        assertionResult.setRawResult(valueList);
        evaluationResult.addAssertionResult(assertionResult);
        if(expression instanceof BinaryOperator) {
            BinaryOperator binaryExpression = (BinaryOperator) expression;
            if (binaryExpression.getOperator() == OperatorKind.eq && binaryExpression.getLeftOperand() instanceof  ModelReference) {
                //matches the form /path/to/something = 3 + 5 * /value[id23]
                ModelReference pathToSet = (ModelReference) binaryExpression.getLeftOperand();

                setPathsToValues(pathToSet.getPath(), ruleElementValues.get(binaryExpression.getRightOperand()));
            }
        }
        //TODO: If expression matches:
        //1. path = expression: set path value to value
        //2. exists path: mark existence in form (Only for usage in implies ...)
        //3. not exists path: mark existence in form (Only for usage in implies ...)

        //before re-evaluation, reset any overridden existence from evaluation?
    }

    private void setPathsToValues(String path, ValueList value) {
        logger.info("path {} set to value {} ", path, value);
        evaluationResult.setSetPathValue(path, value);
    }

    public EvaluationResult getEvaluationResult() {
        return evaluationResult;
    }
}
