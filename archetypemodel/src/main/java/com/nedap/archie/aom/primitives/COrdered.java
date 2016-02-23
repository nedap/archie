package com.nedap.archie.aom.primitives;

import com.nedap.archie.aom.CDefinedObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.base.Interval;

import java.util.Objects;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class COrdered<T> extends CPrimitiveObject<Interval<T>, T> {

    @Override
    public boolean isValidValue(T value) {
        if(getConstraint().isEmpty()) {
            return true;
        }
        for(Interval<T> constraint:getConstraint()) {
            if(constraint.has(value)) {
                return true;
            }
        }
        return false;
    }
}
