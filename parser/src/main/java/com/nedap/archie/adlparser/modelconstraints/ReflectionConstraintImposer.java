package com.nedap.archie.adlparser.modelconstraints;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.Cardinality;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.reflections.ReflectionUtils.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ModelConstraintImposer that checks the constraint with java-reflection. javax.annotation.NonNull is implemented
 * as being NonNull. Other attributes are assumed to be non-null. Collection attributes are assumed to be 0..*
 *
 * Fully thread-safe, but rather expensive to create. Caching in a static field is encouraged.
 *
 * Created by pieter.bos on 04/11/15.
 */
public class ReflectionConstraintImposer implements ModelConstraintImposer {



    /** Contains complex object structure of the specified model. Attributes NEVER will have children. Sorry bout that :)*/
    private Map<String, CComplexObject> objects = new ConcurrentHashMap<>();

    //constructed as  a field to save some object creation
    protected PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy lowerCaseWithUnderscoresStrategy = new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy();

    public ReflectionConstraintImposer(String packageName) {
        this(packageName, ReflectionConstraintImposer.class.getClassLoader());
    }

    public ReflectionConstraintImposer(String packageName, ClassLoader classLoader) {
        this(new ConstraintToClassLookup(packageName, classLoader));
    }

    public ReflectionConstraintImposer(ConstraintToClassLookup classLookup) {
        Map<String, Class> rmTypeNameToClassMap = classLookup.getRmTypeNameToClassMap();
        for(String rmTypeName:rmTypeNameToClassMap.keySet()) {
            Class clazz = rmTypeNameToClassMap.get(rmTypeName);

            CComplexObject object = new CComplexObject();
            object.setRmTypeName(rmTypeName);

            Set<Field> allFields = getAllFields(clazz);
            for(Field field:allFields) {
                CAttribute attribute = new CAttribute();
                attribute.setCardinality(new Cardinality(1,1));
                attribute.setMultiple(false);
                attribute.setRmAttributeName(classLookup.getRmAttributeName(field));
                Nullable annotation = field.getAnnotation(Nullable.class);
                if(annotation != null) {
                    attribute.setCardinality(new Cardinality(0,1));
                }
                if(Collection.class.isAssignableFrom(field.getType())) {
                    attribute.setCardinality(Cardinality.unbounded());
                    attribute.setMultiple(true);
                }
                object.addAttribute(attribute);
            }
            objects.put(object.getRmTypeName(), object);

        }
    }

    @Override
    public CAttribute getDefaultAttribute(String typeId, String attribute) {
        CComplexObject object = objects.get(typeId);
        if(object == null) {
            return null;
        }
        return object.getAttribute(attribute);
    }
}
