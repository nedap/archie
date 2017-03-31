package com.nedap.archie.archetypevalidator;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeSlot;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CComplexObjectProxy;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.rminfo.ModelInfoLookup;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public abstract class ValidatingVisitor implements ArchetypeValidation {

    protected ModelInfoLookup lookup;

    public ValidatingVisitor(ModelInfoLookup lookup) {
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


    protected void beginValidation(Archetype archetype) {

    }

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

    protected List<ValidationMessage> validate(ArchetypeSlot cObject) {
        return Collections.emptyList();
    }

    protected List<ValidationMessage> validate(CComplexObjectProxy cObject) {
        return Collections.emptyList();
    }

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
