package com.nedap.archie.adlparser.modelconstraints;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.nedap.archie.aom.CComplexObject;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllFields;

/**
 * Utility to find Reference Model classed based on the type names as used in the Archetype
 *
 * Created by pieter.bos on 02/02/16.
 */
public class ConstraintToClassLookup {

    private String packageName;
    private ClassLoader classLoader;

    private Map<String, Class> rmTypeNamesToClasses = new HashMap<>();

    //constructed as  a field to save some object creation
    protected PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy lowerCaseWithUnderscoresStrategy = new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy();

    public ConstraintToClassLookup(String packageName) {
        this(packageName, ReflectionConstraintImposer.class.getClassLoader());
    }

    public ConstraintToClassLookup(String packageName, ClassLoader classLoader) {
        this.packageName = packageName;
        this.classLoader = classLoader;

        Reflections reflections = new Reflections(packageName, classLoader, new SubTypesScanner(false));

        Set<String> types = reflections.getAllTypes();
        for(String type:types) {
            try {
                Class clazz = classLoader.loadClass(type);
                String rmTypeName = getRmTypeName(clazz);
                rmTypeNamesToClasses.put(rmTypeName, clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public Class getClass(String rmTypename) {
        return rmTypeNamesToClasses.get(rmTypename);
    }

    public  Map<String, Class> getRmTypeNameToClassMap() {
        return rmTypeNamesToClasses;
    }

    /**
     * Naming from fields. Override to get custom behaviour
     * @param field
     * @return
     */
    public String getRmAttributeName(Field field) {
        return lowerCaseWithUnderscoresStrategy.translate(field.getName());
    }

    public Field getField(Class clazz, String attributeName) {
        Set<Field> allFields = getAllFields(clazz);
        for(Field field:allFields) {
            if(attributeName.equals(getRmAttributeName(field))) {
                return field;
            }
        }
        return null;
    }


    /**
     * Naming from classes. Override to get custom behaviour
     * @param clazz
     * @return
     */
    public String getRmTypeName(Class clazz) {
        return lowerCaseWithUnderscoresStrategy.translate(clazz.getSimpleName()).toUpperCase();
    }
}
