package org.openehr.bmm.persistence.serializer;

import org.junit.Test;
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
 * Created by cnanjo on 2/1/17.
 */

public class BmmGenericTypeSerializerTest {

    private final String genericTypeWithCast = "type_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\troot_type = <\"ResourceReference\">\n" +
            "\tgeneric_parameters = <\"Party\",...>\n" +
            ">\n";

    private final String genericTypeWithoutCast = "type_def = <\n" +
            "\troot_type = <\"ResourceReference\">\n" +
            "\tgeneric_parameters = <\"Party\",...>\n" +
            ">\n";

    private final String nestedGenericType = "type_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\troot_type = <\"ResourceReference\">\n" +
            "\tgeneric_parameter_defs = <\n" +
            "\t\ttype_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\t\t\troot_type = <\"Party\">\n" +
            "\t\t\tgeneric_parameters = <\"Person\",...>\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    private final String nestedParameterizedGenericType = "type_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\troot_type = <\"ResourceReference\">\n" +
            "\tgeneric_parameter_defs = <\n" +
            "\t\ttype_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\t\t\troot_type = <\"Party\">\n" +
            "\t\t\tgeneric_parameters = <\n" +
            "\t\t\t\t[\"T\"] = <\"Person\">\n" +
            "\t\t\t>\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    private final String nestedParameterizedGenericType2 = "type_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\troot_type = <\"ResourceReference\">\n" +
            "\tgeneric_parameter_defs = <\n" +
            "\t\ttype_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\t\t\troot_type = <\"CoolMap\">\n" +
            "\t\t\tgeneric_parameters = <\n" +
            "\t\t\t\t[\"U\"] = <\"String\">\n" +
            "\t\t\t\t[\"V\"] = <\"Object\">\n" +
            "\t\t\t>\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    @Test
    public void serializeAsString1() throws Exception {
        BmmGenericTypeSerializer serializer = new BmmGenericTypeSerializer(buildGenericType());
        assertEquals(genericTypeWithCast, serializer.serialize(0));
    }

    @Test
    public void serializeAsString2() throws Exception {
        BmmGenericTypeSerializer serializer = new BmmGenericTypeSerializer(buildGenericType(), true, false);
        assertEquals(genericTypeWithoutCast, serializer.serialize(0));
    }

    @Test
    public void serializeAsString3() throws Exception {
        BmmGenericTypeSerializer serializer = new BmmGenericTypeSerializer(buildNestedGenericType());
        assertEquals(nestedGenericType, serializer.serialize(0));
    }

    @Test
    public void serializeAsString4() throws Exception {
        BmmGenericTypeSerializer serializer = new BmmGenericTypeSerializer(buildNestedParameterizedGenericType());
        assertEquals(nestedParameterizedGenericType, serializer.serialize(0));
    }

    @Test
    public void serializeAsString5() throws Exception {
        BmmGenericTypeSerializer serializer = new BmmGenericTypeSerializer(buildNestedParameterizedGenericType2());
        assertEquals(nestedParameterizedGenericType2, serializer.serialize(0));
    }

    @Test
    public void serializeAsBuilder() throws Exception {
        StringBuilder builder = new StringBuilder();
        BmmGenericTypeSerializer serializer = new BmmGenericTypeSerializer(buildGenericType());
        serializer.serialize(0, builder);
        assertEquals(genericTypeWithCast, builder.toString());
    }

    protected PersistedBmmGenericType buildGenericType() {
        PersistedBmmGenericType genericType = new PersistedBmmGenericType();
        genericType.setRootType("ResourceReference");
        genericType.addGenericParameter("Party");
        return genericType;
    }

    protected PersistedBmmGenericType buildNestedGenericType() {
        PersistedBmmGenericType genericType = new PersistedBmmGenericType();
        genericType.setRootType("ResourceReference");
        PersistedBmmGenericType childType = new PersistedBmmGenericType();
        childType.setRootType("Party");
        childType.addGenericParameter("Person");
        genericType.addGenericParameterDefinition(childType);
        return genericType;
    }

    protected PersistedBmmGenericType buildNestedParameterizedGenericType() {
        PersistedBmmGenericType genericType = new PersistedBmmGenericType();
        genericType.setRootType("ResourceReference");
        PersistedBmmGenericType childType = new PersistedBmmGenericType();
        childType.setRootType("Party");
        childType.addGenericParameter("T", "Person");
        genericType.addGenericParameterDefinition(childType);
        return genericType;
    }

    protected PersistedBmmGenericType buildNestedParameterizedGenericType2() {
        PersistedBmmGenericType genericType = new PersistedBmmGenericType();
        genericType.setRootType("ResourceReference");
        PersistedBmmGenericType childType = new PersistedBmmGenericType();
        childType.setRootType("CoolMap");
        childType.addGenericParameter("U", "String");
        childType.addGenericParameter("V", "Object");
        genericType.addGenericParameterDefinition(childType);
        return genericType;
    }

}