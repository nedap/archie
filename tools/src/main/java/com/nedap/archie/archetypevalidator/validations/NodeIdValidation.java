package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.flattener.ArchetypeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Validated uniqueness of node ids (VCOSU) and presence of node ids (VCOID)
 *
 * Created by pieter.bos on 31/03/2017.
 */
public class NodeIdValidation extends ValidatingVisitor {

    //for every id code, it's path
    private HashMap<String, String> nodeIds = new HashMap<>();

    @Override
    protected void beginValidation(Archetype archetype, ArchetypeRepository repository) {
        nodeIds.clear();
    }

    @Override
    public List<ValidationMessage> validate(CObject cObject) {
        List<ValidationMessage> result = new ArrayList<>();
        if(cObject.getNodeId() == null) {
            result.add(new ValidationMessage(ErrorType.VCOID, cObject.getPath()));
        }
        else if(!CPrimitiveObject.PRIMITIVE_NODE_ID_VALUE.equals(cObject.getNodeId()) && nodeIds.containsKey(cObject.getNodeId())) {
            result.add(new ValidationMessage(ErrorType.VCOSU, cObject.getPath(), "node ID " + cObject.getNodeId() + " already used in " + nodeIds.get(cObject.getNodeId())));
        }
        nodeIds.put(cObject.getNodeId(), cObject.getPath());
        return result;
    }


}
