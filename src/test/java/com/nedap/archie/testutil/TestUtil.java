package com.nedap.archie.testutil;

import com.google.common.collect.Lists;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.creation.RMObjectCreator;
import com.nedap.archie.rm.RMObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 06/04/16.
 */
public class TestUtil {

    RMObjectCreator creator = new RMObjectCreator();
    /**
     * Creates an empty RM Object, fully nested, one object per CObject found.
     * For those familiar to the old java libs: this is a simple skeleton generator.
     *
     * In a real system you would want user input/a parameter map. Plus just creating every CObject will
     * introduce cardinality/multiplicity problems in many case.
     *
     * @param object
     * @return
     */
    public RMObject constructEmptyRMObject(CObject object) {
        RMObject result = creator.create(object);
        for(CAttribute attribute: object.getAttributes()) {
            List<Object> children = new ArrayList<>();
            for(CObject childConstraint:attribute.getChildren()) {
                if(childConstraint instanceof CComplexObject) {
                    RMObject childObject = constructEmptyRMObject(childConstraint);
                    children.add(childObject);
//                    if(childConstraint.getRmTypeName().equals("EVENT")) {
//                        childObject = constructEmptyRMObject(childConstraint);
//                        children.add(childObject);
//                    }
                }
            }
            if(!children.isEmpty()) {
                if(attribute.isMultiple()) {
                    creator.set(result, attribute.getRmAttributeName(), children);
                } else if(!children.isEmpty()){
                    //set the first possible result in case of multiple children for a single valued value
                    creator.set(result, attribute.getRmAttributeName(), Lists.newArrayList(children.get(0)));
                }
            }
        }
        return result;
    }
}
