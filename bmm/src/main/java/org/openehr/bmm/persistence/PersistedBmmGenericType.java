package org.openehr.bmm.persistence;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017  Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 * Author: Claude Nanjo
 */

import org.openehr.bmm.core.*;

import java.io.Serializable;
import java.util.*;

/**
 * Persistent form of BMM_GENERIC_TYPE.
 *
 * Created by cnanjo on 4/11/16.
 */
public class PersistedBmmGenericType extends PersistedBmmType<BmmGenericType> implements Serializable {

    public static final String P_BMM_GENERIC_TYPE = "P_BMM_GENERIC_TYPE";

    /**
     * Root type of this generic type, e.g. 'Interval' in 'Interval<Integer>'.
     */
    private String rootType;
    /**
     * Generic parameters of the root_type in this type specifier if non-simple types. The order must match the order
     * of the owning class’s formal generic parameter declarations. Persistent attribute.
     */
    private Map<String, PersistedBmmType> genericParameterDefinitions;
    /**
     * Generic parameters of the root_type in this type specifier, if simple types. The order must match the order of
     * the owning class’s formal generic parameter declarations. Persistent attribute.
     */
    private Map<String, String> genericParameters;

    /**
     * Count used as the parameter position if not key is provided
     */
    private int parameterCount;

    public PersistedBmmGenericType() {
        this.genericParameterDefinitions = new LinkedHashMap<>();
        this.genericParameters = new LinkedHashMap<>();
    }

    /**
     * Returns the root type of this generic type, e.g. 'Interval' in 'Interval<Integer>'.
     *
     * @return
     */
    public String getRootType() {
        return rootType;
    }

    /**
     * Sets the root type of this generic type, e.g. 'Interval' in 'Interval<Integer>'.
     *
     * @param rootType
     */
    public void setRootType(String rootType) {
        this.rootType = rootType;
    }

    /**
     * Returns the generic parameters of the root_type in this type specifier if non-simple types. The order must match
     * the order of the owning class’s formal generic parameter declarations.
     *
     * @return
     */
    public Map<String, PersistedBmmType> getGenericParameterDefinitions() {
        return genericParameterDefinitions;
    }

    /**
     * Sets the generic parameters of the root_type in this type specifier if non-simple types. The order must match
     * the order of the owning class’s formal generic parameter declarations.
     *
     *
     * @param genericParameterDefinitions
     */
    public void setGenericParameterDefinitions(Map<String, PersistedBmmType> genericParameterDefinitions) {
        this.genericParameterDefinitions = genericParameterDefinitions;
    }

    /**
     * Adds generic parameter definition. Sets the key for the parameter, its insertion position.
     * When no parameter names are provided, the order and number of the parameters must match the
     * class definition or an error will occur during validation.
     *
     * @param genericParameterDefinition
     */
    public void addGenericParameterDefinition(PersistedBmmType genericParameterDefinition) {
        this.getGenericParameterDefinitions().put("" + parameterCount, genericParameterDefinition);
        parameterCount++;
    }

    /**
     * Adds generic parameter definition. Associates a parameter definition to a parameter with the given name. The
     * parameter's name must much the type definition's parameter name.
     *
     * @param genericParameterDefinition
     */
    public void addGenericParameterDefinition(String parameterName, PersistedBmmType genericParameterDefinition) {
        this.getGenericParameterDefinitions().put(parameterName, genericParameterDefinition);
        parameterCount++;
    }

    /**
     * Returns the generic parameters of the root_type in this type specifier, if simple types. The order must match the
     * order of the owning class’s formal generic parameter declarations.
     *
     * @return
     */
    public Map<String, String> getGenericParameters() {
        return genericParameters;
    }

    /**
     * Sets the generic parameters of the root_type in this type specifier, if simple types. The order must match the
     * order of the owning class’s formal generic parameter declarations.
     *
     * @param genericParameters
     */
    public void setGenericParameters(Map<String, String> genericParameters) {
        this.genericParameters = genericParameters;
    }

    /**
     * Sets the generic parameters of the root_type in this type specifier, if simple types. The order must match the
     * order of the owning class’s formal generic parameter declarations.
     *
     * @param genericParameters
     */
    public void setGenericParameters(List<String> genericParameters) {
        parameterCount = genericParameters.size();
        for(String parameter : genericParameters) {
            addGenericParameter(parameter);
        }
    }

    /**
     * Adds generic parameter. The key for the parameter will be the insertion order of the parameter into the map.
     * If no parameter name is provided, the number and order of the parameters must match the type's definition.
     *
     * @param genericParameter
     */
    public void addGenericParameter(String genericParameter) {
        this.genericParameters.put("" + parameterCount, genericParameter);
        parameterCount++;
    }

    /**
     * Adds generic parameter. The key represent the name of the parameter specified in the given type's definition. The
     * value is the type specification for this parameter.
     *
     * @param genericParameter
     */
    public void addGenericParameter(String parameterName, String genericParameter) {
        this.genericParameters.put(parameterName, genericParameter);
    }

    /**
     * Returns the generic parameters of the root_type in this type specifier. The order must match the order of the
     * owning class’s formal generic parameter declarations
     *
     * @return
     */
    public List<PersistedBmmType> getGenericParameterReferences() {
        List<PersistedBmmType> genericParameterReferences = new ArrayList<>();
        if(genericParameterDefinitions != null && genericParameterDefinitions.size() > 0) {
            genericParameterReferences.addAll(genericParameterDefinitions.values());
        } else {
            genericParameters.values().forEach(param -> {
                if(param.length() == 1) { //Probably a parameter name such as "T". Not ideal to do this though.
                    PersistedBmmOpenType openType = new PersistedBmmOpenType(param);
                    genericParameterReferences.add(openType);
                } else {
                    PersistedBmmSimpleType simpleType = new PersistedBmmSimpleType(param);
                    genericParameterReferences.add(simpleType);
                }
            });
        }
        return genericParameterReferences;
    }

    /**
     * Create appropriate BMM_ object; defined in descendants.
     *
     * @param schema
     * @param classDefinition
     */
    @Override
    public void createBmmType(BmmModel schema, BmmClass classDefinition) {
        BmmGenericType genericType = new BmmGenericType();
        if(schema.getClassDefinition(rootType) != null && schema.getClassDefinition(rootType) instanceof BmmGenericClass) {
            BmmGenericClass baseClass = (BmmGenericClass)schema.getClassDefinition(rootType);
            genericType.setBaseClass(baseClass);
            getGenericParameterReferences().forEach(param -> {
                if(param.getBmmType() == null) {
                    param.createBmmType(schema, classDefinition);
                }
                genericType.addGenericParameter(param.getBmmType());
            });
        }
        setBmmType(genericType);
    }

    /**
     * Formal name of the type for display.
     *
     * @return
     */
    @Override
    public String asTypeString() {
        StringBuilder builder = new StringBuilder();
        builder.append(rootType).append("<");
        List<PersistedBmmType> parameterReferences = getGenericParameterReferences();
        for(int i = 0; i < parameterReferences.size(); i++) {
            builder.append(parameterReferences.get(i).asTypeString());
            if(i < parameterReferences.size() - 1) {
                builder.append(",");
            }
        };
        builder.append(">");
        return builder.toString();
    }

    /**
     * flattened list of type names making up full type.
     * @return
     */
    public List<String> flattenedTypeList() {
        List<String> retVal = new ArrayList<>();
        getGenericParameterReferences().forEach( item -> {
            retVal.addAll(item.flattenedTypeList());
        });
        return retVal;
    }
}
