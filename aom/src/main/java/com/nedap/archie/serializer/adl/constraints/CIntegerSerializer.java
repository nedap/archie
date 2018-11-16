package com.nedap.archie.serializer.adl.constraints;


import com.nedap.archie.aom.primitives.CInteger;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;

/**
 * @author Marko Pipan
 */
public class CIntegerSerializer extends COrderedSerializer<CInteger> {
    public CIntegerSerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }


}
