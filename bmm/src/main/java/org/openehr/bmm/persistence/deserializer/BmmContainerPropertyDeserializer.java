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
import org.openehr.bmm.persistence.PersistedBmmContainerProperty;
import org.openehr.bmm.persistence.PersistedBmmContainerType;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.OdinAttribute;

public class BmmContainerPropertyDeserializer extends BmmPropertyDeserializer {
    public PersistedBmmContainerProperty deserialize(CompositeOdinObject propertyDefinition) {
        String documentation = null;
        OdinAttribute documentationAttr = propertyDefinition.getAttribute(BmmConstants.BMM_DOCUMENTATION);
        if(documentationAttr != null) {
            documentation = documentationAttr.getStringValue();
        }
        PersistedBmmContainerProperty property = new PersistedBmmContainerProperty();
        property.setDocumentation(documentation);
        String propertyName = propertyDefinition.getAttribute("name").getStringValue();
        property.setName(propertyName);
        OdinAttribute typeDefAttribute = propertyDefinition.getAttribute(BmmConstants.BMM_PROPERTY_TYPE_DEF);
        BmmContainerTypeDeserializer deserializer = new BmmContainerTypeDeserializer();
        PersistedBmmContainerType persistedContainerType = deserializer.deserialize(typeDefAttribute.getSoleCompositeObjectBody());
        ((PersistedBmmContainerProperty)property).setTypeDefinition(persistedContainerType);
        // Handle cardinality
        OdinAttribute cardinalityAttribute = propertyDefinition.getAttribute("cardinality");
        BmmCardinalityDeserializer cardinalityDeserializer = new BmmCardinalityDeserializer();
        BmmMultiplicityInterval cardinality = cardinalityDeserializer.deserialize(cardinalityAttribute);
        ((PersistedBmmContainerProperty)property).setCardinality(cardinality);
        configureBmmPropertyFromOdinObject(property, propertyDefinition);
        return property;
    }
}
