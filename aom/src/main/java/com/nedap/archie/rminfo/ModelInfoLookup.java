package com.nedap.archie.rminfo;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CObject;
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

    /**
     * Callback after an RM Object has been created based on a constraint. Can for example be used
     * to set names or archetype ID Node values
     */
    void processCreatedObject(Object createdObject, CObject constraint);


    /**
     * Get the archetype node id from a reference model object. Used in for example path queries.
     * Returns null if the reference model object does not have a node id.
     * @param rmObject
     * @return
     */
    String getArchetypeNodeIdFromRMObject(Object rmObject);


    /**
     * Get the name/meaning from the rmObject. Used in path queries with names instead of id codes.
     * Return null if no name is available
     * @param rmObject
     * @return
     */
    String getNameFromRMObject(Object rmObject);

    /**
     * Deeply clone the given RM Object
     * @param rmObject
     * @return
     */
    Object clone(Object rmObject);

    /**
     * Perform any actions necessary if the value at the given path has just been updated
     * For example, if an ordinal value has been set, this method should also set the symbol.
     *
     * This can be the most complex operation of this entire class to implement. If you just throw an exception instead of implementing it
     * everything will work fine except for the rule evaluation.
     *
     * For now this is only needed in the rule evaluation to automatically fix assertions
     *
     * @param rmObject
     * @param archetype
     * @param pathOfParent
     * @param parent
     */
    void pathHasBeenUpdated(Object rmObject, Archetype archetype, String pathOfParent, Object parent);
}
