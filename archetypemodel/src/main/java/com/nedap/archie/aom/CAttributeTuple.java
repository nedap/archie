package com.nedap.archie.aom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class CAttributeTuple extends CSecondOrder<CAttribute> {

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
    public Integer getMemberIndex(String attributeName) {
        int i =0;
        for(CAttribute member:getMembers()) {
            if(member.getRmAttributeName().equals(attributeName)) {
                return 0;
            }
            i++;
        }
        return null;
    }
}
