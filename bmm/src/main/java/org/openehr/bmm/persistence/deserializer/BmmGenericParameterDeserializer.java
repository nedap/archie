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
import org.openehr.bmm.persistence.PersistedBmmGenericParameter;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.OdinObject;

import java.util.ArrayList;
import java.util.List;

public class BmmGenericParameterDeserializer {
    public List<PersistedBmmGenericParameter> deserialize(CompositeOdinObject parameterListDefinition) {
        List<PersistedBmmGenericParameter> parameters = new ArrayList<>();
        List<OdinObject> genericParameters = parameterListDefinition.getKeyedObjects();
        genericParameters.forEach(I -> {
            CompositeOdinObject paramDef = ((CompositeOdinObject) I);
            String name = paramDef.getAttribute(BmmConstants.BMM_PROPERTY_NAME).getStringValue();
            String conformance = null;
            if(paramDef.getAttribute(BmmConstants.BMM_CONFORMS_TO) != null) {
                conformance = paramDef.getAttribute(BmmConstants.BMM_CONFORMS_TO).getStringValue();
            }
            PersistedBmmGenericParameter parameter = new PersistedBmmGenericParameter(name, conformance);
            parameters.add(parameter);
        });
        return parameters;
    }
}
