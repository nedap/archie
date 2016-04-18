package com.nedap.archie.rules.evaluation.evaluators;

import com.google.common.collect.Lists;
import com.nedap.archie.query.APathQuery;
import com.nedap.archie.query.RMObjectWithPath;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.rules.ModelReference;
import com.nedap.archie.rules.evaluation.Evaluator;
import com.nedap.archie.rules.evaluation.RuleEvaluation;
import com.nedap.archie.rules.evaluation.Value;
import com.nedap.archie.rules.evaluation.ValueList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pieter.bos on 04/04/16.
 */
public class ModelReferenceEvaluator implements Evaluator<ModelReference> {
    @Override
    public ValueList evaluate(RuleEvaluation evaluation, ModelReference statement) {
        List<RMObjectWithPath> rmObjectsWithPath = new APathQuery(statement.getPath()).findList(ArchieRMInfoLookup.getInstance(), evaluation.getRMRoot());
        List<Value> values = rmObjectsWithPath.stream().map(
                rmObjectWithPath ->
                        new Value(rmObjectWithPath.getObject(), Lists.newArrayList(rmObjectWithPath.getPath())))
            .collect(Collectors.toList());

        return new ValueList(values);
    }

    @Override
    public List<Class> getSupportedClasses() {
        return Lists.newArrayList(ModelReference.class);
    }
}
