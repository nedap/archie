package org.openehr.odin;

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

import java.io.Serializable;

/**
 * Created by cnanjo on 4/8/16.
 */
public class PrimitiveObject<T> extends OdinObject implements Serializable {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isStringType() {
        return value instanceof java.lang.String;
    }

    public boolean isBooleanType() {
        return value instanceof java.lang.Boolean;
    }

    public boolean isIntegerType() {
        return value instanceof java.lang.Integer;
    }

    public String getType() {
        return this.value.getClass().getTypeName();
    }
}
