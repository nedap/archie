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
import org.openehr.bmm.BmmMultiplicityInterval;
import org.openehr.bmm.core.*;
import org.openehr.odin.utils.OdinSerializationUtils;

import java.io.Serializable;

/**
 * Persistent form of BMM_CONTAINER_PROPERTY.
 *
 * Created by cnanjo on 4/11/16.
 */
public class PersistedBmmContainerProperty extends PersistedBmmProperty<PersistedBmmContainerType> implements Serializable {

    public static final String P_BMM_CONTAINER_PROPERTY = "P_BMM_CONTAINER_PROPERTY";

    /**
     * Cardinality of this property in its class.
     */
    private BmmMultiplicityInterval cardinality;

    /**
     * No-arg constructor
     */
    public PersistedBmmContainerProperty() {
        super();
    }

    /**
     * Initializes the property with its name.
     *
     * @param name
     */
    public PersistedBmmContainerProperty(String name) {
        super(name);
    }

    /**
     * Creates a new named property that is mandatory.
     *
     * @param name
     * @param isMandatory
     */
    public PersistedBmmContainerProperty(String name, boolean isMandatory) {
        super(name, isMandatory);
    }

    /**
     * Returns the cardinality of this container.
     *
     * @return
     */
    public BmmMultiplicityInterval getCardinality() {
        return cardinality;
    }

    /**
     * Sets the cardinality of this container.
     *
     * @param cardinality
     */
    public void setCardinality(BmmMultiplicityInterval cardinality) {
        this.cardinality = cardinality;
    }

    /**
     * Create bmm_property_definition.
     * @param bmmSchema
     * @param classDefinition
     */
    @Override
    public void createBmmProperty(BmmModel bmmSchema, BmmClass classDefinition) {
        if(getTypeDefinition() != null) {
            getTypeDefinition().createBmmType(bmmSchema, classDefinition);
            if(getTypeDefinition().getBmmType() != null) {
                setBmmProperty(new BmmContainerProperty(getName(), getTypeDefinition().getBmmType()));
                getBmmProperty().setDocumentation(getDocumentation());
                getBmmProperty().setMandatory(getMandatory());
                getBmmProperty().setComputed(getComputed());
                getBmmProperty().setImInfrastructure(getImInfrastructure());
                getBmmProperty().setImRuntime(getImRuntime());
            }
        }
        if(getBmmProperty() != null) {
            BmmContainerProperty container = (BmmContainerProperty)getBmmProperty();
            if(cardinality != null) {
                container.setCardinality(cardinality.convertToMultiplicityInterval());
            }
        }
    }
}
