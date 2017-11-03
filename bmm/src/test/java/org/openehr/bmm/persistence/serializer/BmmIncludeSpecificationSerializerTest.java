package org.openehr.bmm.persistence.serializer;

import org.junit.Test;
import org.openehr.bmm.persistence.BmmIncludeSpecification;

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
 * Created by cnanjo on 2/15/17.
 */

public class BmmIncludeSpecificationSerializerTest {

    private String testString = "includes = <\n" +
            "\t[\"1\"] = <\n" +
            "\t\tid = <\"my.id.1\">\n" +
            "\t>\n" +
            "\t[\"2\"] = <\n" +
            "\t\tid = <\"my.id.2\">\n" +
            "\t>\n" +
            "\t[\"3\"] = <\n" +
            "\t\tid = <\"my.id.3\">\n" +
            "\t>\n" +
            "\t[\"4\"] = <\n" +
            "\t\tid = <\"my.id.4\">\n" +
            "\t>\n" +
            ">\n";

    @Test
    public void serializeAsString() throws Exception {
        BmmIncludeSpecificationSerializer serializer = new BmmIncludeSpecificationSerializer(buildIncludes());
        assertEquals(testString, serializer.serialize(0));
    }

    @Test
    public void serializeAsBuilder() throws Exception {
        StringBuilder builder = new StringBuilder();
        BmmIncludeSpecificationSerializer serializer = new BmmIncludeSpecificationSerializer(buildIncludes());
        serializer.serialize(0, builder);
        assertEquals(testString, builder.toString());
    }

    protected List<BmmIncludeSpecification> buildIncludes() {
        List<BmmIncludeSpecification> includes = new ArrayList<>();
        includes.add(new BmmIncludeSpecification("my.id.1", null));
        includes.add(new BmmIncludeSpecification("my.id.2", null));
        includes.add(new BmmIncludeSpecification("my.id.3", null));
        includes.add(new BmmIncludeSpecification("my.id.4", null));
        return includes;
    }

}