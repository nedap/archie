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

import org.openehr.bmm.BmmConstants;
import org.openehr.bmm.persistence.PersistedBmmPackageContainer;
import org.openehr.odin.utils.OdinSerializationUtils;

/**
 *
 *  Created by cnanjo on 1/24/17.
 */
public class BmmPackageContainerSerializer extends BaseBmmSerializer<PersistedBmmPackageContainer> {

    public BmmPackageContainerSerializer() {
        super();
    }

    public BmmPackageContainerSerializer(PersistedBmmPackageContainer persistedBmmPackageContainer) {
        super(persistedBmmPackageContainer);
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
        indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildOdinObjectDeclaration(BmmConstants.BMM_PROPERTY_PACKAGES));
        indentCount++;
        final int i = indentCount;
        getPersistedModel().getPackages().values().forEach(pBmmPackage -> {
            BmmPackageSerializer packageSerializer = new BmmPackageSerializer(pBmmPackage);
            packageSerializer.serialize(i, builder);
        });
        indentCount--;
        indentByAndAppend(builder, indentCount, OdinSerializationUtils.closeOdinObject());//Close package section
    }
}
