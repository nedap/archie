package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
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

    /**
     * Given a hashmap of attribute names mapping to its values, check the validity of this set of values
     * return true if and only if the given values are valid.
     */
    public boolean isValid(HashMap<String, Object> values) {

        for(CAttribute attribute:getMembers()) {
            if(!values.containsKey(attribute.getRmAttributeName())) {
                return false;
            }
        }

        for(CPrimitiveTuple tuple:tuples) {
            if (isValid(tuple, values)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(CPrimitiveTuple tuple, HashMap<String, Object> values) {

        for(String attributeName:values.keySet()) {
            int index = getMemberIndex(attributeName);
            if(index >= 0) { //if not found, that is an extra attribute and that's fine
                Object value = values.get(attributeName);
                if(!tuple.getMembers().get(index).isValidValue(value)) {
                    return false;
                }
            }
        }
        return true;

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
