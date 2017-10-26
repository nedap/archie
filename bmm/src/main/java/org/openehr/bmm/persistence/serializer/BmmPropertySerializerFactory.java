package org.openehr.bmm.persistence.serializer;

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

import org.openehr.bmm.core.BmmContainerProperty;
import org.openehr.bmm.core.BmmGenericProperty;
import org.openehr.bmm.persistence.*;

public class BmmPropertySerializerFactory {
    public static BaseBmmSerializer<?> createBmmPropertySerializer(PersistedBmmProperty<?> property) {
        if(property instanceof PersistedBmmSinglePropertyOpen) {
            return new BmmSinglePropertyOpenSerializer((PersistedBmmSinglePropertyOpen)property);
        } else if(property instanceof PersistedBmmContainerProperty) {
            return new BmmContainerPropertySerializer((PersistedBmmContainerProperty)property);
        } else if(property instanceof PersistedBmmGenericProperty) {
            return new BmmGenericPropertySerializer((PersistedBmmGenericProperty)property);
        } else if(property instanceof PersistedBmmSingleProperty) {
            return new BmmSinglePropertySerializer((PersistedBmmSingleProperty)property);
        } else {
            throw new IllegalArgumentException("Unknown property type: " + property.getClass());
        }
    }
}
