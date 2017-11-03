package org.openehr.bmm.core;

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
import org.openehr.bmm.core.BmmGenericType;
import org.openehr.bmm.core.BmmProperty;
import org.openehr.odin.utils.OdinSerializationUtils;

import java.io.Serializable;

public class BmmGenericProperty extends BmmProperty implements Serializable {

    private BmmGenericType genericTypeDef;//fix to call it typeDef once fix has been made to this attribute in parent class;

    public BmmGenericType getGenericTypeDef() {
        return genericTypeDef;
    }

    public void setGenericTypeDef(BmmGenericType genericTypeDef) {
        this.genericTypeDef = genericTypeDef;
    }

//    public String toString() {
//        return serialize();
//    }
//
//    public String serialize() {
//        return serialize(0);
//    }

//    public String serialize(int indentation) {
//        StringBuilder builder = new StringBuilder();
//        int indentCount = indentation;
//        indentBy(builder, indentCount);
//        builder.append("[\"").append(getName()).append("\"] = (").append(BmmProperty.P_BMM_GENERIC_PROPERTY).append(") <").append("\n");
//        indentCount++;
//        indentBy(builder, indentCount);
//        builder.append(OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_NAME, getName()));
//        indentBy(builder, indentCount);
//        builder.append(BmmConstants.BMM_PROPERTY_TYPE_DEF).append(" = <").append("\n");
//        indentCount++;
//        indentBy(builder, indentCount);
////        builder.append(OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_ROOT_TYPE, getGenericTypeDef().getRootType()));
//        indentBy(builder, indentCount);
////        builder.append(OdinSerializationUtils.buildOdinListPropertyInitialization(BmmConstants.BMM_PROPERTY_GENERIC_PARAMETERS, getGenericTypeDef().getGenericParameters()));
//        indentCount--;
//        indentBy(builder, indentCount);
//        builder.append(">").append("\n");
//        if(getMandatory() != null && getMandatory()) {
//            indentBy(builder, indentCount);
//            builder.append(OdinSerializationUtils.buildOdinBooleanObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_IS_MANDATORY, true));
//        }
//        indentCount--;
//        indentBy(builder, indentCount);
//        builder.append(">").append("\n");
//        return builder.toString();
//    }
}
