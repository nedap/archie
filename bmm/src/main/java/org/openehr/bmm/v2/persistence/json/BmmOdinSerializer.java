package org.openehr.bmm.v2.persistence.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.openehr.bmm.v2.persistence.PBmmSchema;
import org.openehr.odin.jackson.ODINMapper;

/**
 * Serializes a PBmmSchema as ODIN
 */
public class BmmOdinSerializer {
    private static ODINMapper mapper;

    public String serialize(PBmmSchema schema) throws JsonProcessingException {
        return getMapper().writeValueAsString(schema);
    }

    private ODINMapper getMapper() {
        if(mapper == null) {
            mapper = new ODINMapper();
            BmmJacksonOdinUtil.configureObjectMapper(mapper);
        }
        return mapper;
    }
}
