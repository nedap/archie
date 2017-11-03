package org.openehr.bmm.persistence.serializer;

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
import org.openehr.bmm.persistence.PersistedBmmContainerType;
import org.openehr.bmm.persistence.PersistedBmmType;
import org.openehr.odin.utils.OdinSerializationUtils;

public class BmmContainerTypeSerializer extends BmmTypeSerializer<PersistedBmmContainerType> {

    /**
     * No-arg constructor.
     */
    public BmmContainerTypeSerializer() {
        super();
    }

    /**
     * Constructor setting the model to be serialized.
     *
     * @param persistedModel
     */
    public BmmContainerTypeSerializer(PersistedBmmContainerType persistedModel) {
        super(persistedModel, true, false);
    }

    /**
     * Method serializes the object in BMM-ODIN syntax and returns the serialization as a string.
     *
     * @param indentationCount The indentation to use for the serialization.
     * @return
     */
    @Override
    public String serialize(int indentationCount) {
        StringBuilder builder = new StringBuilder();
        serialize(indentationCount, builder);
        return builder.toString();
    }

    /**
     * Method serializes the object in BMM-ODIN syntax and appends it to an existing string buffer.
     *
     * @param indentationCount The indentation to use for the serialization.
     * @param builder          The StringBuilder to append the serialization output.
     * @return
     */
    @Override
    public void serialize(int indentationCount, StringBuilder builder) {
        int indentCount = indentationCount;
        buildTypeDeclaration(builder, indentCount, getPersistedModel().P_BMM_CONTAINER_TYPE, null);
        ++indentCount;
        indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_CONTAINER_TYPE, getPersistedModel().getContainerType()));
        if(getPersistedModel().getType() != null) {
            indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_TYPE, getPersistedModel().getType()));
        }
        if(getPersistedModel().getTypeDefinition() != null) {
            PersistedBmmType<?> typeDef = getPersistedModel().getTypeDefinition();
            BaseBmmSerializer serializer = BmmTypeSerializerFactory.createBmmTypeSerializer(typeDef);
            serializer.serialize(indentCount, builder);
        }
        --indentCount;
        indentByAndAppend(builder, indentCount, OdinSerializationUtils.closeOdinObject());
    }
}
