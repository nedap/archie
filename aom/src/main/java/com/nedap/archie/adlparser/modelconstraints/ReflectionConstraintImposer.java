package com.nedap.archie.adlparser.modelconstraints;

import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.base.Cardinality;
import com.nedap.archie.base.MultiplicityInterval;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.RMAttributeInfo;

/**
 * ModelConstraintImposer that checks the constraint with java-reflection. javax.annotation.NonNull is implemented
 * as being NonNull. Other attributes are assumed to be non-null. Collection attributes are assumed to be 0..*
 *
 * Fully thread-safe, inexpensive to create as long as you cached your ModelInfoLookup
 *
 * Created by pieter.bos on 04/11/15.
 */
public class ReflectionConstraintImposer implements ModelConstraintImposer {

    /** Contains complex object structure of the specified model. Attributes NEVER will have children. Sorry bout that :)*/

    private ModelInfoLookup lookup;

    public ReflectionConstraintImposer(ModelInfoLookup classLookup) {
        this.lookup = classLookup;
    }

    private CAttribute createCAttribute(RMAttributeInfo attributeInfo) {
        CAttribute attribute = new CAttribute();

        attribute.setMultiple(false);
        attribute.setRmAttributeName(attributeInfo.getRmName());

        if(attributeInfo.isNullable()) {
            attribute.setExistence(new MultiplicityInterval(0, 1));
        } else {
            attribute.setExistence(new MultiplicityInterval(1, 1));
        }

        if(attributeInfo.isMultipleValued()) {
            attribute.setCardinality(Cardinality.unbounded());
            attribute.setMultiple(true);
        } else {
            //only for container attributes (list, set, etc)
            attribute.setCardinality(null);
            attribute.setMultiple(false);
        }
        return attribute;
    }

    @Override
    public CAttribute getDefaultAttribute(String typeId, String attribute) {
        RMAttributeInfo info = lookup.getAttributeInfo(typeId, attribute);
        if(info == null) {
            return null;
        }
        return createCAttribute(info);
    }


}
