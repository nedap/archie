package com.nedap.archie.rules.evaluation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pieter.bos on 25/04/16.
 */
public class EvaluationResult {

    private List<AssertionResult> assertionResults = new ArrayList<>();

    private Map<String, Value> setPathValues = new LinkedHashMap<>();
    private List<String> pathsThatMustExist = new ArrayList<>();
    private List<String> pathsThatMustNotExist = new ArrayList<>();

    public EvaluationResult() {
    }

    public List<AssertionResult> getAssertionResults() {
        return assertionResults;
    }

    public void setAssertionResults(List<AssertionResult> assertionResults) {
        this.assertionResults = assertionResults;
    }

    public Map<String, Value> getSetPathValues() {
        return setPathValues;
    }

    public void setSetPathValues(Map<String, Value> setPathValues) {
        this.setPathValues = setPathValues;
    }

    public List<String> getPathsThatMustExist() {
        return pathsThatMustExist;
    }

    public void setPathsThatMustExist(List<String> pathsThatMustExist) {
        this.pathsThatMustExist = pathsThatMustExist;
    }

    public List<String> getPathsThatMustNotExist() {
        return pathsThatMustNotExist;
    }

    public void setPathsThatMustNotExist(List<String> pathsThatMustNotExist) {
        this.pathsThatMustNotExist = pathsThatMustNotExist;
    }

    protected void addAssertionResult(AssertionResult assertionResult) {
        this.assertionResults.add(assertionResult);

    }

    public void setSetPathValue(String path, ValueList values) {
        for(Value value: values.getValues()) {
            //TODO
            setPathValues.put(path, value);
        }

    }

    public void addPathThatMustExist(String path) {
        pathsThatMustExist.add(path);
    }

    public void addPathThatMustNotExist(String path) {
        pathsThatMustNotExist.add(path);
    }
}
