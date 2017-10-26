package org.openehr.bmm.persistence.deserializer;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017 Cognitive Medical Systems
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
import org.openehr.bmm.persistence.PersistedBmmEnumerationInteger;
import org.openehr.odin.CompositeOdinObject;

import java.util.List;

public class BmmEnumerationIntegerDeserializer {
    public PersistedBmmEnumerationInteger deserialize(CompositeOdinObject integerEnumerationDefinition) {
        PersistedBmmEnumerationInteger integerEnumeration = new PersistedBmmEnumerationInteger();
        String name = integerEnumerationDefinition.getAttribute(BmmConstants.BMM_PROPERTY_NAME).getStringValue();
        integerEnumeration.setName(name);
        if(integerEnumerationDefinition.getAttribute(BmmConstants.BMM_PROPERTY_ANCESTORS) != null) {
            List<String> ancestors = integerEnumerationDefinition.getAttribute(BmmConstants.BMM_PROPERTY_ANCESTORS).getChildrenAsStringList(true);
            integerEnumeration.setAncestors(ancestors);
        }
        List<String> itemNames = integerEnumerationDefinition.getAttribute(BmmConstants.BMM_PROPERTY_ITEM_NAMES).getChildrenAsStringList(true);
        integerEnumeration.setItemNames(itemNames);
        if(integerEnumerationDefinition.getAttribute(BmmConstants.BMM_PROPERTY_ITEM_VALUES) != null) {
            List<Integer> itemValues = integerEnumerationDefinition.getAttribute(BmmConstants.BMM_PROPERTY_ITEM_VALUES).getChildrenAsIntegerList(true);
            integerEnumeration.setItemValues(itemValues);
        }
        return integerEnumeration;
    }
}
