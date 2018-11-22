package org.openehr.bmm.v2.persistence.json;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.nedap.archie.base.Interval;
import com.nedap.archie.base.OpenEHRBase;
import org.openehr.bmm.v2.persistence.PBmmClass;

import java.lang.reflect.Modifier;

/**
 * Class to obtain an ObjectMapper that works with both archie RM and AOM objects, serializing into
 * a JSON with openEHR-spec type names.
 *
 * When a standard is agreed upon in the openEHR-specs, this format will likely change.
 *
 * Created by pieter.bos on 30/06/16.
 */
public class BmmJacksonUtil {

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
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        //keywords = <"value"> is indistinguishable from keywords = <"value1", "value2">
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
        objectMapper.enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL);

//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        objectMapper.enable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
//        objectMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
//        objectMapper.enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //objectMapper.

       // objectMapper.registerModule(new JavaTimeModule());


        TypeResolverBuilder typeResolverBuilder = new BmmTypeResolverBuilder()
                .init(JsonTypeInfo.Id.NAME, new BmmTypeNaming())
                .typeProperty("@type")
                .typeIdVisibility(true)
                .inclusion(JsonTypeInfo.As.PROPERTY);

        objectMapper.setDefaultTyping(typeResolverBuilder);
    }

    /**
     * TypeResolverBuilder that outputs type information for all RMObject classes, but not for java classes.
     * Otherwise, you get this for an arrayList: "ARRAY_LIST: []", while you would expect "[]" without type
     */
    static class BmmTypeResolverBuilder extends ObjectMapper.DefaultTypeResolverBuilder {
        public BmmTypeResolverBuilder() {
            super(ObjectMapper.DefaultTyping.NON_FINAL);
        }

        @Override
        public boolean useForType(JavaType t)
        {
            return OpenEHRBase.class.isAssignableFrom(t.getRawClass()) && super.useForType(t);
        }
    }

}
