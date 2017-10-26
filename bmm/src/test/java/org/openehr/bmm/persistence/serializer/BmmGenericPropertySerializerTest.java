package org.openehr.bmm.persistence.serializer;

import org.junit.Test;
import org.openehr.bmm.persistence.PersistedBmmGenericProperty;
import org.openehr.bmm.persistence.PersistedBmmGenericType;

import java.util.ArrayList;
import java.util.List;

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
 * Created by cnanjo on 1/27/17.
 */

public class BmmGenericPropertySerializerTest {

    private String testString1 = "[\"qty_interval_attr\"] = (P_BMM_GENERIC_PROPERTY) <\n" +
            "\tname = <\"qty_interval_attr\">\n" +
            "\ttype_def = <\n" +
            "\t\troot_type = <\"DV_INTERVAL\">\n" +
            "\t\tgeneric_parameters = <\"DV_QUANTITY\",...>\n" +
            "\t>\n" +
            ">\n";
    @Test
    public void serializeAsString1() throws Exception {
        BmmGenericPropertySerializer serializer = new BmmGenericPropertySerializer(buildProperty());
        assertEquals(testString1, serializer.serialize(0));
    }

    @Test
    public void serializeAsBuilder1() throws Exception {

    }

    protected PersistedBmmGenericProperty buildProperty() {
        PersistedBmmGenericProperty property = new PersistedBmmGenericProperty("qty_interval_attr", false);
        PersistedBmmGenericType type = new PersistedBmmGenericType();
        type.setRootType("DV_INTERVAL");
        type.addGenericParameter("DV_QUANTITY");
        property.setTypeDefinition(type);
        return property;
    }

}