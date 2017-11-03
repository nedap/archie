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

import java.io.Serializable;

/**
 * Type reference to a single type i.e. not generic or container type.
 *
 * Created by cnanjo on 4/11/16.
 */
public class BmmSimpleType extends BmmType implements Serializable {

    private BmmClass baseClass;

    /**
     * Returns the target type; this converts to the first parameter in generic_parameters in BMM_GENERIC_TYPE.
     *
     * @return
     */
    public BmmClass getBaseClass() {
        return baseClass;
    }

    /**
     * Sets the target type; this converts to the first parameter in generic_parameters in BMM_GENERIC_TYPE.
     *
     * @return
     */
    public void setBaseClass(BmmClass baseClass) {
        this.baseClass = baseClass;
    }

    /**
     * Return base_class.type_name.
     *
     * @return
     */
    @Override
    public String getTypeName() {
        return this.baseClass.getTypeName();
    }

    @Override
    public String toDisplayString() {return getTypeName();}
}
