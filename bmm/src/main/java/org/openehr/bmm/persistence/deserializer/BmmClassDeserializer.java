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
import org.openehr.bmm.persistence.PersistedBmmClass;
import org.openehr.bmm.persistence.PersistedBmmGenericParameter;
import org.openehr.bmm.persistence.PersistedBmmProperty;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.OdinAttribute;
import org.openehr.odin.OdinObject;

import java.util.List;

public class BmmClassDeserializer {
    public PersistedBmmClass deserialize(CompositeOdinObject classDefinition) {
        String className = classDefinition.getAttribute(BmmConstants.BMM_PROPERTY_NAME).getStringValue();
        String documentation = null;
        OdinAttribute documentationAttr = classDefinition.getAttribute(BmmConstants.BMM_DOCUMENTATION);
        if(documentationAttr != null) {
            documentation = documentationAttr.getStringValue();
        }
        PersistedBmmClass classDefType = new PersistedBmmClass(className);
        classDefType.setDocumentation(documentation);
        OdinAttribute ancestorDefinition = classDefinition.getAttribute(BmmConstants.BMM_PROPERTY_ANCESTORS);
        if (ancestorDefinition != null){
            List<String> ancestorList = ancestorDefinition.getChildrenAsStringList(true);
            classDefType.setAncestors(ancestorList);
        }
        OdinAttribute abstractAttributeDef = classDefinition.getAttribute(BmmConstants.BMM_PROPERTY_IS_ABSTRACT);
        if (abstractAttributeDef != null){
            classDefType.setAbstract(abstractAttributeDef.getBooleanValue());
        }
        OdinAttribute propertyDefinitions = classDefinition.getAttribute(BmmConstants.BMM_PROPERTY_PROPERTIES);
        if (propertyDefinitions != null) {
            List<OdinObject> properties = propertyDefinitions.getSoleCompositeObjectBody().getKeyedObjects();
            properties.forEach( propertyDef -> {
                PersistedBmmProperty<?> property = BmmPropertyDeserializer.deserializeProperty((CompositeOdinObject)propertyDef);
                classDefType.addProperty(property);
            });
        }
        OdinAttribute genericParameterDefinitions = classDefinition.getAttribute(BmmConstants.BMM_PROPERTY_GENERIC_PARAMETER_DEFS);
        if(genericParameterDefinitions != null) {
            BmmGenericParameterDeserializer genericParameterDeserializer = new BmmGenericParameterDeserializer();
            List<PersistedBmmGenericParameter> genericParameters = genericParameterDeserializer.deserialize(genericParameterDefinitions.getSoleCompositeObjectBody());
            classDefType.setGenericParameterDefinitions(genericParameters);
        }
        return classDefType;
    }
}
