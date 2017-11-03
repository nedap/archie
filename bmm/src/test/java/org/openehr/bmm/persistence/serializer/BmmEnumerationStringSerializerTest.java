package org.openehr.bmm.persistence.serializer;

import org.junit.Test;
import org.openehr.bmm.persistence.PersistedBmmEnumerationString;

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

public class BmmEnumerationStringSerializerTest {

    private String testString1 = "[\"MAGNITUDE_STATUS\"] = (P_BMM_ENUMERATION_STRING) <\n" +
            "\tname = <\"MAGNITUDE_STATUS\">\n" +
            "\tancestors = <\"String\",...>\n" +
            "\titem_names = <\"le\", \"ge\", \"eq\", \"approx_eq\">\n" +
            "\titem_values = <\"<=\", \">=\", \"=\", \"~\">\n" +
            ">\n";
    @Test
    public void serializeAsString1() throws Exception {
        BmmEnumerationStringSerializer serializer = new BmmEnumerationStringSerializer(buildClass());
        assertEquals(testString1, serializer.serialize(0));
    }

    @Test
    public void serializeAsBuilder1() throws Exception {
        StringBuilder builder = new StringBuilder();
        BmmEnumerationStringSerializer serializer = new BmmEnumerationStringSerializer(buildClass());
        serializer.serialize(0, builder);
        assertEquals(testString1, builder.toString());
    }

    protected PersistedBmmEnumerationString buildClass() {
        PersistedBmmEnumerationString clazz = new PersistedBmmEnumerationString("MAGNITUDE_STATUS");
        clazz.addAncestor("String");
        clazz.addItemName("le");
        clazz.addItemName("ge");
        clazz.addItemName("eq");
        clazz.addItemName("approx_eq");
        clazz.addItemValue("<=");
        clazz.addItemValue(">=");
        clazz.addItemValue("=");
        clazz.addItemValue("~");
        return clazz;
    }
}