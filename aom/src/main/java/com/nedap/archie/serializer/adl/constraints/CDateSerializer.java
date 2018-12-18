package com.nedap.archie.serializer.adl.constraints;


import com.nedap.archie.aom.primitives.CDate;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;

/**
 * @author Marko Pipan
 */
public class CDateSerializer extends CTemporalSerializer<CDate> {
    public CDateSerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }

}
