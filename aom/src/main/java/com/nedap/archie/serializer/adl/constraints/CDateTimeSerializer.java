package com.nedap.archie.serializer.adl.constraints;

import com.nedap.archie.aom.primitives.CDateTime;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;

/**
 * @author Marko Pipan
 */
public class CDateTimeSerializer extends CTemporalSerializer<CDateTime> {
    public CDateTimeSerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }
}
