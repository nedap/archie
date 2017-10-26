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
import org.openehr.bmm.persistence.PersistedBmmGenericType;
import org.openehr.bmm.persistence.PersistedBmmSinglePropertyOpen;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.OdinAttribute;

public class BmmSinglePropertyOpenDeserializer extends BmmPropertyDeserializer {
    public PersistedBmmSinglePropertyOpen deserialize(CompositeOdinObject propertyDefinition) {
        String documentation = null;
        OdinAttribute documentationAttr = propertyDefinition.getAttribute(BmmConstants.BMM_DOCUMENTATION);
        if(documentationAttr != null) {
            documentation = documentationAttr.getStringValue();
        }
        String propertyName = propertyDefinition.getAttribute("name").getStringValue();
        OdinAttribute propertyType = propertyDefinition.getAttribute("type");
        PersistedBmmSinglePropertyOpen property = new PersistedBmmSinglePropertyOpen();
        property.setDocumentation(documentation);
        property.setName(propertyName);
        ((PersistedBmmSinglePropertyOpen)property).setType(propertyType.getStringValue());
        configureBmmPropertyFromOdinObject(property, propertyDefinition);
        return property;
    }
}
