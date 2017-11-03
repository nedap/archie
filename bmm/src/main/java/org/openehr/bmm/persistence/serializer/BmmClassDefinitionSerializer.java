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

import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.persistence.PersistedBmmClass;
import org.openehr.bmm.persistence.PersistedBmmEnumerationInteger;
import org.openehr.bmm.persistence.PersistedBmmEnumerationString;
import org.openehr.odin.utils.OdinSerializationUtils;

import java.util.List;

public class BmmClassDefinitionSerializer extends BaseBmmSerializer<List<PersistedBmmClass>> implements Serialize {

    public BmmClassDefinitionSerializer(List<PersistedBmmClass> bmmClasses) {
        super(bmmClasses);
    }

    @Override
    public String serialize(int indentationCount) {
        StringBuilder builder = new StringBuilder();
        serialize(indentationCount, builder);
        return builder.toString();
    }

    @Override
    public void serialize(int indentationCount, StringBuilder builder) {
        int indentCount = indentationCount;
        indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildOdinObjectDeclaration("class_definitions"));
        indentCount++;
        final int i = indentCount;
        getPersistedModel().forEach(bmmClass -> {
            if(bmmClass instanceof PersistedBmmEnumerationInteger) {
                BmmEnumerationIntegerSerializer classSerializer = new BmmEnumerationIntegerSerializer((PersistedBmmEnumerationInteger)bmmClass);
                classSerializer.serialize(i, builder);
            } else if(bmmClass instanceof PersistedBmmEnumerationString) {
                BmmEnumerationStringSerializer classSerializer = new BmmEnumerationStringSerializer((PersistedBmmEnumerationString)bmmClass);
                classSerializer.serialize(i, builder);
            } else {
                BmmClassSerializer classSerializer = new BmmClassSerializer(bmmClass);
                classSerializer.serialize(i, builder);
            }
        });
        indentCount--;
        indentByAndAppend(builder, indentCount, OdinSerializationUtils.closeOdinObject());//Close package section
    }
}
