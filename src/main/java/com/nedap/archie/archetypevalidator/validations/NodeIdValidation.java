package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.rminfo.ModelInfoLookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Validated uniqueness of node ids (VCOSU) and presence of node ids (VCOID)
 *
 * Created by pieter.bos on 31/03/2017.
 */
public class NodeIdValidation extends ValidatingVisitor {

    //for every id code, it's path
    HashMap<String, String> nodeIds = new HashMap<>();

    @Override
    protected void beginValidation(Archetype archetype) {
        nodeIds.clear();
    }

    @Override
    public List<ValidationMessage> validate(CObject cObject) {
        List<ValidationMessage> result = new ArrayList<>();
        if(cObject.getNodeId() == null) {
            result.add(new ValidationMessage(ErrorType.VCOID, cObject.getPath()));
        }
        else if(!"Primitive_node_id".equals(cObject.getNodeId()) && nodeIds.containsKey(cObject.getNodeId())) {
            result.add(new ValidationMessage(ErrorType.VCOSU, cObject.getPath(), "node ID " + cObject.getNodeId() + " already used in " + nodeIds.get(cObject.getNodeId())));
        }
        nodeIds.put(cObject.getNodeId(), cObject.getPath());
        return result;
    }


}
