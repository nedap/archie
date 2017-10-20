package com.nedap.archie.serializer.odin;

import com.google.common.collect.Lists;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * @author markopi
 */
public class OdinSerializer {
    private final OdinStringBuilder builder;
    private final Function<Object, Object> objectMapper;

    public OdinSerializer(OdinStringBuilder builder, Function<Object, Object> odinObjectMapper) {
        this.builder = builder;
        this.objectMapper = odinObjectMapper.compose(this::checkNotNull);
    }

    public static String serialize(Object structure, Function<Object, Object> objectMapper) {
        OdinStringBuilder builder = new OdinStringBuilder();
        new OdinSerializer(builder, objectMapper).serializeDirect(structure);
        return builder.toString().trim();
    }

    public static String serialize(Object structure) {
        return serialize(structure, Function.identity());
    }

    @SuppressWarnings("unchecked")
    private static boolean isSingleLine(Object structure) {
        if (structure instanceof OdinObject) {
            return ((OdinObject) structure).getAttributes().isEmpty();
        }
        if (structure instanceof Map) {
            return ((Map) structure).isEmpty();
        }
        if (structure instanceof List) {
            return ((List) structure).stream().allMatch(OdinSerializer::isSingleLine);
        }
        if (structure instanceof Set) {
            return ((Set) structure).stream().allMatch(OdinSerializer::isSingleLine);
        }

        return true;
    }

    private Object checkNotNull(Object value) {
        if (value == null) {
            throw new AssertionError("Odin value must not be null");
        }
        return value;
    }

    public void serializeDirect(Object structure) {
        serializeAny(objectMapper.apply(structure));
    }

    @SuppressWarnings("unchecked")
    private void serializeAny(Object structure) {
        if (structure instanceof OdinObject) {
            serializeOdinObject((OdinObject) structure);
        } else if (structure instanceof Map) {
            serializeMap((Map) structure);
        } else if (structure instanceof List) {
            serializeList((List) structure);
        } else if (structure instanceof Set) {
            serializeSet((Set) structure);
        } else if (structure instanceof String) {
            builder.text((String) structure);
        } else if (structure instanceof Character) {
            builder.character((Character) structure);
        } else if (structure instanceof Number) {
            builder.number((Number) structure);
        } else if (structure instanceof Boolean) {
            builder.bool((boolean) structure);
        } else if (structure instanceof URI) {
            builder.uri((URI) structure);
        } else {
            builder.append(structure.toString());
        }

    }

    private void serializeList(List<Object> structure) {
        final int size = structure.size();
        for (int i = 0; i < size; i++) {
            Object item = structure.get(i);
            serializeAny(item);
            if (i<size-1) {
                builder.append(", ");
            }

        }
        if (size==0) {
            builder.append("...");
        } else if (size==1) {
            builder.append(", ...");
        }
    }

    private void serializeSet(Set<Object> structure) {
        serializeList(Lists.newArrayList(structure));
    }

    private void serializeOdinObject(OdinObject structure) {
        if (structure.getAttributes().isEmpty()) return;

        structure.getAttributes().forEach((key, value) -> {
            if (value == null) return;
            builder.tryNewLine().append(key).append(" = ");
            serializeEmbeddedValue(value);
        });
        builder.newline();
    }

    private void serializeEmbeddedValue(Object value) {
        value = objectMapper.apply(value);
        if (value instanceof OdinObject) {
            OdinObject oo = (OdinObject) value;
            if (oo.getObjectType() != null) {
                builder.append("(").append(oo.getObjectType()).append(") ");
            }
        }
        builder.append("<").indent();
        if (isSingleLine(value)) {
            serializeAny(value);
            builder.unindent().append(">");
        } else {
            builder.newline();
            serializeAny(value);
            builder.unindent().tryNewLine().append(">");
        }
    }

    private void serializeMap(Map<Object, Object> structure) {
        if (structure.isEmpty()) return;

        structure.forEach((key, value) -> {
            builder.tryNewLine().append("[");
            serializeDirect(key);
            builder.append("] = ");
            serializeEmbeddedValue(value);
        });
        builder.newline();
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
