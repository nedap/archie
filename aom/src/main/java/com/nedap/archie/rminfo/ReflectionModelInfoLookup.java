package com.nedap.archie.rminfo;

import com.google.common.reflect.TypeToken;
import com.nedap.archie.aom.CPrimitiveObject;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Utility that defines the java mapping of type and attribute names of a given reference model.
 *
 * Use it to obtain java classes for RM Type Names, and java fields, getters, setters and types for RM Attribute Names
 *
 * This class is never directly created, but subclasses must be created that setup the correct model. Create a subclass
 * per model you want to use with Archie, for example one for an OpenEHR RM implementation, or the CIMI RM implementation
 *
 * Created by pieter.bos on 02/02/16.
 */
public abstract class ReflectionModelInfoLookup implements ModelInfoLookup {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionModelInfoLookup.class);

    private ModelNamingStrategy namingStrategy;

    private String packageName;
    private ClassLoader classLoader;

    private Map<String, RMTypeInfo> rmTypeNamesToRmTypeInfo = new HashMap<>();
    private Map<Class, RMTypeInfo> classesToRmTypeInfo = new HashMap<>();

    private boolean inConstructor = true;

    /**
     * All methods that cannot be called by using reflection. For example getClass();
     */
    @SuppressWarnings("unchecked")
    private Set<String> forbiddenMethods = new HashSet(
        Arrays.asList("getClass", "wait", "notify", "notifyAll", "clone", "finalize")
    );

    public ReflectionModelInfoLookup(ModelNamingStrategy namingStrategy, Class baseClass) {
        this(namingStrategy, baseClass, ReflectionModelInfoLookup.class.getClassLoader());
    }

    public ReflectionModelInfoLookup(ModelNamingStrategy namingStrategy, String packageName, ClassLoader classLoader) {
        this.packageName = packageName;
        this.namingStrategy = namingStrategy;

        this.classLoader = classLoader;
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        Set<String> typeNames = reflections.getAllTypes();

        System.out.println("type names size: " + typeNames.size());
        typeNames.forEach(typeName -> {
            try {
                addClass(classLoader.loadClass(typeName));
            } catch (ClassNotFoundException e) {
                logger.error("error loading model info lookup", e);
            }
        });
        addSuperAndSubclassInfo();
        inConstructor = false;
    }

    public ReflectionModelInfoLookup(ModelNamingStrategy namingStrategy, Class baseClass, ClassLoader classLoader) {
        this.namingStrategy = namingStrategy;

        this.classLoader = classLoader;
        addSubtypesOf(baseClass);
        addSuperAndSubclassInfo();
        inConstructor = false;
    }

    private void addSuperAndSubclassInfo() {
        for(RMTypeInfo typeInfo:rmTypeNamesToRmTypeInfo.values()) {
            Class superclass = typeInfo.getJavaClass().getSuperclass();
            if(!superclass.equals(Object.class)) {
                addDescendantClass(typeInfo, superclass);
            }

            for(Class interfaceClass:typeInfo.getJavaClass().getInterfaces()) {
                addDescendantClass(typeInfo, interfaceClass);
            }
        }

    }

    private void addDescendantClass(RMTypeInfo typeInfo, Class interfaceClass) {
        RMTypeInfo superClassTypeInfo = this.getTypeInfo(interfaceClass);
        if(superClassTypeInfo != null) {
            typeInfo.addDirectParentClass(superClassTypeInfo);
            superClassTypeInfo.addDirectDescendantClass(typeInfo);
        }
    }

    /**
     * Add all subtypes of the given class
     * @param baseClass
     */
    protected void addSubtypesOf(Class baseClass) {
        Reflections reflections = new Reflections(ClasspathHelper.forClass(baseClass), new SubTypesScanner(false));
        Set<Class<?>> classes = reflections.getSubTypesOf(baseClass);

        System.out.println("type names size: " + classes.size());
        classes.forEach(this::addClass);
        addClass(baseClass);
    }

    protected void addClass(Class clazz) {
        String rmTypeName = namingStrategy.getTypeName(clazz);
        RMTypeInfo typeInfo = new RMTypeInfo(clazz, rmTypeName);
        addAttributeInfo(clazz, typeInfo);
        rmTypeNamesToRmTypeInfo.put(rmTypeName, typeInfo);
        classesToRmTypeInfo.put(clazz, typeInfo);
        if(!inConstructor) {
            //if someone called this after initial creation, we need to update super/subclass info.
            //could be done more efficiently by only updating for the added class and parents/descendants, but
            //should not be a problem to do it this way
            addSuperAndSubclassInfo();
        }
    }

    private void addAttributeInfo(Class clazz, RMTypeInfo typeInfo) {
        //TODO: it's possible to constrain some method as well. should we do that here too?
        TypeToken typeToken = TypeToken.of(clazz);

        for(Field field: ReflectionUtils.getAllFields(clazz)) {
            addRMAttributeInfo(clazz, typeInfo, typeToken, field);
        }
    }

    private void addRMAttributeInfo(Class clazz, RMTypeInfo typeInfo, TypeToken typeToken, Field field) {
        String attributeName = namingStrategy.getAttributeName(field);
        String javaFieldName = field.getName();
        String javaFieldNameUpperCased = upperCaseFirstChar(javaFieldName);
        Method getMethod = getMethod(clazz, "get" + javaFieldNameUpperCased);
        Method setMethod = null, addMethod = null;
        if (getMethod == null) {
            getMethod = getMethod(clazz, "is" + javaFieldNameUpperCased);
        }
        if (getMethod != null) {
            setMethod = getMethod(clazz, "set" + javaFieldNameUpperCased, getMethod.getReturnType());
            addMethod = getAddMethod(clazz, typeToken, field, javaFieldNameUpperCased, getMethod);
        } else {
            logger.warn("No get method found for field {} on class {}", field.getName(), clazz.getSimpleName());
        }

        TypeToken fieldType = null;
        if (getMethod != null) {
            fieldType = typeToken.resolveType(getMethod.getGenericReturnType());
        } else {
            fieldType = typeToken.resolveType(field.getGenericType());
        }

        Class rawFieldType = fieldType.getRawType();
        Class typeInCollection = getTypeInCollection(fieldType);
        if (setMethod != null) {
            RMAttributeInfo attributeInfo = new RMAttributeInfo(
                    attributeName,
                    field,
                    rawFieldType,
                    typeInCollection,
                    field.getAnnotation(Nullable.class) != null,
                    getMethod,
                    setMethod,
                    addMethod
            );
            typeInfo.addAttribute(attributeInfo);
        } else {
            logger.info("property without a set method ignored for field {} on class {}", field.getName(), clazz.getSimpleName());
        }
    }

    private Class getTypeInCollection(TypeToken fieldType) {
        Class rawFieldType = fieldType.getRawType();
        if (Collection.class.isAssignableFrom(rawFieldType)) {
            Type[] actualTypeArguments = ((ParameterizedType) fieldType.getType()).getActualTypeArguments();
            if (actualTypeArguments.length == 1) {
                //the java reflection api is kind of tricky with types. This works for the archie RM, but may cause problems for other RMs. The fix is implementing more ways
                if (actualTypeArguments[0] instanceof Class) {
                    return (Class) actualTypeArguments[0];
                } else if (actualTypeArguments[0] instanceof ParameterizedType) {
                    ParameterizedType parameterizedTypeInCollection = (ParameterizedType) actualTypeArguments[0];
                    return (Class) parameterizedTypeInCollection.getRawType();
                } else if (actualTypeArguments[0] instanceof java.lang.reflect.TypeVariable) {
                    return (Class) ((java.lang.reflect.TypeVariable) actualTypeArguments[0]).getBounds()[0];
                }
            }
        }
        return rawFieldType;
    }

    private Method getAddMethod(Class clazz, TypeToken typeToken, Field field, String javaFieldNameUpperCased, Method getMethod) {
        Method addMethod = null;
        if (Collection.class.isAssignableFrom(getMethod.getReturnType())) {
            Type[] typeArguments = ((ParameterizedType) getMethod.getGenericReturnType()).getActualTypeArguments();
            if (typeArguments.length == 1) {
                TypeToken singularParameter = typeToken.resolveType(typeArguments[0]);
                //TODO: does this work or should we use the typeArguments[0].getSomething?
                String addMethodName = "add" + toSingular(javaFieldNameUpperCased);
                addMethod = getMethod(clazz, addMethodName, singularParameter.getRawType());
                if (addMethod == null) {
                    //Due to generics, this does not always work
                    Set<Method> allAddMethods = ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withName(addMethodName));
                    if (allAddMethods.size() == 1) {
                        addMethod = allAddMethods.iterator().next();
                    } else {
                        logger.warn("strange number of add methods for field {} on class {}", field.getName(), clazz.getSimpleName());
                    }
                }
            }
        }
        return addMethod;
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

    @Override
    public Class getClass(String rmTypename) {
        RMTypeInfo rmTypeInfo = rmTypeNamesToRmTypeInfo.get(rmTypename);
        return rmTypeInfo == null ? null : rmTypeInfo.getJavaClass();
    }

    @Override
    public Class getClassToBeCreated(String rmTypename) {
        return getClass(rmTypename);
    }

    @Override
    public Map<String, Class> getRmTypeNameToClassMap() {
        HashMap<String, Class> result = new HashMap<>();
        for(String rmTypeName: rmTypeNamesToRmTypeInfo.keySet()) {
            result.put(rmTypeName, rmTypeNamesToRmTypeInfo.get(rmTypeName).getJavaClass());
        }
        return result;
    }

    @Override
    public RMTypeInfo getTypeInfo(Class clazz) {
        return this.classesToRmTypeInfo.get(clazz);
    }

    @Override
    public Field getField(Class clazz, String attributeName) {
        RMTypeInfo typeInfo = classesToRmTypeInfo.get(clazz);
        RMAttributeInfo attributeInfo = typeInfo == null ? null : typeInfo.getAttribute(attributeName);
        return attributeInfo == null ? null : attributeInfo.getField();
    }

    @Override
    public RMTypeInfo getTypeInfo(String rmTypeName) {
        return this.rmTypeNamesToRmTypeInfo.get(rmTypeName);
    }

    @Override
    public RMAttributeInfo getAttributeInfo(Class clazz, String attributeName) {
        RMTypeInfo typeInfo = this.classesToRmTypeInfo.get(clazz);
        return typeInfo == null ? null : typeInfo.getAttribute(attributeName);
    }

    @Override
    public RMAttributeInfo getAttributeInfo(String rmTypeName, String attributeName) {
        RMTypeInfo typeInfo = this.rmTypeNamesToRmTypeInfo.get(rmTypeName);
        return typeInfo == null ? null : typeInfo.getAttribute(attributeName);
    }

    @Override
    public List<RMTypeInfo> getAllTypes() {
        return new ArrayList<>(classesToRmTypeInfo.values());
    }

    @Override
    public ModelNamingStrategy getNamingStrategy() {
        return namingStrategy;
    }

    /**
     * Convert the given reference model object to the object required for the archetype constraint.
     *
     * for example, a CTerminologyCode can be used to check a CodePhrase or a DvCodedText. This cannot be directly checked and must be converted first.
     *
     * @param object
     * @param cPrimitiveObject
     * @return
     */
    @Override
    public Object convertToConstraintObject(Object object, CPrimitiveObject cPrimitiveObject) {
        return object;
    }

    @Override
    public Object convertConstrainedPrimitiveToRMObject(Object object) {
        //TODO: this should take an AttributeInfo as param, so to be able to pick the right object
        return object;
    }
}
