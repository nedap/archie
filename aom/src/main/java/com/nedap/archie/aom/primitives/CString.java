package com.nedap.archie.aom.primitives;

import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.rminfo.ModelInfoLookup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by pieter.bos on 15/10/15.
 */
@XmlType(name="C_STRING")
@XmlAccessorType(XmlAccessType.FIELD)
public class CString extends CPrimitiveObject<String, String> {

    @XmlElement(name="assumed_value")
    private String assumedValue;
    private List<String> constraint = new ArrayList<>();

    @Override
    public String getAssumedValue() {
        return assumedValue;
    }

    @Override
    public void setAssumedValue(String assumedValue) {
        this.assumedValue = assumedValue;
    }

    @Override
    public List<String> getConstraint() {
        return constraint;
    }

    @Override
    public void setConstraint(List<String> constraint) {
        this.constraint = constraint;
    }

    @Override
    public void addConstraint(String constraint) {
        this.constraint.add(constraint);
    }

    public boolean isValidValue(String value) {
        if(getConstraint().isEmpty()) {
            return true;
        }
        for(String constraint:getConstraint()) {
            if(constraint.length() > 1 &&
                    ((constraint.startsWith("/") && constraint.endsWith("/")) ||
                            (constraint.startsWith("^") && constraint.endsWith("^")))) {
                //regexp. Strip first and last character and match. If you want to input
                //data starting and ending with '/', you cannot in the AOM, although ADL lets you express if just fine.
                //perhaps we should make the constraint object something more expressive than a String?
                if(value.matches(constraint.substring(1).substring(0, constraint.length()-2))) {
                    return true;
                }
            } else {
                //TODO: does case matter here?
                if(Objects.equals(value, constraint)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean cConformsTo(CObject other, ModelInfoLookup lookup) {
        if(!super.cConformsTo(other, lookup)) {
            return false;
        }
        //now guaranteed to be the same class

        CString otherString = (CString) other;
        if(otherString.constraint.isEmpty()) {
            return true;
        }

        if(!(constraint.size() < otherString.constraint.size())) {
            return false;
        }

        for(String constraint:constraint) {
            if(!otherString.constraint.contains(constraint)) {
                return false;
            }
        }
        return true;
    }
}
