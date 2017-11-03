package org.openehr.bmm.persistence.serializer;

import org.junit.Before;
import org.junit.Test;
import org.openehr.bmm.BmmMultiplicityInterval;
import org.openehr.bmm.persistence.PersistedBmmContainerProperty;
import org.openehr.bmm.persistence.PersistedBmmContainerType;

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
 * Created by cnanjo on 1/26/17.
 */

public class BmmContainerPropertySerializerTest {

    private String testString1 = "[\"items\"] = (P_BMM_CONTAINER_PROPERTY) <\n" +
            "\tname = <\"items\">\n" +
            "\ttype_def = <\n" +
            "\t\tcontainer_type = <\"List\">\n" +
            "\t\ttype = <\"ITEM\">\n" +
            "\t>\n" +
            "\tcardinality = <|>=1|>\n" +
            "\tis_mandatory = <True>\n" +
            ">\n";
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void serializeAsString() throws Exception {
        BmmContainerPropertySerializer serializer = new BmmContainerPropertySerializer(buildProperty());
        assertEquals(testString1, serializer.serialize(0));
    }

    @Test
    public void serializeAsBuilder() throws Exception {

    }

    protected PersistedBmmContainerProperty buildProperty() {
        PersistedBmmContainerProperty property = new PersistedBmmContainerProperty("items", true);
        property.setCardinality(new BmmMultiplicityInterval(1, false, null, true));
        PersistedBmmContainerType typeDef = new PersistedBmmContainerType("List", "ITEM");
        property.setTypeDefinition(typeDef);
        return property;
    }

}