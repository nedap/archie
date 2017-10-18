package com.nedap.archie.archetypevalidator;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeSlot;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CComplexObjectProxy;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.rminfo.ReflectionModelInfoLookup;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public abstract class ValidatingVisitor implements ArchetypeValidation {

    protected ReflectionModelInfoLookup lookup;

    public ValidatingVisitor(ReflectionModelInfoLookup lookup) {
        this.lookup = lookup;
    }

    public ValidatingVisitor() {

    }

    @Override
    public List<ValidationMessage> validate(Archetype archetype) {
        List<ValidationMessage> result = new ArrayList<>();
        beginValidation(archetype);
        ArrayDeque<CObject> workList = new ArrayDeque<>();
        workList.add(archetype.getDefinition());
        while(!workList.isEmpty()) {
            CObject cObject = workList.pop();
            result.addAll(validate(cObject));
            for(CAttribute attribute: cObject.getAttributes()) {
                result.addAll(validate(attribute));
                workList.addAll(attribute.getChildren());
            }
        }
        result.addAll(endValidation(archetype));
        return result;
    }


    /**
     * Override to get a callback when validation begins
     * @param archetype the archetype that is currently being validated
     */
    protected void beginValidation(Archetype archetype) {

    }

    /**
     * Override to get a callback when validation ends
     * @param archetype the archetype that is currently being validated
     * @return
     */
    protected List<ValidationMessage> endValidation(Archetype archetype) {
        return Collections.emptyList();
    }

    protected List<ValidationMessage> validate(CObject cObject) {
        if(cObject instanceof  CComplexObject) {
            return validate((CComplexObject) cObject);
        } else if (cObject instanceof  CPrimitiveObject) {
            return validate((CPrimitiveObject) cObject);
        } else if(cObject instanceof ArchetypeSlot){
            return validate((ArchetypeSlot) cObject);
        } else if(cObject instanceof CComplexObjectProxy){
            return validate((CComplexObjectProxy) cObject);
        }
        throw new IllegalStateException("this code should be unreachable");
    }

    /**
     * Override for validation on archetype slots
     * @param cObject
     * @return
     */
    protected List<ValidationMessage> validate(ArchetypeSlot cObject) {
        return Collections.emptyList();
    }

    /**
     * Override for validation on complex object proxy constraints
     * @param cObject
     * @return
     */
    protected List<ValidationMessage> validate(CComplexObjectProxy cObject) {
        return Collections.emptyList();
    }

    /**
     * Override for validation on complex objects
     * @param cObject
     * @return
     */
    protected List<ValidationMessage> validate(CComplexObject cObject) {
        return Collections.emptyList();
    }

    protected List<ValidationMessage> validate(CPrimitiveObject cObject) {
        return Collections.emptyList();
    }

    protected List<ValidationMessage> validate(CAttribute cAttribute) {
        return Collections.emptyList();
    }

}
