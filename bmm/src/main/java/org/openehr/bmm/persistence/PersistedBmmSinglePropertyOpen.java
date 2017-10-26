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

import org.openehr.bmm.BmmConstants;
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmOpenType;
import org.openehr.bmm.core.BmmProperty;
import org.openehr.bmm.persistence.serializer.BmmSinglePropertySerializer;
import org.openehr.odin.utils.OdinSerializationUtils;

import java.io.Serializable;

/**
 * Persistent form of a BMM_SINGLE_PROPERTY_OPEN.
 *
 * Created by cnanjo on 11/24/16.
 */
public class PersistedBmmSinglePropertyOpen extends PersistedBmmProperty<PersistedBmmOpenType> implements Serializable {

    public static final String P_BMM_SINGLE_PROPERTY_OPEN = "P_BMM_SINGLE_PROPERTY_OPEN";

    /**
     * Type definition of this property, if not a simple String type reference.
     */
    private PersistedBmmOpenType typeReference;

    /**
     * Type definition of this property, if a simple String type reference. Really we should use `type_def' to be
     * regular in the schema, but that makes the schema more wordy and less clear. So we use this persisted String
     * value, and compute the `type_def' on the fly.
     */
    private String type;

    public PersistedBmmSinglePropertyOpen() {
        super();
    }

    public PersistedBmmSinglePropertyOpen(String name) {
        super(name);
    }

    public PersistedBmmSinglePropertyOpen(String name, String type) {
        this(name);
        this.type = type;
    }

    /**
     * Returns type definition of this property, if not a simple String type reference.
     * @return
     */
    public PersistedBmmOpenType getTypeReference() {
        return typeReference;
    }

    /**
     * Sets type definition of this property, if not a simple String type reference.
     *
     * @param typeReference
     */
    public void setTypeReference(PersistedBmmOpenType typeReference) {
        this.typeReference = typeReference;
    }

    /**
     * Returns type definition of this property, if a simple String type reference. Really we should use `type_def' to
     * be regular in the schema, but that makes the schema more wordy and less clear. So we use this persisted String
     * value, and compute the `type_def' on the fly.
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type definition of this property, if a simple String type reference. Really we should use `type_def' to be
     * regular in the schema, but that makes the schema more wordy and less clear. So we use this persisted String value,
     * and compute the `type_def' on the fly.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Generate `type_ref' from `type' and save.
     *
     * @return
     */
    public PersistedBmmOpenType createTypeDefinition() {
        if(typeReference == null && type != null) {
            typeReference = new PersistedBmmOpenType(type);
        }
        return typeReference;
    }

    @Override
    public PersistedBmmOpenType getTypeDefinition() {
        if(super.getTypeDefinition() == null) {
            createTypeDefinition();
            setTypeDefinition(typeReference);
        }
        return super.getTypeDefinition();
    }
}
