package com.nedap.archie.aom;

import com.nedap.archie.rminfo.ModelInfoLookup;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
@XmlType(name="C_PRIMITIVE_TUPLE")
//TODO: because of how jaxb works, this might need work for members
public class CPrimitiveTuple extends CSecondOrder<CPrimitiveObject> {

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        boolean first = true;
        for(CPrimitiveObject object:getMembers()) {
            if(!first) {
                result.append(", ");
            }
            first = false;
            result.append(object.toString());
        }
        result.append("]");
        return result.toString();
    }

    public boolean cConformsTo(CPrimitiveTuple other, ModelInfoLookup lookup) {
        return this.getMembers().size() == other.getMembers().size() && allTupleMembersConform(other, lookup);
    }

    private boolean allTupleMembersConform(CPrimitiveTuple other, ModelInfoLookup lookup) {
        for(int i = 0; i < getMembers().size(); i++){
            CPrimitiveObject member = getMember(i);
            CPrimitiveObject otherMember = other.getMember(i);
            if(!member.getClass().equals(otherMember.getClass()) || !member.cConformsTo(otherMember, lookup)) {
                return false;
            }
        }
        return true;
    }

    public boolean cCongruentTo(CPrimitiveTuple primitiveTuple) {
        return true;
    }
}
