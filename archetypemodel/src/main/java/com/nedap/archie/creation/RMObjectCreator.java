package com.nedap.archie.creation;

import com.nedap.archie.aom.CObject;
import com.nedap.archie.rm.archetypes.Locatable;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.RMAttributeInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pieter.bos on 03/02/16.
 */
public class RMObjectCreator {

    private final ModelInfoLookup classLookup;

    public RMObjectCreator(ModelInfoLookup lookup) {
        this.classLookup = lookup;
    }

    public <T> T create(CObject constraint) {
        Class clazz = classLookup.getClass(constraint.getRmTypeName());
        if(clazz == null) {
            throw new IllegalArgumentException("cannot construct RMObject because of unknown constraint name " + constraint.getRmTypeName() + " full constraint " + constraint);
        }
        try {
            Object result = clazz.newInstance();
            if(result instanceof Locatable) { //and most often, it will be
                Locatable locatable = (Locatable) result;
                locatable.setArchetypeNodeId(constraint.getNodeId());
            }
            return (T) result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(Object object, String rmAttributeName, List<Object> values) {
        try {
            RMAttributeInfo attributeInfo = classLookup.getAttributeInfo(object.getClass(), rmAttributeName);
            if(attributeInfo == null) {
                throw new IllegalArgumentException(String.format("Attribute %s not known for object %s", rmAttributeName, object.getClass().getSimpleName()));
            }

            Field field = attributeInfo.getField();
            Type type = attributeInfo.getType();
            if(type instanceof Class) {
                Class clazz = (Class) type;
                if(Collection.class.isAssignableFrom(clazz)) {
                    Collection collection = (Collection) newInstance(attributeInfo);
                    setField(object, attributeInfo, collection);
                    if(values != null) {
                        collection.addAll(values);
                    }
                } else {
                    setSingleValuedAttribute(object, rmAttributeName, values, attributeInfo);
                }
            } else {
                //primitive value.
                setSingleValuedAttribute(object, rmAttributeName, values, attributeInfo);
            }

        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    private void setSingleValuedAttribute(Object object, String rmAttributeName, List<Object> values, RMAttributeInfo attributeInfo) throws InvocationTargetException, IllegalAccessException {
        if(values == null || values.isEmpty()) {
            setField(object, attributeInfo, null);
        } else if(values.size() > 1) {
            throw new IllegalArgumentException(String.format("trying to set multiple values for a single valued field, %s %s",
                    object.getClass().getSimpleName(), rmAttributeName)
            );
        } else {
            setField(object, attributeInfo, values.get(0));
        }
    }

    private Object newInstance(RMAttributeInfo attributeInfo) throws InstantiationException, IllegalAccessException {
        if(attributeInfo.getType().equals(List.class)) {
            return new ArrayList<>();
        } else if (attributeInfo.getType().equals(Set.class)) {
            return new LinkedHashSet<>();
        } else if(attributeInfo.getType() instanceof Class){
            return ((Class)attributeInfo.getType()).newInstance();
        } else {
            throw new IllegalArgumentException("cannot create collection instanceof " + attributeInfo.toString());
        }
    }

    private void setField(Object object, RMAttributeInfo field, Object value) throws InvocationTargetException, IllegalAccessException {
        field.getSetMethod().invoke(object, value);

    }

    public void addElementToList(Object object, RMAttributeInfo attributeInfo, Object element) {
        try {
            if(attributeInfo.getAddMethod() != null) {
                attributeInfo.getAddMethod().invoke(object, element);
            } else {
                Object collectionValue = attributeInfo.getGetMethod().invoke(object);
                if(!(attributeInfo.getType() instanceof Class)) {
                    throw new IllegalArgumentException("trying to add an element to an object with type " + attributeInfo.getType());
                }
                if(Collection.class.isAssignableFrom((Class) attributeInfo.getType())) {
                    if(collectionValue == null) {
                        collectionValue = newInstance(attributeInfo);
                        setField(object, attributeInfo, collectionValue);
                    }
                    Collection collection = (Collection) collectionValue;
                    collection.add(element);
                } else {
                    throw new IllegalArgumentException("trying to add an element to an object with type " + attributeInfo.getType());
                }
            }

        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
