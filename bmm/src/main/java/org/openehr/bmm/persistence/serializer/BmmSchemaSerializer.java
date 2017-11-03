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
import org.openehr.bmm.persistence.PersistedBmmPackageContainer;
import org.openehr.bmm.persistence.PersistedBmmSchema;
import org.openehr.odin.utils.OdinSerializationUtils;

import java.util.*;

public class BmmSchemaSerializer {

    private PersistedBmmSchema schema;

    public BmmSchemaSerializer(PersistedBmmSchema schema) {
        this.schema = schema;
    }

    public PersistedBmmSchema getSchema() {
        return schema;
    }

    public void setBmmSchema(PersistedBmmSchema schema) {
        this.schema = schema;
    }

    public String serialize() {
        StringBuilder builder = new StringBuilder();
        serializeModelHeader(builder);
        BmmIncludeSpecificationSerializer includeSerializer = new BmmIncludeSpecificationSerializer(schema.getIncludes().values());
        includeSerializer.serialize(0, builder);
        BmmPackageContainerSerializer packageContainerSerializer = new BmmPackageContainerSerializer(schema);
        packageContainerSerializer.serialize(0, builder);
        if(schema.getClassDefinitions() != null && schema.getClassDefinitions().size() > 0) {
            BmmClassDefinitionSerializer classDefSerializer = new BmmClassDefinitionSerializer(schema.getClassDefinitions());
            classDefSerializer.serialize(0, builder);
        }
        if(schema.getPrimitives() != null && schema.getPrimitives().size() > 0) {
            BmmPrimitiveSerializer primitiveDefSerializer = new BmmPrimitiveSerializer(schema.getPrimitives());
            primitiveDefSerializer.serialize(0, builder);
        }

        return builder.toString();
    }

    private void serializeModelHeader(StringBuilder builder) {
        builder.append(OdinSerializationUtils.buildOdinComment("Basic Metamodel Syntax Version"));
        builder.append(OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_VERSION, BmmConstants.BMM_VERSION));
        builder.append("\n");
        builder.append(OdinSerializationUtils.buildOdinCommentBlock("schema identification", "(schema_id computed as <rm_publisher>_<schema_name>_<rm_release>)"));
        builder.append(OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_RM_PUBLISHER, schema.getRmPublisher()));
        builder.append(OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_SCHEMA_NAME, schema.getSchemaName()));
        builder.append(OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_RM_RELEASE, schema.getRmRelease()));
        builder.append("\n");
        builder.append(OdinSerializationUtils.buildOdinCommentBlock("schema documentation"));
        builder.append(OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_SCHEMA_REVISION, schema.getSchemaRevision()));
        builder.append(OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_SCHEMA_LIFECYCLE_STATE, schema.getSchemaLifecycleState()));
        builder.append(OdinSerializationUtils.buildOdinStringObjectPropertyInitialization(BmmConstants.BMM_PROPERTY_SCHEMA_DESCRIPTION, schema.getSchemaDescription()));
        builder.append("\n");
        builder.append("\n");
        builder.append(OdinSerializationUtils.buildOdinCommentBlock("archetyping"));
        builder.append(OdinSerializationUtils.buildOdinStringListPropertyInitialization(BmmConstants.BMM_PROPERTY_RM_CLOSURE_PACKAGES, schema.getArchetypeRmClosurePackages()));
    }
}
