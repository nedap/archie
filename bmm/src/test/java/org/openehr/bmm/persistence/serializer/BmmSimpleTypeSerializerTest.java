package org.openehr.bmm.persistence.serializer;

import org.junit.Test;
import org.openehr.bmm.persistence.PersistedBmmOpenType;
import org.openehr.bmm.persistence.PersistedBmmSimpleType;

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
 * Created by cnanjo on 2/4/17.
 */

public class BmmSimpleTypeSerializerTest {
    private String testString1 = "type = <\"String\">\n";
    private String testString2 = "type_def = <\n" +
            "\ttype = <\"String\">\n" +
            ">\n";
    private String testString3 = "type_def = (P_BMM_SIMPLE_TYPE) <\n" +
            "\ttype = <\"String\">\n" +
            ">\n";
    private String testString4 = "(P_BMM_SIMPLE_TYPE) <\n" +
            "\ttype = <\"String\">\n" +
            ">\n";
    @Test
    public void serialize() throws Exception {
        PersistedBmmSimpleType simpleType = new PersistedBmmSimpleType("String");
        BmmSimpleTypeSerializer serializer = new BmmSimpleTypeSerializer(simpleType, true);
        assertEquals(testString1, serializer.serialize(0));
    }

    @Test
    public void serialize1() throws Exception {
        PersistedBmmSimpleType simpleType = new PersistedBmmSimpleType("String");
        BmmSimpleTypeSerializer serializer = new BmmSimpleTypeSerializer(simpleType, true, false);
        assertEquals(testString2, serializer.serialize(0));
    }

    @Test
    public void serialize2() throws Exception {
        PersistedBmmSimpleType simpleType = new PersistedBmmSimpleType("String");
        BmmSimpleTypeSerializer serializer = new BmmSimpleTypeSerializer(simpleType, false, false);
        assertEquals(testString3, serializer.serialize(0));
    }

    @Test
    public void serialize3() throws Exception {
        PersistedBmmSimpleType simpleType = new PersistedBmmSimpleType("String");
        BmmSimpleTypeSerializer serializer = new BmmSimpleTypeSerializer(simpleType, false, true);
        assertEquals(testString4, serializer.serialize(0));
    }

}