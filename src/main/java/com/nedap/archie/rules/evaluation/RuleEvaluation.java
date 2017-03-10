package com.nedap.archie.rules.evaluation;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.creation.RMObjectCreator;
import com.nedap.archie.query.RMQueryContext;
import com.nedap.archie.rm.archetyped.Pathable;
import com.nedap.archie.rminfo.ArchieAOMInfoLookup;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.rminfo.RMAttributeInfo;
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

import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pieter.bos on 31/03/16.
 */
public class RuleEvaluation {

    private static Logger logger = LoggerFactory.getLogger(RuleEvaluation.class);

    private Archetype archetype;
    private List<Evaluator> evaluators = new ArrayList<>();
    private HashMap<Class, Evaluator> classToEvaluator = new HashMap<>();

    //evaluation state
    private Pathable root;
    private VariableMap variables;
    EvaluationResult evaluationResult;
    private List<AssertionResult> assertionResults;

    private RMQueryContext queryContext;

    private ArrayListMultimap<RuleElement, ValueList> ruleElementValues = ArrayListMultimap.create();
    private FixableAssertionsChecker fixableAssertionsChecker;

    private RMObjectCreator creator = new RMObjectCreator();

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
        this.root = (Pathable) root.clone();

        ruleElementValues = ArrayListMultimap.create();
        variables = new VariableMap();
        assertionResults = new ArrayList<>();
        evaluationResult = new EvaluationResult();
        queryContext = new RMQueryContext(this.root);

        fixableAssertionsChecker = new FixableAssertionsChecker(ruleElementValues);

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
            logger.debug("evaluated rule: {}", valueList);
            return valueList;
        }
        throw new UnsupportedOperationException("no evaluator present for rule type " + rule.getClass().getSimpleName());
    }

    public Pathable getRMRoot() {
        return root;
    }

    public VariableMap getVariableMap() {
        return variables;
    }

    private void ruleElementValueSet(RuleElement expression, ValueList values) {
        ruleElementValues.put(expression, values);
    }

    public RMQueryContext getQueryContext() {
        return queryContext;
    }

    /**
     * Callback: an assertion has been evaluated with the given result
     */
    public void assertionEvaluated(String tag, Expression expression, ValueList valueList) {
        AssertionResult assertionResult = new AssertionResult();
        assertionResult.setTag(tag);
        assertionResult.setAssertion(expression);

        boolean result = valueList.getSingleBooleanResult();
        assertionResult.setResult(result);
        assertionResult.setRawResult(valueList);
        evaluationResult.addAssertionResult(assertionResult);

        fixableAssertionsChecker.checkAssertionForFixablePatterns(assertionResult, expression, 0);

        //Fix any assertions that should be fixed before processing the next rule
        //this means we can calculate a score, then use that score in the next rule
        //otherwise this would mean several passes through the evaluator
        fixAssertions(assertionResult);


        //before re-evaluation, reset any overridden existence from evaluation?
    }

    private void fixAssertions(AssertionResult assertionResult) {
        try {
            Map<String, Value> setPathValues = assertionResult.getSetPathValues();
            for(String path:setPathValues.keySet()) {
                Value value = setPathValues.get(path);

                String pathOfParent = stripLastPathSegment(path);
                String lastPathSegment = getLastPathSegment(path);
                List<Object> parents = null;

                parents = queryContext.findList(pathOfParent);

                for(Object parent:parents) {
                    RMAttributeInfo attributeInfo = ArchieRMInfoLookup.getInstance().getAttributeInfo(parent.getClass(), lastPathSegment);
                    if(attributeInfo == null) {
                        throw new IllegalStateException("attribute " + lastPathSegment + " does not exist on type " + parent.getClass());
                    }
                    if(value.getValue() == null) {
                        creator.set(parent, lastPathSegment, Lists.newArrayList(value.getValue()));
                    } else if(attributeInfo.getType().equals(Long.class) && value.getValue().getClass().equals(Double.class)) {
                        Long convertedValue = ((Double) value.getValue()).longValue(); //TODO or should this round?
                        creator.set(parent, lastPathSegment, Lists.newArrayList(convertedValue));
                    } else if(attributeInfo.getType().equals(Double.class) && value.getValue().getClass().equals(Long.class)) {
                        Double convertedValue = ((Long) value.getValue()).doubleValue(); //TODO or should this round?
                        creator.set(parent, lastPathSegment, Lists.newArrayList(convertedValue));
                    } else {
                        creator.set(parent, lastPathSegment, Lists.newArrayList(value.getValue()));
                    }
                    //TODO: this should be something like:
                    // queryContext.updateValue(parent);
                    //but it does not work. So we do it the slow/ugly way, which does work.
                    queryContext = new RMQueryContext(root);
                }
            }
        } catch (XPathExpressionException e) {
            logger.error("error fixing assertionResult {}", assertionResult, e);
        }
    }

    /**
     * Return the path with everything except the last path segment, so /items/value becomes /items
     * @param path
     * @return
     */
    private String stripLastPathSegment(String path) {
        int lastIndex = path.lastIndexOf('/');
        if(lastIndex < 0) {
            return path;
        }
        return path.substring(0, lastIndex);
    }

    /**
     * Return the path with everything except the last path segment, so /items/value becomes /items
     * @param path
     * @return
     */
    private String getLastPathSegment(String path) {
        int lastIndex = path.lastIndexOf('/');
        if(lastIndex < 0) {
            return path;
        }
        return path.substring(lastIndex+1);
    }



    public EvaluationResult getEvaluationResult() {
        return evaluationResult;
    }
}
