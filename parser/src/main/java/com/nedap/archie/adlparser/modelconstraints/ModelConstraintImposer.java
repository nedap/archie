package com.nedap.archie.adlparser.modelconstraints;

import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;

import java.util.Stack;

/**
 * Imposes constraint on an ArchetypeModel that come from the model structure that is being described by this archetype
 *
 * Required because according to the ADL 2 specification some fields in the archetype inherit cardinality and isMultiple
 * fields from the actual model.
 *
 * As instances could be quite expensive to create, any implementing classes should be thread-safe.
 *
 *
 * Created by pieter.bos on 04/11/15.
 */
public interface ModelConstraintImposer {

    /**
     * Get the default attribute specs for a specific model, with a given typeId and attribute
     * @param typeId
     * @param attribute
     * @return
     */
    CAttribute getDefaultAttribute(String typeId, String attribute);


    default void imposeConstraints(CComplexObject rootNode) {
        if(rootNode == null) {
            return;
        }
        Stack<CObject> workList = new Stack<>();
        workList.add(rootNode);
        while(!workList.isEmpty()) {
            CObject object = workList.pop();
            for(CAttribute attribute:object.getAttributes()) {
                CAttribute defaultAttribute = getDefaultAttribute(object.getRmTypeName(), attribute.getRmAttributeName());
                if(defaultAttribute == null) {
                    //TODO: log
                    System.out.println("could not find attribute for " + object.getRmTypeName() + "." + attribute.getRmAttributeName());
                } else {
                    attribute.setMultiple(defaultAttribute.isMultiple());
                    if (attribute.getCardinality() == null) {
                        attribute.setCardinality(defaultAttribute.getCardinality());
                    }
                }
                for (CObject child : attribute.getChildren()) {
                    workList.push(child);
                }
            }
        }
    }
}
