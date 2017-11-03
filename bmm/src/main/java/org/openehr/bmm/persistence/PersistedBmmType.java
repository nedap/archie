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
import org.openehr.bmm.core.BmmType;
import org.openehr.bmm.core.BmmTypeElement;

import java.io.Serializable;
import java.util.List;

/**
 * Persistent form of BMM_TYPE.
 *
 * Created by cnanjo on 1/25/17.
 */
public abstract class PersistedBmmType<T extends BmmType> extends PersistedBmmModelElement implements Serializable {

    /**
     * Result of create_bmm_type() call.
     */
    private T bmmType;

    /**
     * Returns the underlying BmmType definition for this persisted type.
     *
     * @return
     */
    public T getBmmType() {
        return bmmType;
    }

    /**
     * Sets the underlying BmmType definition for this persisted type.
     *
     * @param bmmType
     */
    public void setBmmType(T bmmType) {
        this.bmmType = bmmType;
    }

    /**
     * Create appropriate BMM_ object; defined in descendants.
     *
     * @param schema
     * @param classDefinition
     */
    public abstract void createBmmType(BmmModel schema, BmmClass classDefinition);

    /**
     * Formal name of the type for display.
     *
     * @return
     */
    public abstract String asTypeString();

    /**
     * Flattened list of type names making up full type.
     *
     * @return
     */
    public abstract List<String> flattenedTypeList();

}
