package com.nedap.archie.aom;

import com.nedap.archie.rminfo.ModelInfoLookup;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Primitive object. Parameterized with a Constraint type and AssumedAndDefault value type, to be able to override
 * the methods in subclasses easily
 *
 * Created by pieter.bos on 15/10/15.
 */
@XmlType(name="C_PRIMITIVE_OBJECT")
//TODO: we might have to make this with abstract methods and move the attributes to the
//lower classes to get this to work with JAXB, or create a custom XML adapter
public abstract class CPrimitiveObject<Constraint, ValueType> extends CDefinedObject<ValueType> {

    public static final String PRIMITIVE_NODE_ID_VALUE = "Primitive_node_id";

    private Boolean enumeratedTypeConstraint;

    public abstract ValueType getAssumedValue();

    public abstract void setAssumedValue(ValueType assumedValue);

    public abstract List<Constraint> getConstraint();

    public abstract void setConstraint(List<Constraint> constraint);

    public abstract void addConstraint(Constraint constraint);

    public Boolean getEnumeratedTypeConstraint() {
        return enumeratedTypeConstraint;
    }

    public void setEnumeratedTypeConstraint(Boolean enumeratedTypeConstraint) {
        this.enumeratedTypeConstraint = enumeratedTypeConstraint;
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

    /**
     * True if the given value is a valid value for this constraint
     * first Converts the value to a checkable value using the given ModelInfoLookup
     * For example when it is an interval or pattern
     *
     * @param value
     * @return
     */
    public boolean isValidValue(ModelInfoLookup lookup, Object value) {
        Object convertedValue = lookup.convertToConstraintObject(value, this);
        return isValidValue((ValueType) convertedValue);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{");
        boolean first = true;
        for(Constraint constraint:getConstraint()) {
            if(!first) {
                result.append(", ");
            }
            first = false;
            if(constraint instanceof String) {
                result.append('"');
                result.append(((String) constraint).replace("\"", "\\\""));
                result.append('"');
            } else {
                result.append(constraint.toString());
            }
        }
        result.append("}");
        return result.toString();
    }

}

