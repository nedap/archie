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

import java.io.Serializable;

/**
 * Schema inclusion structure
 *
 * Created by cnanjo on 10/12/16.
 */
public class BmmIncludeSpecification implements Serializable {

    /**
     * Full identifier of the included schema, e.g. "openehr_primitive_types_1.0.2".
     */
    private String id;
    /**
     * Namespace prepended to namespace in included schema, under which included types will be known in this schema.
     */
    private String namespace;

    public BmmIncludeSpecification() {
    }

    public BmmIncludeSpecification(String id) {
        this.id = id;
    }

    public BmmIncludeSpecification(String id, String namespace) {
        this.id = id;
        this.namespace = namespace;
    }

    /**
     * Method returns the full identifier of the included schema, e.g. "openehr_primitive_types_1.0.2".
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Method sets the full identifier of the included schema, e.g. "openehr_primitive_types_1.0.2".
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method returns the namespace prepended to namespace in included schema, under which included types will be known in this schema.
     * @return
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Method sets the namespace prepended to namespace in included schema, under which included types will be known in this schema.
     * @param namespace
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
