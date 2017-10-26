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

import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmType;

/**
 * Type reference that specifies containers with one generic parameter.
 *
 * Created by cnanjo on 4/11/16.
 */
public class BmmContainerType extends BmmType {

    /**
     * The type of the container. This converts to the root_type in BMM_GENERIC_TYPE.
     */
    private BmmClass containerType;
    /**
     *
     */
    private BmmType baseType;

    /**
     * Returns the type of the container. This converts to the root_type in BMM_GENERIC_TYPE.
     *
     * @return
     */
    public BmmClass getContainerType() {
        return containerType;
    }

    /**
     * Sets the type of the container. This converts to the root_type in BMM_GENERIC_TYPE.
     *
     * @param containerType
     */
    public void setContainerType(BmmClass containerType) {
        this.containerType = containerType;
    }

    /**
     * Returns the target type; this converts to the first parameter in generic_parameters in BMM_GENERIC_TYPE.
     *
     * @return
     */
    public BmmType getBaseType() {
        return baseType;
    }

    /**
     * Sets the target type; this converts to the first parameter in generic_parameters in BMM_GENERIC_TYPE.
     *
     * @param baseType
     */
    public void setBaseType(BmmType baseType) {
        this.baseType = baseType;
    }

    /**
     * Return full type name, e.g. 'List<ELEMENT>'.
     *
     * @return
     */
    @Override
    public String getTypeName() {
        return containerType.getName() + "<" + baseType.getTypeName() + ">";
    }

    /**
     * Return base_type.conformance_type_name; e.g. if this type is 'List<ELEMENT>', return 'ELEMENT'.
     *
     * @return
     */
    public String getConformanceTypeName() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String toDisplayString() {return getTypeName();}
}
