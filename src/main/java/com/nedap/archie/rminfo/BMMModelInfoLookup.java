package com.nedap.archie.rminfo;

import com.nedap.archie.aom.CPrimitiveObject;
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmOpenType;
import org.openehr.bmm.core.BmmProperty;
import org.openehr.bmm.core.BmmType;
import org.openehr.bmm.persistence.PersistedBmmClass;
import org.openehr.bmm.persistence.PersistedBmmContainerType;
import org.openehr.bmm.persistence.PersistedBmmGenericType;
import org.openehr.bmm.persistence.PersistedBmmOpenType;
import org.openehr.bmm.persistence.PersistedBmmProperty;
import org.openehr.bmm.persistence.PersistedBmmSchema;
import org.openehr.bmm.persistence.PersistedBmmSimpleType;
import org.openehr.bmm.persistence.PersistedBmmType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BMMModelInfoLookup implements ModelInfoLookup {

    private Map<String, RMTypeInfo> rmTypeNamesToRmTypeInfo = new HashMap<>();
    private ModelNamingStrategy namingStrategy;

    public BMMModelInfoLookup(List<PersistedBmmSchema> schemas) {

        this.namingStrategy = new ArchieModelNamingStrategy();

        for(PersistedBmmSchema schema:schemas) {
            processSchema(schema);
        }
        processAncestors(schemas);
    }

    private void processAncestors(List<PersistedBmmSchema> schemas) {
        for(PersistedBmmSchema schema:schemas) {
            for(PersistedBmmClass persistedClass:schema.getClassDefinitions()) {
                RMTypeInfo info = rmTypeNamesToRmTypeInfo.get(persistedClass.getName());

                for(String ancestorName:persistedClass.getAncestors()) {
                    RMTypeInfo ancestorTypeInfo = rmTypeNamesToRmTypeInfo.get(ancestorName);
                    info.addDirectParentClass(ancestorTypeInfo);
                    ancestorTypeInfo.addDirectDescendantClass(info);//Set, so we can safely add this twice
                    for(RMAttributeInfo attribute:ancestorTypeInfo.getAttributes().values()) {
                        //now add all the attributes as well
                        info.addAttribute(attribute);
                    }
                }
            }
        }
    }

    private void processSchema(PersistedBmmSchema schema) {

        for(PersistedBmmClass persistedClass:schema.getClassDefinitions()) {
            RMTypeInfo typeInfo = new RMTypeInfo(null, persistedClass.getName());
            Map<String, PersistedBmmProperty> properties = persistedClass.getProperties();
            for(String propertyName:properties.keySet()) {
                addProperty(persistedClass, typeInfo, propertyName);
            }
            rmTypeNamesToRmTypeInfo.put(persistedClass.getName(), typeInfo);
        }
    }

    private void addProperty(PersistedBmmClass classDefinition, RMTypeInfo typeInfo, String propertyName) {
        PersistedBmmProperty bmmProperty = classDefinition.getProperties().get(propertyName);
        boolean nullable = !bmmProperty.getMandatory();
        RMAttributeInfo attribute = new RMAttributeInfo(propertyName,
                getTypeName(bmmProperty.getTypeDefinition()),
                nullable,
                isCollection(bmmProperty.getTypeDefinition())
        );
        typeInfo.addAttribute(attribute);
    }

    private boolean isCollection(PersistedBmmType type) {
        String typeName = getContainerTypeName(type);
        if(typeName == null) {
            return false;
        }

        String lowerCaseTypeName = typeName.toLowerCase();
        return lowerCaseTypeName.startsWith("list") || lowerCaseTypeName.startsWith("set");//todo: there has to be a better way for this
    }

    private String getContainerTypeName(PersistedBmmType type) {
        if(type instanceof PersistedBmmSimpleType) {
            return ((PersistedBmmSimpleType) type).getType();
        } else if(type instanceof PersistedBmmContainerType) {
            PersistedBmmContainerType p = (PersistedBmmContainerType) type;
            return p.getContainerType();
        } else if(type instanceof PersistedBmmOpenType) {
            PersistedBmmOpenType p = (PersistedBmmOpenType) type;
            //TODO: check if BMM allows bounds here
            return p.getType();
        } else if(type instanceof PersistedBmmGenericType) {
            PersistedBmmGenericType p = (PersistedBmmGenericType) type;
            return p.getRootType();
        }
        return null;
    }

    private String getTypeName(PersistedBmmType type) {
        if(type instanceof PersistedBmmSimpleType) {
            return ((PersistedBmmSimpleType) type).getType();
        } else if(type instanceof PersistedBmmContainerType) {
            PersistedBmmContainerType p = (PersistedBmmContainerType) type;
            return p.getContainerType();
        } else if(type instanceof PersistedBmmOpenType) {
            PersistedBmmOpenType p = (PersistedBmmOpenType) type;
            //TODO: check if BMM allows bounds here
            return p.getType();
        } else if(type instanceof PersistedBmmGenericType) {
            PersistedBmmGenericType p = (PersistedBmmGenericType) type;
            return p.getRootType();
        }
        return null;
    }

    @Override
    public RMTypeInfo getTypeInfo(String rmTypeName) {
        return rmTypeNamesToRmTypeInfo.get(rmTypeName);
    }

    @Override
    public RMAttributeInfo getAttributeInfo(String rmTypeName, String attributeName) {
        RMTypeInfo typeInfo = this.rmTypeNamesToRmTypeInfo.get(rmTypeName);
        return typeInfo == null ? null : typeInfo.getAttribute(attributeName);
    }

    @Override
    public List<RMTypeInfo> getAllTypes() {
        return new ArrayList<>(rmTypeNamesToRmTypeInfo.values());
    }

    @Override
    public ModelNamingStrategy getNamingStrategy() {
        return namingStrategy;
    }



    /////THE FOLLOWING METHODS SHOULD BE MOVED TO A SEPARATE INTERFACE.
    //They are dependent on a java model being available. That is very useful in many cases, but not if we only have BMM :)

    @Override
    public Object convertToConstraintObject(Object object, CPrimitiveObject cPrimitiveObject) {
        throw new UnsupportedOperationException("reflection model info lookup not supported in BMM schema");
    }

    @Override
    public Object convertConstrainedPrimitiveToRMObject(Object object) {
        throw new UnsupportedOperationException("reflection model info lookup not supported in BMM schema");
    }

    @Override
    public Class getClass(String rmTypename) {
        throw new UnsupportedOperationException("reflection model info lookup not supported in BMM schema");
    }

    @Override
    public Class getClassToBeCreated(String rmTypename) {
        throw new UnsupportedOperationException("reflection model info lookup not supported in BMM schema");
    }

    @Override
    public Map<String, Class> getRmTypeNameToClassMap() {
        throw new UnsupportedOperationException("reflection model info lookup not supported in BMM schema");
    }

    @Override
    public RMTypeInfo getTypeInfo(Class clazz) {
        throw new UnsupportedOperationException("reflection model info lookup not supported in BMM schema");
    }

    @Override
    public Field getField(Class clazz, String attributeName) {
        throw new UnsupportedOperationException("reflection model info lookup not supported in BMM schema");
    }



    @Override
    public RMAttributeInfo getAttributeInfo(Class clazz, String attributeName) {
        throw new UnsupportedOperationException("reflection model info lookup not supported in BMM schema");
    }

}
