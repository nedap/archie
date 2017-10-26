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
import org.openehr.bmm.persistence.PersistedBmmContainerType;
import org.openehr.bmm.persistence.PersistedBmmGenericProperty;
import org.openehr.bmm.persistence.PersistedBmmGenericType;
import org.openehr.bmm.persistence.PersistedBmmType;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.OdinAttribute;
import org.openehr.odin.OdinObject;
import org.openehr.odin.StringObject;

import java.util.List;
import java.util.Map;

public class BmmGenericTypeDeserializer extends BmmTypeDeserializer {
    public PersistedBmmGenericType deserialize(CompositeOdinObject typeDefinition) {
        PersistedBmmGenericType persistedGenericType = new PersistedBmmGenericType();
        String rootType = typeDefinition.getAttribute(BmmConstants.BMM_PROPERTY_ROOT_TYPE).getStringValue();
        persistedGenericType.setRootType(rootType);
        OdinAttribute genericParametersAttribute = typeDefinition.getAttribute(BmmConstants.BMM_PROPERTY_GENERIC_PARAMETERS);
        List<String> genericParameters = null;
        if(genericParametersAttribute != null) {
            genericParameters = genericParametersAttribute.getChildrenAsStringList(true);
            persistedGenericType.setGenericParameters(genericParameters);
        }
        OdinAttribute genericParameterDefsAttribute = typeDefinition.getAttribute(BmmConstants.BMM_PROPERTY_GENERIC_PARAMETER_DEFS);
        if(genericParameterDefsAttribute != null) {
            Map<OdinObject,OdinObject> genericParameterMap = genericParameterDefsAttribute.getSoleCompositeObjectBody().getKeyedObjectMap();
            for(OdinObject genericParameterKey : genericParameterMap.keySet()) {
                OdinObject nestedDef = genericParameterMap.get(genericParameterKey);
                String name = ((StringObject)genericParameterKey).getValue();
                String type = nestedDef.getType();
                PersistedBmmType typeDef = BmmTypeDeserializer.deserializeType(type, (CompositeOdinObject)nestedDef);
                persistedGenericType.addGenericParameterDefinition(name, typeDef);
            }
        }
        return persistedGenericType;
    }
}
