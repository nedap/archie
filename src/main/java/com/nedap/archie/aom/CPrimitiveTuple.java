package com.nedap.archie.aom;

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
}
