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
import org.openehr.bmm.persistence.PersistedBmmGenericType;
import org.openehr.bmm.persistence.PersistedBmmType;
import org.openehr.odin.utils.OdinSerializationUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Deprecated
public class BmmGenericTypeSerializer extends BmmTypeSerializer<PersistedBmmGenericType> {

    /**
     * No-arg constructor.
     */
    public BmmGenericTypeSerializer() {
        super();
    }

    /**
     * Constructor setting the model to be serialized.
     *
     * @param persistedModel
     */
    public BmmGenericTypeSerializer(PersistedBmmGenericType persistedModel) {
        super(persistedModel, false, false);
    }

    public BmmGenericTypeSerializer(PersistedBmmGenericType persistedModel, boolean omitCast, boolean parameterBound) {
        super(persistedModel, omitCast, parameterBound);
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
        buildTypeDeclaration(builder, indentCount, getPersistedModel().P_BMM_GENERIC_TYPE, null);
        ++indentCount;
        indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_ROOT_TYPE, getPersistedModel().getRootType()));
        if(getPersistedModel().getGenericParameters() != null && getPersistedModel().getGenericParameters().size() > 0) {
            final boolean isParameterized = isParameterizedList(getPersistedModel().getGenericParameters().keySet());
            if(isParameterized) {
                indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildObjectOpeningDeclaration(BmmConstants.BMM_PROPERTY_GENERIC_PARAMETERS));
                final int tabs = ++indentCount;
                getPersistedModel().getGenericParameters().forEach((K,V) -> {
                    indentByAndAppend(builder, tabs, OdinSerializationUtils.buildKeyedStringObjectdeclaration(K,V));
                });
                --indentCount;
                indentByAndAppend(builder, indentCount, OdinSerializationUtils.closeOdinObject());
            } else {
                indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildOdinStringListPropertyInitialization(BmmConstants.BMM_PROPERTY_GENERIC_PARAMETERS, new ArrayList(getPersistedModel().getGenericParameters().values())));
            }
        } else if(getPersistedModel().getGenericParameterDefinitions() != null && getPersistedModel().getGenericParameterDefinitions().size() > 0) {
            indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildObjectOpeningDeclaration(BmmConstants.BMM_PROPERTY_GENERIC_PARAMETER_DEFS));
            final boolean isParameterized = isParameterizedList(getPersistedModel().getGenericParameterDefinitions().keySet());
            final int tabs = ++indentCount;
            if (isParameterized) {
                getPersistedModel().getGenericParameterDefinitions().forEach((K, V) -> {
                    BmmTypeSerializer<?> serializer = BmmTypeSerializerFactory.createBmmTypeSerializer(V);
                    serializer.setOmitCast(false);
                    serializer.setParameterBound(true);
                    indentByAndAppend(builder, tabs, new StringBuilder().append("[\"").append(K).append("\"]").append(" = ").append(serializer.serialize(tabs)).toString());
                });
            } else {
                getPersistedModel().getGenericParameterDefinitions().values().forEach(typeDef -> {
                    BmmTypeSerializer<?> serializer = BmmTypeSerializerFactory.createBmmTypeSerializer(typeDef);
                    serializer.setOmitCast(false);
                    serializer.setParameterBound(false);
                    serializer.serialize(tabs, builder);
                });
            }
            --indentCount;
            indentByAndAppend(builder, indentCount, OdinSerializationUtils.closeOdinObject());
        }
        --indentCount;
        indentByAndAppend(builder, indentCount, OdinSerializationUtils.closeOdinObject());
    }

    protected boolean isParameterizedList(Collection<String> keys) {
        boolean isParameterized = false;
        for(String key : keys) {
            try {
                java.lang.Integer.parseInt(key);
            } catch(NumberFormatException nfe) {
                isParameterized = true;
            }
        }
        return isParameterized;
    }
}
