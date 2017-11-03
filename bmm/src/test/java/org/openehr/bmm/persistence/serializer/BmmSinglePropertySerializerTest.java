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

import org.junit.Before;
import org.junit.Test;
import org.openehr.bmm.persistence.PersistedBmmSingleProperty;

import static org.junit.Assert.*;

/**
 * Tests for the serialization of PersistedSingleProperty.
 *
 * Created by cnanjo on 1/26/17.
 */
public class BmmSinglePropertySerializerTest {

    protected String testString1 = "[\"null_flavour\"] = (P_BMM_SINGLE_PROPERTY) <\n" +
            "\tname = <\"null_flavour\">\n" +
            "\ttype = <\"DV_CODED_TEXT\">\n" +
            "\tis_mandatory = <True>\n" +
            ">\n";

    protected String testString2 = "[\"value\"] = (P_BMM_SINGLE_PROPERTY) <\n" +
            "\tname = <\"value\">\n" +
            "\ttype = <\"DATA_VALUE\">\n" +
            ">\n";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void serializeAsString1() throws Exception {
        BmmSinglePropertySerializer serializer = new BmmSinglePropertySerializer(buildSimpleDatatype1());
        assertEquals(testString1, serializer.serialize(0));

    }

    @Test
    public void serializeAsString2() throws Exception {
        BmmSinglePropertySerializer serializer = new BmmSinglePropertySerializer(buildSimpleDatatype2());
        assertEquals(testString2, serializer.serialize(0));

    }

    @Test
    public void serializeAsBuilder1() throws Exception {
        StringBuilder builder = new StringBuilder();
        BmmSinglePropertySerializer serializer = new BmmSinglePropertySerializer(buildSimpleDatatype2());
        String serializedProperty = serializer.serialize(0);
        assertEquals(testString2, serializedProperty);
    }

    protected PersistedBmmSingleProperty buildSimpleDatatype1() {
        PersistedBmmSingleProperty property = new PersistedBmmSingleProperty("null_flavour", true, "DV_CODED_TEXT");
        return property;
    }

    protected PersistedBmmSingleProperty buildSimpleDatatype2() {
        PersistedBmmSingleProperty property = new PersistedBmmSingleProperty("value", false, "DATA_VALUE");
        return property;
    }

}