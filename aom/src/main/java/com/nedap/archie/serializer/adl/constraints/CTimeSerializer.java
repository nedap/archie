package com.nedap.archie.serializer.adl.constraints;


import com.nedap.archie.aom.primitives.CTime;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;

/**
 * @author Marko Pipan
 */
public class CTimeSerializer extends CTemporalSerializer<CTime> {
    public CTimeSerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }
}
