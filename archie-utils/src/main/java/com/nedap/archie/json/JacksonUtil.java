package com.nedap.archie.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.nedap.archie.base.OpenEHRBase;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nedap.archie.rm.support.identification.ArchetypeID;

import java.io.IOException;

/**
 * Class to obtain an ObjectMapper that works with both archie RM and AOM objects, serializing into
 * a JSON with openEHR-spec type names.
 *
 * When a standard is agreed upon in the openEHR-specs, this format will likely change.
 *
 * Created by pieter.bos on 30/06/16.
 */
public class JacksonUtil {

    //threadsafe, can be cached
    private volatile static ObjectMapper objectMapper;

    /**
     * Get an object mapper that works with Archie RM and AOM objects. It will be cached in a static variable for
     * performance reasons
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        if(objectMapper == null) {
            objectMapper = new ObjectMapper();
            configureObjectMapper(objectMapper);

        }
        return objectMapper;
    }

    /**
     * Configure an existing object mapper to work with Archie RM and AOM Objects.
     * Indentation is enabled. Feel free to disable again in your own code.
     * @param objectMapper
     */
    public static void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.enable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
        objectMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //objectMapper.

        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL);

        TypeResolverBuilder typeResolverBuilder = new ArchieTypeResolverBuilder()
                .init(JsonTypeInfo.Id.NAME, new OpenEHRTypeNaming())
                .typeProperty("@type")
                .typeIdVisibility(true)
                .inclusion(JsonTypeInfo.As.PROPERTY);

        //@type is always allowed as an extra property, even if we don't expect it.
        objectMapper.addHandler(new DeserializationProblemHandler() {
            @Override
            public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser p, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException {
                if (propertyName.equalsIgnoreCase("@type")) {
                    return true;
                }
                return super.handleUnknownProperty(ctxt, p, deserializer, beanOrClass, propertyName);
            }
        });

        objectMapper.setDefaultTyping(typeResolverBuilder);

    }

    /**
     * TypeResolverBuilder that outputs type information for all RMObject classes, but not for java classes.
     * Otherwise, you get this for an arrayList: "ARRAY_LIST: []", while you would expect "[]" without type
     */
    static class ArchieTypeResolverBuilder extends ObjectMapper.DefaultTypeResolverBuilder
    {
        public ArchieTypeResolverBuilder()
        {
            super(ObjectMapper.DefaultTyping.NON_FINAL);
        }

        @Override
        public boolean useForType(JavaType t)
        {
            return (OpenEHRBase.class.isAssignableFrom(t.getRawClass()));
        }
    }
}
