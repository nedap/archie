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
import org.openehr.bmm.persistence.PersistedBmmType;
import org.openehr.odin.utils.OdinSerializationUtils;

public abstract class BmmTypeSerializer<T extends PersistedBmmType> extends BaseBmmSerializer<T> {

    private boolean omitCast = true;
    private boolean parameterBound = false;
    private boolean isSimpleStatement = false;

    /**
     * No-arg constructor.
     */
    public BmmTypeSerializer() {
    }

    /**
     *
     * @param isSimpleStatement
     */
    public BmmTypeSerializer(boolean isSimpleStatement) {
        this.omitCast = true;
        this.parameterBound = false;
        this.isSimpleStatement = true;
    }

    /**
     *
     * @param omitCast
     * @param parameterBound
     */
    public BmmTypeSerializer(boolean omitCast, boolean parameterBound) {
        this.omitCast = omitCast;
        this.parameterBound = parameterBound;
    }

    /**
     *
     * @param persistedModel
     * @param omitCast
     * @param parameterBound
     */
    public BmmTypeSerializer(T persistedModel, boolean omitCast, boolean parameterBound) {
        super(persistedModel);
        this.omitCast = omitCast;
        this.parameterBound = parameterBound;
    }

    /**
     *
     * @param persistedModel
     * @param isSimpleStatement
     */
    public BmmTypeSerializer(T persistedModel, boolean isSimpleStatement) {
        super(persistedModel);
        this.omitCast = true;
        this.parameterBound = false;
        this.isSimpleStatement = true;
    }

    /**
     * Flag to serializer to omit cast in type_def declaration.
     *
     * @return
     */
    public boolean isOmitCast() {
        return omitCast;
    }

    /**
     * Tells serializer to omit cast in type_def declaration.
     *
     * @param omitCast
     */
    public void setOmitCast(boolean omitCast) {
        this.omitCast = omitCast;
    }

    /**
     * True if this type declaration is explicitly bound to a generic parameter such as:
     * <pre>
     *     <code>
     *          ["T"] = (P_BMM_GENERIC_TYPE) <...>
     *     </code>
     * </pre>
     *
     * @return
     */
    public boolean isParameterBound() {
        return parameterBound;
    }

    /**
     * Sets flag indicating whether type is explicitly bound to a parameter such as S,T,U,...
     *
     * @param parameterBound
     */
    public void setParameterBound(boolean parameterBound) {
        this.parameterBound = parameterBound;
    }

    public boolean isSimpleStatement() {
        return isSimpleStatement;
    }

    public void setSimpleStatement(boolean simpleStatement) {
        isSimpleStatement = simpleStatement;
    }

    /**
     * TODO Add text
     *
     * @param builder
     * @param indentCount
     * @param type
     */
    protected void buildTypeDeclaration(StringBuilder builder, int indentCount, String type, String value) {
        if(!isSimpleStatement) {
            if (omitCast) {
                indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildObjectOpeningDeclaration(BmmConstants.BMM_PROPERTY_TYPE_DEF));
            } else {
                if (parameterBound) {
                    indentByAndAppend(builder, 0, OdinSerializationUtils.buildOpeningDeclarationWithCast(type));
                } else {
                    indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildObjectOpeningDeclarationWithCast(BmmConstants.BMM_PROPERTY_TYPE_DEF, type));
                }
            }
        } else {
            indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_TYPE, value));
        }
    }

    /**
     * TODO Add text
     *
     * @param builder
     * @param indentCount
     * @param type
     */
    protected void closeTypeDeclaration(StringBuilder builder, int indentCount) {
        if(!isSimpleStatement) {
            indentByAndAppend(builder, indentCount, OdinSerializationUtils.closeOdinObject());
        }
    }
}
