package com.nedap.archie.rules.evaluation;

import com.nedap.archie.rules.PrimitiveType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 31/03/16.
 */
public class ValueList {
    private PrimitiveType type;
    private List<Value> values = new ArrayList<>();

    public ValueList() {

        //
//        [3,5,8] + 1 = [4,6,9]
//        [3,5,8] > [1,2,3] = [true, true, true]
//        [3,5,8] > [12,2,3] = [false, true, true]
//          [3,5,8] > [1,2]= ERROR
        //sum(/blabla/jfkldsjfds / 3), mean, avg,
        // /part1/part2 implies exist /part1/part3
        // /looptest/is_gelopen matches {'ja'} implies exists /looptest/other_questions
        /**
            testformulier:
                looptest:
                    is er gelopen? nee
                    other_questions: does not exist/hidden
                        ...
                looptest:
                    is er gelopen? ja
                    other_questions:
                        wanneer?
                        hoever? 23 km

         */
    }

    public ValueList(List<Object> values) {
        setValues(values);
        if(!values.isEmpty()) {
            this.type = PrimitiveType.fromJavaType(values.get(0).getClass());
        } else{
            this.type = PrimitiveType.Unknown;
        }
    }

    public ValueList(Object value) {
        if(value == null) {
            this.type = null;
        } else {
            addValue(value);
            this.type = PrimitiveType.fromJavaType(value.getClass());
        }
    }

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

    public List<Object> getValues() {
        List<Object> result = new ArrayList();
        for(Value value:values) {
            result.add(value.getValue());
        }
        return result;
    }

    public void setValues(List<Object> values) {
        this.values = new ArrayList<>(values.size());
        for(Object o:values) {
            addValue(o);
        }
    }

    public void addValue(Object value,  List<String> paths) {
        values.add(new Value(value, paths));
    }

    public void addValue(Object value){
        values.add(new Value(value));
    }

    @Override
    public String toString() {
        return type + ": " + values;
    }

    public int size() {
        return values.size();
    }

    public Object get(int i) {
        return values.get(i).getValue();
    }
}
