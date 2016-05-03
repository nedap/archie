package com.nedap.archie.query;

import com.nedap.archie.rminfo.RMAttributeInfo;
import com.nedap.archie.rminfo.RMTypeInfo;
import org.apache.commons.jxpath.JXPathBeanInfo;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class RMJXPathBeanInfo implements JXPathBeanInfo {

    private RMTypeInfo typeInfo;

    private boolean atomic = false;
    private Class clazz;
    private transient List<PropertyDescriptor> propertyDescriptors;
    private transient HashMap<String, PropertyDescriptor> propertyNameToDescriptorMap;

    public RMJXPathBeanInfo(RMTypeInfo typeInfo) {
        this.typeInfo = typeInfo;
        this.clazz = typeInfo.getJavaClass();
        this.atomic = false;//for now
        propertyDescriptors = new ArrayList<>();
        int i = 0;
        for(RMAttributeInfo attributeInfo:typeInfo.getAttributes().values()) {
            PropertyDescriptor descriptor = createPropertyDescriptor(attributeInfo);
            if(descriptor != null) {
                propertyDescriptors.add(descriptor);
            }
        }
    }

    private PropertyDescriptor createPropertyDescriptor(RMAttributeInfo attributeInfo) {
        try {
            //TODO: should this be the java property name, or the RM property name?
            return new PropertyDescriptor(attributeInfo.getRmName(), attributeInfo.getGetMethod(), attributeInfo.getSetMethod());
        } catch (IntrospectionException e) {
            //TODO: gracefully degradate
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns true if objects of this class are treated as atomic
     * objects which have no properties of their own.
     * @return boolean
     */
    @Override
    public boolean isAtomic() {
        return atomic;
    }

    /**
     * Return true if the corresponding objects have dynamic properties.
     * @return boolean
     */
    @Override
    public boolean isDynamic() {
        return false;
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return propertyDescriptors.toArray(new PropertyDescriptor[propertyDescriptors.size()]);
    }

    @Override
    public PropertyDescriptor getPropertyDescriptor(String propertyName) {
        if (propertyNameToDescriptorMap == null) {
            HashMap<String, PropertyDescriptor> descriptors = new HashMap();
            PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                descriptors.put(propertyDescriptors[i].getName(), propertyDescriptors[i]);
            }
            this.propertyNameToDescriptorMap = descriptors;
        }
        return propertyNameToDescriptorMap.get(propertyName);
    }

    /**
     * For a dynamic class, returns the corresponding DynamicPropertyHandler
     * class.
     * @return Class
     */
    public Class getDynamicPropertyHandlerClass() {
        return null;
    }

    public String toString() {
       return typeInfo.toString();
    }
}