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
import java.util.ArrayList;
import java.util.List;

/**
 * Persistent form of BMM_CONTAINER_TYPE.
 *
 * Created by cnanjo on 4/11/16.
 */
public class PersistedBmmContainerType extends PersistedBmmType<BmmContainerType> implements Serializable {

    public static final String P_BMM_CONTAINER_TYPE = "P_BMM_CONTAINER_TYPE";

    /**
     * The target type; this converts to the first parameter in generic_parameters in BMM_GENERIC_TYPE. Persisted attribute.
     */
    private String type;
    /**
     * Type definition of `type', if not a simple String type reference. Persisted attribute.
     */
    private PersistedBmmType typeDefinition;
    /**
     * The type of the container. This converts to the root_type in BMM_GENERIC_TYPE. Persisted attribute.
     */
    private String containerType;



    public PersistedBmmContainerType() {
        super();
    }

    public PersistedBmmContainerType(String containerType, String type) {
        this();
        this.containerType = containerType;
        this.type = type;
    }

    /**
     * Returns the type of the container. This converts to the root_type in BMM_GENERIC_TYPE. Persisted attribute.
     *
     * @return
     */
    public String getContainerType() {
        return containerType;
    }

    /**
     * Sets the type of the container. This converts to the root_type in BMM_GENERIC_TYPE. Persisted attribute.
     *
     * @param containerType
     */
    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    /**
     * Returns the type definition of `type', if not a simple String type reference. Persisted attribute.
     *
     * @return
     */
    public PersistedBmmType getTypeDefinition() {
        return typeDefinition;
    }

    /**
     * Sets the type definition of `type', if not a simple String type reference. Persisted attribute.
     *
     * @param typeDef
     */
    public void setTypeDefinition(PersistedBmmType typeDef) {
        this.typeDefinition = typeDef;
    }

    /**
     * Returns the target type; this converts to the first parameter in generic_parameters in BMM_GENERIC_TYPE. Persisted attribute.
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the target type; this converts to the first parameter in generic_parameters in BMM_GENERIC_TYPE. Persisted attribute.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The target type; this converts to the first parameter in generic_parameters in BMM_GENERIC_TYPE. Persisted attribute.
     *
     * @return
     */
    public PersistedBmmType getTypeReference() {
        if(typeDefinition == null && type != null) {
            if(type.length() == 1) {//Probably a parameter such as "T"
                typeDefinition = new PersistedBmmOpenType(type);
            } else {
                typeDefinition = new PersistedBmmSimpleType(type);
            }
        }
        return typeDefinition;
    }

    /**
     * Create appropriate BMM_ object; defined in descendants.
     *
     * @param schema
     * @param classDefinition
     */
    @Override
    public void createBmmType(BmmModel schema, BmmClass classDefinition) {
        BmmContainerType bmmContainerType = new BmmContainerType();
        getTypeReference().createBmmType(schema, classDefinition);
        BmmType type = getTypeReference().getBmmType();
        BmmClass containerClass = schema.getClassDefinition(containerType);
        if(containerClass == null) {
            throw new RuntimeException("Container type is null for " + classDefinition.getName());
        } else {
            bmmContainerType.setContainerType(containerClass);
        }
        bmmContainerType.setBaseType(type);
        setBmmType(bmmContainerType);
    }

    /**
     * Formal name of the type for display.
     *
     * @return
     */
    @Override
    public String asTypeString() {
        return containerType + "<" + typeDefinition.asTypeString() + ">";
    }

    public List<String> flattenedTypeList() {
        List<String> retVal = new ArrayList<>();
        retVal.add(containerType);
        if(getTypeReference() != null) {
            retVal.addAll(getTypeReference().flattenedTypeList());
        }
        return retVal;
    }
}
