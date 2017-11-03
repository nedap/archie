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

import org.openehr.bmm.persistence.PersistedBmmModelElement;
import org.openehr.odin.utils.OdinSerializationUtils;

public abstract class BaseBmmSerializer<T> implements Serialize {

    /**
     * The model to serialize
     */
    private T persistedModel;

    /**
     * No-arg constructor.
     */
    public BaseBmmSerializer() {
        super();
    }

    /**
     * Constructor setting the model to be serialized.
     *
     * @param persistedModel
     */
    public BaseBmmSerializer(T persistedModel) {
        this();
        this.persistedModel = persistedModel;
    }

    /**
     * Returns the model to serialize.
     *
     * @return
     */
    public T getPersistedModel() {
        return persistedModel;
    }

    /**
     * Sets the model to serialize.
     *
     * @param persistedModel
     */
    public void setPersistedModel(T persistedModel) {
        this.persistedModel = persistedModel;
    }

    /**
     * Convenience method to help readability when serializing BMM model elements.
     * Indents a statement by the number of tabs specified.
     * Appends requested number of tabs to StringBuilder argument.
     *
     * @param builder The string builder where the tabbed indentation is to be appended
     * @param indentCount The number of tabs to indent
     */
    protected void indentBy(StringBuilder builder, int indentCount) {
        builder.append(OdinSerializationUtils.indentByTabCount(indentCount));
    }

    /**
     * Convenience method to help readability when serializing BMM model elements.
     * Convenience method that tabs (indents) a statement by the specified indentCount and then appends the content
     * of that statement.
     *
     * @param builder
     * @param indentCount
     * @param content
     */
    protected void indentByAndAppend(StringBuilder builder, int indentCount, String content) {
        indentBy(builder, indentCount);
        builder.append(content);
    }
}
