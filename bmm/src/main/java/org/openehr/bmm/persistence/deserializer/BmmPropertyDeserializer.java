package org.openehr.bmm.persistence.deserializer;

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
import org.openehr.bmm.core.BmmContainerProperty;
import org.openehr.bmm.persistence.*;
import org.openehr.bmm.persistence.serializer.BmmGenericTypeSerializer;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.IntegerIntervalObject;
import org.openehr.odin.OdinAttribute;
import org.openehr.odin.OdinObject;

import java.util.List;


public abstract class BmmPropertyDeserializer {
    protected PersistedBmmProperty configureBmmPropertyFromOdinObject(PersistedBmmProperty property, CompositeOdinObject propertyDef) {

        // Handle is_mandatory
        OdinAttribute isMandatoryAttribute = propertyDef.getAttribute("is_mandatory");
        if (isMandatoryAttribute != null) {
            Boolean isMandatory = isMandatoryAttribute.getBooleanValue();
            property.setMandatory(isMandatory);
        }

        // Handle is_computed
        OdinAttribute isComputedAttribute = propertyDef.getAttribute("is_computed");
        if (isComputedAttribute != null) {
            Boolean isComputed = isComputedAttribute.getBooleanValue();
            property.setComputed(isComputed);
        }

        // Handle is_im_runtime
        OdinAttribute isImRuntimeAttribute = propertyDef.getAttribute("is_im_runtime");
        if (isImRuntimeAttribute != null) {
            Boolean isImRuntime = isImRuntimeAttribute.getBooleanValue();
            property.setImRuntime(isImRuntime);
        }

        // Handle is_im_infrastructure
        OdinAttribute isImInfrastructureAttribute = propertyDef.getAttribute("is_im_infrastructure");
        if (isImInfrastructureAttribute != null) {
            Boolean value = isImInfrastructureAttribute.getBooleanValue();
            property.setImInfrastructure(value);
        }

        return property;
    }

    /**
     * Method translate a BMM cast into the corresponding persisted property.
     *
     * @param odinPropertyDefinition
     * @return
     */
    public static PersistedBmmProperty deserializeProperty(CompositeOdinObject odinPropertyDefinition) {
        PersistedBmmProperty property = null;
        String kind = odinPropertyDefinition.getType();
        if (kind.equals(PersistedBmmSimpleType.P_BMM_SIMPLE_TYPE) || kind.equals(PersistedBmmSingleProperty.P_BMM_SINGLE_PROPERTY)) {
            BmmSinglePropertyDeserializer deserializer = new BmmSinglePropertyDeserializer();
            property = deserializer.deserialize(odinPropertyDefinition);
        } else if (kind.equals(PersistedBmmContainerType.P_BMM_CONTAINER_TYPE) || kind.equals(PersistedBmmContainerProperty.P_BMM_CONTAINER_PROPERTY)) {
            BmmContainerPropertyDeserializer deserializer = new BmmContainerPropertyDeserializer();
            property = deserializer.deserialize(odinPropertyDefinition);
        } else if (kind.equals(PersistedBmmOpenType.P_BMM_OPEN_TYPE) || kind.equals(PersistedBmmSinglePropertyOpen.P_BMM_SINGLE_PROPERTY_OPEN)) {
            BmmSinglePropertyOpenDeserializer deserializer = new BmmSinglePropertyOpenDeserializer();
            property = deserializer.deserialize(odinPropertyDefinition);
        } else if (kind.equals(PersistedBmmGenericType.P_BMM_GENERIC_TYPE) || kind.equals(PersistedBmmGenericProperty.P_BMM_GENERIC_PROPERTY)) {
            BmmGenericPropertyDeserializer deserializer = new BmmGenericPropertyDeserializer();
            property = deserializer.deserialize(odinPropertyDefinition);
        } else {
            throw new RuntimeException("Unrecognized property type " + kind);
        }
        return property;
    }
}
