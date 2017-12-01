package com.nedap.archie.rminfo;

import com.nedap.archie.aom.CPrimitiveObject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public interface ModelInfoLookup {
    Class getClass(String rmTypename);

    Class getClassToBeCreated(String rmTypename);

    Map<String, Class> getRmTypeNameToClassMap();

    RMTypeInfo getTypeInfo(Class clazz);

    Field getField(Class clazz, String attributeName);

    RMTypeInfo getTypeInfo(String rmTypeName);

    RMAttributeInfo getAttributeInfo(Class clazz, String attributeName);

    RMAttributeInfo getAttributeInfo(String rmTypeName, String attributeName);

    List<RMTypeInfo> getAllTypes();

    ModelNamingStrategy getNamingStrategy();

    Object convertToConstraintObject(Object object, CPrimitiveObject cPrimitiveObject);

    Object convertConstrainedPrimitiveToRMObject(Object object);
}
