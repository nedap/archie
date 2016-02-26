package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Primitive object. Parameterized with a Constraint type and AssumedAndDefault value type, to be able to override
 * the methods in subclasses easily
 *
 * Created by pieter.bos on 15/10/15.
 */
public class CPrimitiveObject<Constraint, ValueType> extends CDefinedObject<ValueType> {

    public static final String PRIMITIVE_NODE_ID_VALUE = "Primitive_node_id";

    private ValueType assumedValue;
    private Boolean enumeratedTypeConstraint;
    private List<Constraint> constraints = new ArrayList<>();

    public ValueType getAssumedValue() {
        return assumedValue;
    }

    public void setAssumedValue(ValueType assumedValue) {
        this.assumedValue = assumedValue;
    }

    public Boolean getEnumeratedTypeConstraint() {
        return enumeratedTypeConstraint;
    }

    public void setEnumeratedTypeConstraint(Boolean enumeratedTypeConstraint) {
        this.enumeratedTypeConstraint = enumeratedTypeConstraint;
    }

    public List<Constraint> getConstraint() {
        return constraints;
    }

    public void setConstraint(List<Constraint> constraint) {
        this.constraints = constraint;
    }

    public void addConstraint(Constraint constraint) {
        this.constraints.add(constraint);
    }

    public String getNodeId() {
        return PRIMITIVE_NODE_ID_VALUE;
    }

    public void setNodeId(String nodeId) {
        if(!nodeId.equals(PRIMITIVE_NODE_ID_VALUE)) {
            throw new UnsupportedOperationException("Cannot set node id on a CPrimitiveObject");
        }
    }

    /**
     * True if the given value is a valid value for this constraint
     * Must be overridden in classes where the AssumedAndDefaultValue is not the actual value.
     * For example when it is an interval or pattern
     *
     * @param value
     * @return
     */
    public boolean isValidValue(ValueType value) {
        if(getConstraint().isEmpty()) {
            return true;
        }
        for(Constraint constraint:getConstraint()) {
            if(Objects.equals(constraint, value)) {
                return true;
            }
        }
        return false;
    }

}

