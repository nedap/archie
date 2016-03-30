package com.nedap.archie.rminfo;

import com.google.common.reflect.TypeToken;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility that defines the java mapping of type and attribute names of a given reference model.
 *
 * Use it to obtain java classes for RM Type Names, and java fields, getters, setters and types for RM Attribute Names
 *
 * Created by pieter.bos on 02/02/16.
 */
public class ModelInfoLookup {

    private static final Logger logger = LoggerFactory.getLogger(ModelInfoLookup.class);

    private ModelNamingStrategy namingStrategy;

    private String packageName;
    private ClassLoader classLoader;

    private Map<String, RMTypeInfo> rmTypeNamesToRmTypeInfo = new HashMap<>();
    private Map<Class, RMTypeInfo> classesToRmTypeInfo = new HashMap<>();

    /**
     * All methods that cannot be called by using reflection. For example getClass();
     */
    Set<String> forbiddenMethods = new HashSet();

    {
        forbiddenMethods.add("getClass");
        forbiddenMethods.add("wait");
        forbiddenMethods.add("notify");
        forbiddenMethods.add("notifyAll");
        forbiddenMethods.add("clone");
        forbiddenMethods.add("finalize");
    }

    //constructed as  a field to save some object creation

    public ModelInfoLookup(ModelNamingStrategy namingStrategy, String packageName) {
        this(namingStrategy, packageName, ModelInfoLookup.class.getClassLoader());
    }

    public ModelInfoLookup(ModelNamingStrategy namingStrategy, String packageName, ClassLoader classLoader) {
        this.namingStrategy = namingStrategy;
        this.packageName = packageName;
        this.classLoader = classLoader;

        Reflections reflections = new Reflections(packageName, classLoader, new SubTypesScanner(false));

        Set<String> types = reflections.getAllTypes();
        for(String type:types) {
            try {
                Class clazz = classLoader.loadClass(type);
                addClass(clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private void addClass(Class clazz) {
        String rmTypeName = namingStrategy.getRMTypeName(clazz);
        RMTypeInfo typeInfo = new RMTypeInfo(clazz, rmTypeName);
        addAttributeInfo(clazz, typeInfo);
        rmTypeNamesToRmTypeInfo.put(rmTypeName, typeInfo);
        classesToRmTypeInfo.put(clazz, typeInfo);
    }

    private void addAttributeInfo(Class clazz, RMTypeInfo typeInfo) {
        //TODO: it's possible to constrain some method as well. should we do that here too?
        TypeToken typeToken = TypeToken.of(clazz);

        for(Field field: ReflectionUtils.getAllFields(clazz)) {
            String attributeName = namingStrategy.getRMAttributeName(field);
            String javaFieldName = field.getName();
            String javaFieldNameUpperCased = upperCaseFirstChar(javaFieldName);
            Method getMethod = getMethod(clazz, "get" + javaFieldNameUpperCased);
            Method setMethod = null, addMethod = null;
            if(getMethod == null) {
                getMethod = getMethod(clazz, "is" + javaFieldNameUpperCased);
            }
            if(getMethod != null) {
                setMethod = getMethod(clazz, "set" + javaFieldNameUpperCased, getMethod.getReturnType());
                if(Collection.class.isAssignableFrom(getMethod.getReturnType())) {
                    Type[] typeArguments = ((ParameterizedType) getMethod.getGenericReturnType()).getActualTypeArguments();
                    if(typeArguments.length == 1) {
                        TypeToken singularParameter = typeToken.resolveType(typeArguments[0]);
                        //TODO: does this work or should we use the typeArguments[0].getSomething?
                        String addMethodName = "add" + toSingular(javaFieldNameUpperCased);
                        addMethod = getMethod(clazz, addMethodName, singularParameter.getRawType());
                        if(addMethod == null) {
                            //Due to generics, this does not always work
                            Set<Method> allAddMethods = ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withName(addMethodName));
                            if(allAddMethods.size() == 1) {
                                addMethod = allAddMethods.iterator().next();
                            } else {
                                logger.warn("strange number of add methods for field {} on class {}", field.getName(), clazz.getSimpleName());
                            }
                        }
                    }
                }
            } else {
                logger.warn("No get method found for field {} on class {}", field.getName(), clazz.getSimpleName());
            }

            TypeToken fieldType = null;
            if(getMethod != null) {
                fieldType = typeToken.resolveType(getMethod.getGenericReturnType());
            } else {
                fieldType = typeToken.resolveType(field.getGenericType());
            }



            RMAttributeInfo attributeInfo = new RMAttributeInfo(
                    attributeName,
                    field,
                    fieldType.getRawType(),
                    field.getAnnotation(Nullable.class) != null,
                    getMethod,
                    setMethod,
                    addMethod
            );
            typeInfo.addAttribute(attributeInfo);
        }

    }

    private String toSingular(String javaFieldNameUpperCased) {
        if(javaFieldNameUpperCased.endsWith("s")) {
            return javaFieldNameUpperCased.substring(0, javaFieldNameUpperCased.length() - 1);
        }
        //TODO: a way to override plural names to go back to singular names. Use a library?
        return javaFieldNameUpperCased;
    }

    private Method getMethod(Class clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(name, parameterTypes);
        } catch(NoSuchMethodException ex) {
            return null;
        }
    }

    private String upperCaseFirstChar(String name) {
        return new StringBuilder(name).replace(0,1,
                Character.toString(Character.toUpperCase(name.charAt(0)))
            ).toString();
    }

    public Class getClass(String rmTypename) {

        RMTypeInfo rmTypeInfo = rmTypeNamesToRmTypeInfo.get(rmTypename);
        return rmTypeInfo == null ? null : rmTypeInfo.getJavaClass();
    }

    public  Map<String, Class> getRmTypeNameToClassMap() {
        HashMap<String, Class> result = new HashMap<>();
        for(String rmTypeName: rmTypeNamesToRmTypeInfo.keySet()) {
            result.put(rmTypeName, rmTypeNamesToRmTypeInfo.get(rmTypeName).getJavaClass());
        }
        return result;
    }



    public Field getField(Class clazz, String attributeName) {
        RMTypeInfo typeInfo = classesToRmTypeInfo.get(clazz);
        RMAttributeInfo attributeInfo = typeInfo == null ? null : typeInfo.getAttribute(attributeName);
        return attributeInfo == null ? null : attributeInfo.getField();
    }

    public RMTypeInfo getTypeInfo(String rmTypeName) {
        return this.rmTypeNamesToRmTypeInfo.get(rmTypeName);
    }

    public RMAttributeInfo getAttributeInfo(Class clazz, String attributeName) {
        RMTypeInfo typeInfo = this.classesToRmTypeInfo.get(clazz);
        return typeInfo == null ? null : typeInfo.getAttribute(attributeName);
    }

    public RMAttributeInfo getAttributeInfo(String rmTypeName, String attributeName) {
        RMTypeInfo typeInfo = this.rmTypeNamesToRmTypeInfo.get(rmTypeName);
        return typeInfo == null ? null : typeInfo.getAttribute(attributeName);
    }

    public List<RMTypeInfo> getAllTypes() {
        return new ArrayList<>(classesToRmTypeInfo.values());
    }

    public ModelNamingStrategy getNamingStrategy() {
        return namingStrategy;
    }
}
