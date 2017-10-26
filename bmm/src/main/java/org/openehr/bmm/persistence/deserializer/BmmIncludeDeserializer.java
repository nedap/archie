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
import org.openehr.bmm.persistence.BmmIncludeSpecification;
import org.openehr.bmm.persistence.PersistedBmmGenericParameter;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.OdinAttribute;
import org.openehr.odin.OdinObject;
import org.openehr.odin.StringObject;

import java.util.ArrayList;
import java.util.List;

public class BmmIncludeDeserializer {
    public List<BmmIncludeSpecification> deserialize(CompositeOdinObject includeBlock) {
        List<BmmIncludeSpecification> includes = new ArrayList<>();
        OdinAttribute includesAttribute = includeBlock.getAttribute(BmmConstants.BMM_PROPERTY_INCLUDES);
        if(includesAttribute != null) {
            List<OdinObject> includeEntries = includesAttribute.getSoleCompositeObjectBody().getKeyedObjects();
            includeEntries.forEach(includeEntry -> {
                OdinAttribute includeItem = ((CompositeOdinObject)includeEntry).getAttribute(BmmConstants.BMM_PROPERTY_ID);
                if(includeItem != null) {
                    BmmIncludeSpecification includeSpec = new BmmIncludeSpecification(includeItem.getStringValue());
                    includes.add(includeSpec);
                }
            });
        }
        return includes;
    }
}
