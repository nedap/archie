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
import org.openehr.bmm.core.BmmGenericParameter;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmTypeElement;

import java.io.Serializable;

/**
 * Definition of a generic parameter in a class definition of a generic type.
 *
 * Created by cnanjo on 4/11/16.
 */
public class PersistedBmmGenericParameter extends PersistedBmmModelElement implements Serializable {

    public PersistedBmmGenericParameter() {
        super();
    }

    public PersistedBmmGenericParameter(String name) {
        this();
        this.name = name;
    }

    public PersistedBmmGenericParameter(String name, String conformsToType) {
        this(name);
        this.conformsToType = conformsToType;
    }

    /**
     * Name of the parameter, e.g. 'T' etc. Persisted attribute.
     */
    private String name;
    /**
     * Optional conformance constraint - the name of a type to which a concrete substitution of this generic
     * parameter must conform.
     */
    private String conformsToType;
    /**
     * BMM_GENERIC_PARAMETER created by create_bmm_generic_parameter.
     */
    private BmmGenericParameter bmmGenericParameter;

    /**
     * Returns the name of the parameter, e.g. 'T' etc.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the parameter, e.g. 'T' etc.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the optional conformance constraint - the name of a type to which a concrete substitution of this generic
     * parameter must conform.
     * @return
     */
    public String getConformsToType() {
        return conformsToType;
    }

    /**
     * Sets the optional conformance constraint - the name of a type to which a concrete substitution of this generic
     * parameter must conform.
     *
     * @param conformsToType
     */
    public void setConformsToType(String conformsToType) {
        this.conformsToType = conformsToType;
    }

    /**
     * Returns the BmmGenericParameter constructed from its persisted form.
     * @return
     */
    public BmmGenericParameter getBmmGenericParameter() {
        return bmmGenericParameter;
    }

    /**
     * Returns the BmmGenericParameter constructed from its persisted form.
     *
     * @param bmmGenericParameter
     */
    public void setBmmGenericParameter(BmmGenericParameter bmmGenericParameter) {
        this.bmmGenericParameter = bmmGenericParameter;
    }

    /**
     * Creates a new BmmGenericParameter from its persisted state.
     *
     * @param bmmSchema
     */
    public void createBmmGenericParameter(BmmModel bmmSchema) {
        if(conformsToType != null && bmmSchema.getClassDefinition(conformsToType) != null) {
            BmmClass conformsToTypeClass = bmmSchema.getClassDefinition(conformsToType);
            bmmGenericParameter = new BmmGenericParameter();
            bmmGenericParameter.setName(name);
            bmmGenericParameter.setDocumentation(getDocumentation());
            bmmGenericParameter.setConformsToType(conformsToTypeClass);
        } else {
            throw new RuntimeException("No conform_to_type found. If type is completely open, specify parent type such as 'Any'");
        }
    }
}

