package org.openehr.odin.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

/**
 * @since 2.8
 */
public class JacksonODINParseException extends JsonParseException
{
    private static final long serialVersionUID = 1L;

    public JacksonODINParseException(JsonParser p, String msg, Exception e) {
        super(p, msg, e);
    }
}
