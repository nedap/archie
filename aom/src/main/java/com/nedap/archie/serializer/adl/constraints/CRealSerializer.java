package com.nedap.archie.serializer.adl.constraints;


import com.nedap.archie.aom.primitives.CReal;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;

/**
 * @author Marko Pipan
 */
public class CRealSerializer extends COrderedSerializer<CReal> {
    public CRealSerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }
}
