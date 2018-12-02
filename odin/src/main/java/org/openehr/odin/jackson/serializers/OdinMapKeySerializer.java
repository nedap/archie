package org.openehr.odin.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * ODIN map keys serialize differently from propery field names.
 * Jackson has a mechanism that allows you to set a key serializer. So use this class for that, it adds the key in the
 * ["key_name"] format without affecting object property names
 *
 */
public class OdinMapKeySerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeFieldName("[\"" + value + "\"]");
    }
}

