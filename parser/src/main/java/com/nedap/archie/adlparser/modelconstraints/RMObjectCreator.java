package com.nedap.archie.adlparser.modelconstraints;

import com.nedap.archie.aom.CObject;
import com.nedap.archie.rm.archetypes.Locatable;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pieter.bos on 03/02/16.
 */
public class RMObjectCreator {

    private final ConstraintToClassLookup classLookup;

    public RMObjectCreator(ConstraintToClassLookup lookup) {
        this.classLookup = lookup;
    }

    public Object create(CObject constraint) {
        Class clazz = classLookup.getClass(constraint.getRmTypeName());
        try {
            Object result = clazz.newInstance();
            if(result instanceof Locatable) { //and most often, it will be
                Locatable locatable = (Locatable) result;
                locatable.setArchetypeNodeId(constraint.getNodeId());
            }
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(Object object, String rmAttributeName, List<Object> values) {
        try {
            Field field = classLookup.getField(object.getClass(), rmAttributeName);
            if(Collection.class.isAssignableFrom(field.getType())) {
                Collection collection = (Collection) newInstance(field);
                setField(object, field, collection);
                if(values != null) {
                    collection.addAll(values);
                }
            } else {
                if(values == null || values.isEmpty()) {
                    setField(object, field, null);
                } else if(values.size() > 1) {
                    throw new IllegalArgumentException(String.format("trying to set multiple values for a single valued field, %s %s",
                                    object.getClass().getSimpleName(), rmAttributeName)
                    );
                } else {
                    setField(object, field, values.get(0));
                }
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    private Object newInstance(Field field) throws InstantiationException, IllegalAccessException {
        if(field.getType().equals(List.class)) {
            return new ArrayList<>();
        } else if (field.getType().equals(Set.class)) {
            return new LinkedHashSet<>();
        } else {
            return field.getType().newInstance();
        }
    }

    private void setField(Object object, Field field, Object value) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.setProperty(object, field.getName(), value);

    }

    public void addElementToList(Object object, String rmAttributeName, Object element) {
        try {
            Field field = classLookup.getField(object.getClass(), rmAttributeName);
            Object collectionValue = field.get(object);

            if(Collection.class.isAssignableFrom(field.getType())) {
                if(collectionValue == null) {
                    collectionValue = newInstance(field);
                    setField(object, field, collectionValue);
                }
                Collection collection = (Collection) collectionValue;
                collection.add(element);//TODO: use add method in object instead!
            } else {
                throw new IllegalArgumentException("trying to add an element to an object with type " + field.getType());
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
