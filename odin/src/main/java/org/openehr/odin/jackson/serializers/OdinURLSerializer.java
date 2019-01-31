package org.openehr.odin.jackson.serializers;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.net.URL;

public class OdinURLSerializer extends JsonSerializer<URL> {
    @Override
    public void serialize(URL value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeRawValue(value.toString());
    }
}
