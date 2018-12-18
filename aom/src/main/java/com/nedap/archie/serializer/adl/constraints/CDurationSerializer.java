package com.nedap.archie.serializer.adl.constraints;


import com.nedap.archie.aom.primitives.CDuration;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;

/**
 * @author Marko Pipan
 */
public class CDurationSerializer extends CTemporalSerializer<CDuration> {
    public CDurationSerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }
}
