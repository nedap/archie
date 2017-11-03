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
import org.openehr.bmm.persistence.PersistedBmmSchema;
import org.openehr.bmm.persistence.PersistedBmmType;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.OdinAttribute;

public class BmmContainerTypeDeserializer extends BmmTypeDeserializer {
    public PersistedBmmContainerType deserialize(CompositeOdinObject typeDefAttribute) {
        PersistedBmmContainerType persistedContainerType = new PersistedBmmContainerType();
        String containerType = typeDefAttribute.getAttribute(BmmConstants.BMM_PROPERTY_CONTAINER_TYPE).getStringValue();
        persistedContainerType.setContainerType(containerType);
        OdinAttribute nestedTypeDefAttribute = typeDefAttribute.getAttribute(BmmConstants.BMM_PROPERTY_TYPE_DEF);
        if(nestedTypeDefAttribute != null) {
            CompositeOdinObject body = nestedTypeDefAttribute.getSoleCompositeObjectBody();
            String type = body.getType();
            PersistedBmmType bmmType = BmmTypeDeserializer.deserializeType(type, body);
            persistedContainerType.setTypeDefinition(bmmType);
        } else {
            String itemType = typeDefAttribute.getAttribute("type").getStringValue();
            persistedContainerType.setType(itemType);
        }
        return persistedContainerType;
    }
}
