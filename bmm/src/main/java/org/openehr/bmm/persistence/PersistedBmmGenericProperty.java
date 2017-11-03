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
import org.openehr.bmm.core.*;
import org.openehr.odin.utils.OdinSerializationUtils;

import java.io.Serializable;

/**
 * Persistent form of BMM_GENERIC_PROPERTY.
 *
 * Created by cnanjo on 11/5/16.
 */
public class PersistedBmmGenericProperty extends PersistedBmmProperty<PersistedBmmGenericType> implements Serializable {

    public static final String P_BMM_GENERIC_PROPERTY = "P_BMM_GENERIC_PROPERTY";

    /**
     * No-arg constructor
     */
    public PersistedBmmGenericProperty() {
        super();
    }

    /**
     * Initializes a new named generic property
     * @param name The name of the property.
     */
    public PersistedBmmGenericProperty(String name) {
        super(name);
    }

    /**
     * Initializes a new named generic property.
     *
     * @param name The name of the property.
     * @param isMandatory True if the property is mandatory (1..1)
     */
    public PersistedBmmGenericProperty(String name, boolean isMandatory) {
        super(name, isMandatory);
    }
}
