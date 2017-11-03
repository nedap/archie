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

import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmProperty;
import org.openehr.bmm.core.BmmSimpleType;

import java.io.Serializable;

/**
 *  Persistent form of BMM_SINGLE_PROPERTY.
 *
 *  Created by cnanjo on 1/26/17.
 */
public class PersistedBmmSingleProperty extends PersistedBmmProperty<PersistedBmmSimpleType> implements Serializable {

    public static final String P_BMM_SINGLE_PROPERTY = "P_BMM_SINGLE_PROPERTY";

    /**
     *
     If the type is a simple type, then this attribute will hold the type name. If the type is a container or generic,
     then type_ref will hold the type definition. The resulting type is generated in type_def.
     */
    private String type;
    /**
     * Type definition of this property, if not a simple String type reference. Can be used in schema instead of `type',
     * but less readable. Persisted attribute.
     */
    private PersistedBmmSimpleType typeReference;

    /**
     * No-arg constructor
     */
    public PersistedBmmSingleProperty() {
        super();
    }

    /**
     * Configures a new property with the specified name
     *
     * @param name
     */
    public PersistedBmmSingleProperty(String name) {
        super(name);
    }

    public PersistedBmmSingleProperty(String name, boolean isMandatory) {
        super(name, isMandatory);
    }

    public PersistedBmmSingleProperty(String name, boolean isMandatory, String type) {
        super(name, isMandatory);
        setType(type);
    }

    /**
     * If the type is a simple type, then this attribute will hold the type name. If the type is a container or generic,
     * then type_ref will hold the type definition. The resulting type is generated in type_def.
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type for this property.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Type definition of this property, if not a simple String type reference. Can be used in schema instead of `type',
     * but less readable.
     * @return
     */
    public PersistedBmmSimpleType getTypeReference() {
        return typeReference;
    }

    /**
     * Sets the type reference for this property.
     *
     * @param typeReference
     */
    public void setTypeReference(PersistedBmmSimpleType typeReference) {
        this.typeReference = typeReference;
    }

    /**
     * Generate `type_ref' from `type' and save.
     *
     * @return
     */
    public PersistedBmmSimpleType createTypeDefinition() {
        if(typeReference == null && type != null) {
            typeReference = new PersistedBmmSimpleType(type);
        }
        return typeReference;
    }

    @Override
    public PersistedBmmSimpleType getTypeDefinition() {
        if(super.getTypeDefinition() == null) {
            createTypeDefinition();
            setTypeDefinition(typeReference);
        }
        return super.getTypeDefinition();
    }
}
