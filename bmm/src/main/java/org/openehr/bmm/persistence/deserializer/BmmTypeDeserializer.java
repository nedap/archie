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

import org.openehr.bmm.BmmMultiplicityInterval;
import org.openehr.bmm.persistence.*;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.OdinAttribute;


public abstract class BmmTypeDeserializer {

    public static PersistedBmmType deserializeType(String type, CompositeOdinObject typeDefinition) {
        if (type.equals(PersistedBmmSimpleType.P_BMM_SIMPLE_TYPE) || type.equals(PersistedBmmSingleProperty.P_BMM_SINGLE_PROPERTY)) {
            BmmSimpleTypeDeserializer deserializer = new BmmSimpleTypeDeserializer();
            return deserializer.deserialize(typeDefinition);
        } else if (type.equals(PersistedBmmContainerType.P_BMM_CONTAINER_TYPE) || type.equals(PersistedBmmContainerProperty.P_BMM_CONTAINER_PROPERTY)) {
            BmmContainerTypeDeserializer deserializer = new BmmContainerTypeDeserializer();
            return deserializer.deserialize(typeDefinition);
        } else if (type.equals(PersistedBmmOpenType.P_BMM_OPEN_TYPE) || type.equals(PersistedBmmSinglePropertyOpen.P_BMM_SINGLE_PROPERTY_OPEN)) {
            BmmOpenTypeDeserializer deserializer = new BmmOpenTypeDeserializer();
            return deserializer.deserialize(typeDefinition);
        } else if (type.equals(PersistedBmmGenericType.P_BMM_GENERIC_TYPE) || type.equals(PersistedBmmGenericProperty.P_BMM_GENERIC_PROPERTY)) {
            BmmGenericTypeDeserializer deserializer = new BmmGenericTypeDeserializer();
            return deserializer.deserialize(typeDefinition);
        } else {
            throw new RuntimeException("Unrecognized property type " + type);
        }
    }

}
