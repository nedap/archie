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
import org.openehr.bmm.persistence.BmmIncludeSpecification;
import org.openehr.odin.utils.OdinSerializationUtils;

import java.util.Collection;
import java.util.List;

public class BmmIncludeSpecificationSerializer extends BaseBmmSerializer {

    private Collection<BmmIncludeSpecification> includeSpecifications;

    /**
     * No-arg constructor.
     */
    public BmmIncludeSpecificationSerializer(Collection<BmmIncludeSpecification> includeSpecifications) {
        this.includeSpecifications = includeSpecifications;
    }

    public Collection<BmmIncludeSpecification> getIncludeSpecification() {
        return includeSpecifications;
    }

    public void setIncludeSpecification(List<BmmIncludeSpecification> includeSpecification) {
        this.includeSpecifications = includeSpecification;
    }

    /**
     * Method serializes the object in BMM-ODIN syntax and returns the serialization as a string.
     *
     * @param indentationCount The indentation to use for the serialization.
     * @return
     */
    @Override
    public String serialize(int indentationCount) {
        StringBuilder builder = new StringBuilder();
        serialize(indentationCount, builder);
        return builder.toString();
    }

    /**
     * Method serializes the object in BMM-ODIN syntax and appends it to an existing string buffer.
     *
     * @param indentationCount The indentation to use for the serialization.
     * @param builder          The StringBuilder to append the serialization output.
     * @return
     */
    @Override
    public void serialize(int indentationCount, StringBuilder builder) {
        if(includeSpecifications != null && includeSpecifications.size() > 0) {
            int indentCount = indentationCount;
            int index = 1;
            indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildObjectOpeningDeclaration(BmmConstants.BMM_PROPERTY_INCLUDES));
            for (BmmIncludeSpecification include : includeSpecifications) {
                ++indentCount;
                indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildKeyedObjectOpeningDeclaration("" + index));
                ++indentCount;
                indentByAndAppend(builder, indentCount, OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_ID, include.getId()));
                --indentCount;
                indentByAndAppend(builder, indentCount, OdinSerializationUtils.closeOdinObject());
                --indentCount;
                index++;
            }
            indentByAndAppend(builder, indentCount, OdinSerializationUtils.closeOdinObject());
        }
    }
}
