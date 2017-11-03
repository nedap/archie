package org.openehr.bmm.persistence.serializer;

import org.junit.Test;
import org.openehr.bmm.persistence.PersistedBmmSinglePropertyOpen;

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

public class BmmSinglePropertyOpenSerializerTest {

    private String testString1 = "[\"lower\"] = (P_BMM_SINGLE_PROPERTY_OPEN) <\n" +
            "\tname = <\"lower\">\n" +
            "\ttype = <\"T\">\n" +
            ">\n";
    @Test
    public void serializeAsString() throws Exception {
        BmmSinglePropertyOpenSerializer serializer = new BmmSinglePropertyOpenSerializer(buildProperty());
        assertEquals(testString1, serializer.serialize(0));
    }

    @Test
    public void serialize1() throws Exception {

    }

    public PersistedBmmSinglePropertyOpen buildProperty() {
        PersistedBmmSinglePropertyOpen property = new PersistedBmmSinglePropertyOpen("lower", "T");
        return property;
    }

}