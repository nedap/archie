package com.nedap.archie.serializer.odin;

import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author markopi
 */
public class OdinObject {
    private final String objectType;
    private final Map<String, Object> attributes;

    public OdinObject(String objectType, Map<String, Object> attributes) {
        this.objectType = objectType;
        this.attributes = attributes;
    }

    public OdinObject(Map<String, Object> attributes) {
        this(null, attributes);
    }

    public OdinObject() {
        this(null, Maps.newLinkedHashMap());
    }

    public String getObjectType() {
        return objectType;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @SuppressWarnings("unchecked")
    public OdinObject put(String key, @Nullable Object value) {
        if (value instanceof Optional) {
            value = ((Optional<Object>) value).orElse(null);
        }
        if (value == null) return this;

        if (value instanceof Map && ((Map) value).isEmpty()) return this;
        if (value instanceof List && ((List) value).isEmpty()) return this;

        attributes.put(key, value);
        return this;
    }
}
