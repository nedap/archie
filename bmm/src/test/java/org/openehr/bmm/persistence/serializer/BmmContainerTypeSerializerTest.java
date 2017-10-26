package org.openehr.bmm.persistence.serializer;

import org.junit.Test;
import org.openehr.bmm.persistence.PersistedBmmContainerType;
import org.openehr.bmm.persistence.PersistedBmmGenericType;

import static org.junit.Assert.*;

/**
 * Copyright 2017 Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by cnanjo on 2/3/17.
 */

public class BmmContainerTypeSerializerTest {

    private final String testString1 = "type_def = <\n" +
            "\tcontainer_type = <\"List\">\n" +
            "\ttype_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\t\troot_type = <\"ResourceReference\">\n" +
            "\t\tgeneric_parameters = <\"Party\",...>\n" +
            "\t>\n" +
            ">\n";

    @Test
    public void serializeAsString3() throws Exception {
        BmmContainerTypeSerializer serializer = new BmmContainerTypeSerializer(buildContainerType());
        assertEquals(testString1, serializer.serialize(0));
    }

    @Test
    public void serializeAsBuilder() throws Exception {
        StringBuilder builder = new StringBuilder();
        BmmContainerTypeSerializer serializer = new BmmContainerTypeSerializer(buildContainerType());
        serializer.serialize(0, builder);
        assertEquals(testString1, builder.toString());
    }

    protected PersistedBmmContainerType buildContainerType() {
        PersistedBmmContainerType containerType = new PersistedBmmContainerType();
        containerType.setContainerType("List");
        PersistedBmmGenericType genericType = new PersistedBmmGenericType();
        genericType.setRootType("ResourceReference");
        genericType.addGenericParameter("Party");
        containerType.setTypeDefinition(genericType);
        return containerType;
    }
}