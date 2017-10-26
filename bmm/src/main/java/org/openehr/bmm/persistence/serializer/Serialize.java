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

import org.openehr.bmm.persistence.PersistedBmmModelElement;

public interface Serialize {

    /**
     * Method serializes the object in BMM-ODIN syntax and returns the serialization as a string.
     * @param modelElement The model to serialize.
     * @param indentationCount The indentation to use for the serialization.
     * @return
     */
    public String serialize(int indentationCount);

    /**
     * Method serializes the object in BMM-ODIN syntax and appends it to an existing string buffer.
     * @param modelElement The model to serialize.
     * @param indentationCount The indentation to use for the serialization.
     * @param builder The StringBuilder to append the serialization output.
     * @return
     */
    public void serialize(int indentationCount, StringBuilder builder);
}
