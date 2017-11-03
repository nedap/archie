package org.openehr.bmm.persistence.serializer;

import org.junit.Test;
import org.openehr.bmm.core.BmmEnumerationInteger;
import org.openehr.bmm.persistence.PersistedBmmEnumerationInteger;

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

public class BmmEnumerationIntegerSerializerTest {

    private String testString1 = "[\"PROPORTION_KIND_2\"] = (P_BMM_ENUMERATION_INTEGER) <\n" +
            "\tname = <\"PROPORTION_KIND_2\">\n" +
            "\tancestors = <\"Integer\",...>\n" +
            "\titem_names = <\"pk_ratio\", \"pk_unitary\", \"pk_percent\", \"pk_fraction\", \"pk_integer_fraction\">\n" +
            "\titem_values = <0, 1001, 1002, 1003>\n" +
            ">\n";

    @Test
    public void serializeAsString() throws Exception {
        BmmEnumerationIntegerSerializer serializer = new BmmEnumerationIntegerSerializer(buildClass());
        assertEquals(testString1, serializer.serialize(0));
    }

    @Test
    public void serialize1() throws Exception {
        StringBuilder builder = new StringBuilder();
        BmmEnumerationIntegerSerializer serializer = new BmmEnumerationIntegerSerializer(buildClass());
        serializer.serialize(0, builder);
        assertEquals(testString1, builder.toString());
    }

    protected PersistedBmmEnumerationInteger buildClass() {
        PersistedBmmEnumerationInteger clazz = new PersistedBmmEnumerationInteger("PROPORTION_KIND_2");
        clazz.addAncestor("Integer");
        clazz.addItemName("pk_ratio");
        clazz.addItemName("pk_unitary");
        clazz.addItemName("pk_percent");
        clazz.addItemName("pk_fraction");
        clazz.addItemName("pk_integer_fraction");
        clazz.addItemValue(0);
        clazz.addItemValue(1001);
        clazz.addItemValue(1002);
        clazz.addItemValue(1003);
        return clazz;
    }

}