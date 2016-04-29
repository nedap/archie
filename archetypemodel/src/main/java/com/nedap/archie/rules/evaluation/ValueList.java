package com.nedap.archie.rules.evaluation;

import com.nedap.archie.rules.PrimitiveType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A list of values, as evaluated by the rule evaluation.
 *
 * Every value has both a value and a set of paths used in calculating that value. See Value
 * Created by pieter.bos on 31/03/16.
 */
public class ValueList {
    private PrimitiveType type;
    private List<Value> values = new ArrayList<>();

    public ValueList() {

    }

    public ValueList(List<Value> values) {
        setValues(values);
        if(!values.isEmpty()) {
            this.type = PrimitiveType.fromJavaType(values.get(0).getValue().getClass());
        } else{
            this.type = PrimitiveType.Unknown;
        }
    }

    /*
     * Construct a value list of a single value, that does not have a path.
     */
    public ValueList(Object value) {
        this(value, Collections.EMPTY_LIST);
    }

    /**
     * Construct a value list of a single object, with its paths
     * @param value
     * @param paths
     */
    public ValueList(Object value, List<String> paths){
        if(value == null) {
            this.type = null;
        } else {
            addValue(value, paths);

            this.type = PrimitiveType.fromJavaType(value.getClass());
        }
    }

    public PrimitiveType getType() {
        return type;
    }

    public void setType(PrimitiveType type) {
        this.type = type;
    }

    public List<Value> getValues() {
        return values;
    }

    public List<Object> getValueObjects() {
        List<Object> result = new ArrayList();
        for(Value value:values) {
            result.add(value.getValue());
        }
        return result;
    }

    public void setValues(List<Value> values) {
        this.values = new ArrayList<>(values.size());
        for(Value o:values) {
            addValue(o);
        }
    }

    public void addValue(Object value,  List<String> paths) {
        values.add(new Value(value, paths));
    }

    public void addValue(Value value){
        values.add(value);
    }

    @Override
    public String toString() {
        return type + ": " + values;
    }

    public int size() {
        return values.size();
    }

    public Object getObject(int i) {
        return values.get(i).getValue();
    }

    public List<String> getPaths(int i) {
        return values.get(i).getPaths();
    }

    public Value get(int i) {
        return values.get(i);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }
}
