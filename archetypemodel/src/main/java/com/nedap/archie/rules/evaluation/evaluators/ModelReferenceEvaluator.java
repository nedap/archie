package com.nedap.archie.rules.evaluation.evaluators;

import com.google.common.collect.Lists;
import com.nedap.archie.rules.ModelReference;
import com.nedap.archie.rules.evaluation.Evaluator;
import com.nedap.archie.rules.evaluation.RuleEvaluation;
import com.nedap.archie.rules.evaluation.Value;

import java.util.List;

/**
 * Created by pieter.bos on 04/04/16.
 */
public class ModelReferenceEvaluator implements Evaluator<ModelReference> {
    @Override
    public Value evaluate(RuleEvaluation evaluation, ModelReference statement) {
        return null;//implement me!
    }

    @Override
    public List<Class> getSupportedClasses() {
        return Lists.newArrayList(ModelReference.class);
    }
}
