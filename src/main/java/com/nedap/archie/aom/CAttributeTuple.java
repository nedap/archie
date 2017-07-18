package com.nedap.archie.aom;

import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.RMAttributeInfo;

import javax.xml.bind.annotation.XmlType;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TODO: noone created an XML model for this.
 * Created by pieter.bos on 15/10/15.
 */
@XmlType(name="C_ATTRIBUTE_TUPLE", propOrder={
        "tuples"
})
public class CAttributeTuple extends CSecondOrder<CAttribute> {

    /**
     * Each CPrimitiveTuple is a constraint for all attributes of this tuple, each containing a constraint for every attribute.
     * If at least one of these tuples is valid for the given RM values, the list of values is valid.
     *
     * The members List of CSecondOrder contains the attribute names.
     */

    private List<CPrimitiveTuple> tuples = new ArrayList<>();

    public List<CPrimitiveTuple> getTuples() {
        return tuples;
    }

    public void setTuples(List<CPrimitiveTuple> tuples) {
        this.tuples = tuples;
    }

    public void addTuple(CPrimitiveTuple tuple) {
        this.tuples.add(tuple);
    }

    public boolean isValid(HashMap<String, Object> values) {
        return isValid(ArchieRMInfoLookup.getInstance(), values);
    }

    public boolean isValid(Object rmObjectValue) {
        return isValid(ArchieRMInfoLookup.getInstance(), rmObjectValue);
    }

    /**
     * Given a hashmap of attribute names mapping to its values, check the validity of this set of values
     * return true if and only if the given values are valid.
     */
    public boolean isValid(ModelInfoLookup lookup, HashMap<String, Object> values) {
        for(CAttribute attribute:getMembers()) {
            if(!values.containsKey(attribute.getRmAttributeName())) {
                return false;
            }
        }

        for(CPrimitiveTuple tuple:tuples) {
            if (isValid(lookup, tuple, values)) {
                return true;
            }
        }
        return false;
    }


    private boolean isValid(ModelInfoLookup lookup, CPrimitiveTuple tuple, HashMap<String, Object> values) {

        int index = 0;
        for(CAttribute attribute:getMembers()) {
            String attributeName = attribute.getRmAttributeName();

            CPrimitiveObject cPrimitiveObject = tuple.getMembers().get(index);
            Object value = values.get(attributeName);
            if(value == null) {
                return false;
                //alternatively, look at occurrences or parent attribute existence?
                //not sure if we should in a tuple - a constrained value that is null is generally an error
            }
            if(!cPrimitiveObject.isValidValue(lookup, value)) {
                return false;
            }
            index++;
        }
        return true;
    }

    /**
     * Given a reference model object, check if it is valid
     * return true if and only if the given values are valid.
     */
    public boolean isValid(ModelInfoLookup lookup, Object value) {

        HashMap<String, Object> members = new HashMap();
        for(CAttribute attribute:getMembers()) {
            RMAttributeInfo attributeInfo = lookup.getAttributeInfo(value.getClass(), attribute.getRmAttributeName());
            try {
                if (attributeInfo != null && attributeInfo.getGetMethod() != null) {
                    members.put(attribute.getRmAttributeName(), attributeInfo.getGetMethod().invoke(value));
                } else {
                    //warn? throw exception?
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return isValid(lookup, members);
    }

    public CAttribute getMember(String attributeName) {
        for(CAttribute member:getMembers()) {
            if(member.getRmAttributeName().equals(attributeName)) {
                return member;
            }
        }
        return null;
    }

    /**
     * Get the index of the tuple with the given named attribute. For example, "[magnitude, units] matches {
     [{|0.0..1000.0|}, {"cm"}]," .getMemberIndex("magnitude") returns 0. getMemberIndex("units") returns 1
     * @param attributeName
     * @return
     */
    public int getMemberIndex(String attributeName) {
        int i =0;
        for(CAttribute member:getMembers()) {
            if(member.getRmAttributeName().equals(attributeName)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        boolean first = true;
        for(CAttribute member:getMembers()) {
            if(!first) {
                result.append(", ");
            }
            first = false;
            result.append(member.getRmAttributeName());
        }
        result.append("] ∈ {\n");
        first = true;
        for(CPrimitiveTuple tuple:tuples) {
            if(!first) {
                result.append(",\n");
            }
            first = false;
            result.append("\t");
            result.append(tuple.toString());
        }
        result.append("\n}");

        return result.toString();

    }

}
