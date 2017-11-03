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
 */
package org.openehr.bmm.persistence.serializer;

import org.junit.Test;
import org.openehr.bmm.BmmConstants;
import org.openehr.bmm.persistence.PersistedBmmContainerProperty;
import org.openehr.bmm.persistence.PersistedBmmGenericParameter;
import org.openehr.odin.utils.OdinSerializationUtils;

import static org.junit.Assert.*;

/**
 *
 *
 * Created by cnanjo on 1/31/17.
 */
public class BmmGenericParameterSerializerTest {

    public static final String testString1 = "[\"T\"] = <\n" +
            "\tname = <\"T\">\n" +
            "\tconforms_to_type = <\"Ordered\">\n" +
            ">\n";

    @Test
    public void serializeAsString() throws Exception {;
        BmmGenericParameterSerializer serializer = new BmmGenericParameterSerializer(buildParameter());
        assertEquals(testString1, serializer.serialize(0));
    }

    @Test
    public void serializeAsBuilder() throws Exception {
        StringBuilder builder = new StringBuilder();
        BmmGenericParameterSerializer serializer = new BmmGenericParameterSerializer(buildParameter());
        serializer.serialize(0, builder);
        assertEquals(testString1, builder.toString());

    }

    public PersistedBmmGenericParameter buildParameter() {
        PersistedBmmGenericParameter parameter = new PersistedBmmGenericParameter("T", "Ordered");
        return parameter;
    }
}