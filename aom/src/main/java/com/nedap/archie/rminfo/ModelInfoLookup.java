package com.nedap.archie.rminfo;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public interface ModelInfoLookup {

    /**
     * Get the java class corresponding to the rm type name for this reference model
     * @param rmTypename the rm type name
     * @return the class in the implementation
     */
    Class getClass(String rmTypename);

    /**
     * Return the default class to be created for a type name
     * If this typename is abstract, MUST return a default class to be created. This allows the RMObjectCreator to work.
     * For the class Event in the OpenEHR RM for example, this can return the POINT_EVENT class
     * @param rmTypename the rm type name
     * @return the class from which an instance should be created
     */
    Class getClassToBeCreated(String rmTypename);

    /**
     * Returns a map from rm type name to java class
     * @return a map from rm type name to java class
     */
    Map<String, Class> getRmTypeNameToClassMap();

    /**
     * Get RM Type info for a specific class
     * @param clazz
     * @return
     */
    RMTypeInfo getTypeInfo(Class clazz);

    /**
     * Get the java reflection Field of an attribute of a certain class
     * @param clazz the class to lookup the attribute for
     * @param attributeName the attribute name
     * @return the java Field, or null if the attribute does not exist
     */
    Field getField(Class clazz, String attributeName);

    /**
     * Get the type info for the given RM Type Name
     * @param rmTypeName
     * @return The type info, or null if the class does not exist in the model
     */
    RMTypeInfo getTypeInfo(String rmTypeName);

    /**
     * Get AttributeInfo for the given class and attribute
     * @param clazz the class
     * @param attributeName the attribute name
     * @return the attribute info, or null if it does not exist
     */
    RMAttributeInfo getAttributeInfo(Class clazz, String attributeName);

    /**
     * Get AttributeInfo for the given typename and attribute
     * @param rmTypeName the reference model type name
     * @param attributeName the attribute name
     * @return the attribute info, or null if either the class or attribute does not exist
     */
    RMAttributeInfo getAttributeInfo(String rmTypeName, String attributeName);

    /**
     * Returns a list of all known types
     * @return a list of all knowns types
     */
    List<RMTypeInfo> getAllTypes();

    /**
     * Returns the naming strategy for the java classes of this model
     * @return
     */
    ModelNamingStrategy getNamingStrategy();

    /**
     * Converts the given rm object with the constraint in cPrimitiveObject to the corresponding AOM model
     *
     * For example, converts an OpenEHR RM CodePhrase to a TerminologyCode
     *
     * @param object the rm object
     * @param cPrimitiveObject the AOM constraint
     * @return the rm object converted to the corresponding AOM object
     */
    Object convertToConstraintObject(Object object, CPrimitiveObject cPrimitiveObject);


    /**
     * Converts from an AOM object to the corresponding reference model object
     * For example, converts a TerminologyCode to a CodePhrase
     * @param object
     * @return
     */
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
