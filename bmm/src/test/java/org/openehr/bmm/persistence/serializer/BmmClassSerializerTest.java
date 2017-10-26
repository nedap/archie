package org.openehr.bmm.persistence.serializer;

import org.junit.Test;
import org.openehr.bmm.BmmMultiplicityInterval;
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.persistence.*;

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
 * Created by cnanjo on 1/31/17.
 */

public class BmmClassSerializerTest {

    private final String basicClass = "[\"ITEM\"] = <\n" +
            "\tname = <\"ITEM\">\n" +
            ">\n";

    private final String basicClassWithOneAncestor = "[\"ITEM\"] = <\n" +
            "\tname = <\"ITEM\">\n" +
            "\tancestors = <\"Parent1\",...>\n" +
            ">\n";

    private final String basicClassWithTwoAncestors = "[\"ITEM\"] = <\n" +
            "\tname = <\"ITEM\">\n" +
            "\tancestors = <\"Parent1\", \"Parent2\">\n" +
            ">\n";

    private final String genericClass = "[\"Interval\"] = <\n" +
            "\tname = <\"Interval\">\n" +
            "\tancestors = <\"Any\",...>\n" +
            "\tgeneric_parameter_defs = <\n" +
            "\t\t[\"T\"] = <\n" +
            "\t\t\tname = <\"T\">\n" +
            "\t\t\tconforms_to_type = <\"Ordered\">\n" +
            "\t\t>\n" +
            "\t>\n" +
            "\tproperties = <\n" +
            "\t\t[\"lower\"] = (P_BMM_SINGLE_PROPERTY_OPEN) <\n" +
            "\t\t\tname = <\"lower\">\n" +
            "\t\t\ttype = <\"T\">\n" +
            "\t\t>\n" +
            "\t\t[\"upper\"] = (P_BMM_SINGLE_PROPERTY_OPEN) <\n" +
            "\t\t\tname = <\"upper\">\n" +
            "\t\t\ttype = <\"T\">\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    private final String classWithTwoSimpleProps = "[\"ELEMENT\"] = <\n" +
            "\tname = <\"ELEMENT\">\n" +
            "\tancestors = <\"ITEM\",...>\n" +
            "\tproperties = <\n" +
            "\t\t[\"null_flavour\"] = (P_BMM_SINGLE_PROPERTY) <\n" +
            "\t\t\tname = <\"null_flavour\">\n" +
            "\t\t\ttype = <\"DV_CODED_TEXT\">\n" +
            "\t\t\tis_mandatory = <True>\n" +
            "\t\t>\n" +
            "\t\t[\"value\"] = (P_BMM_SINGLE_PROPERTY) <\n" +
            "\t\t\tname = <\"value\">\n" +
            "\t\t\ttype = <\"DATA_VALUE\">\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    private final String classWithContainerProperty = "[\"ELEMENT\"] = <\n" +
            "\tname = <\"ELEMENT\">\n" +
            "\tancestors = <\"ITEM\",...>\n" +
            "\tproperties = <\n" +
            "\t\t[\"items\"] = (P_BMM_CONTAINER_PROPERTY) <\n" +
            "\t\t\tname = <\"items\">\n" +
            "\t\t\ttype_def = <\n" +
            "\t\t\t\tcontainer_type = <\"List\">\n" +
            "\t\t\t\ttype = <\"ITEM\">\n" +
            "\t\t\t>\n" +
            "\t\t\tcardinality = <|>=1|>\n" +
            "\t\t\tis_mandatory = <True>\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    private final String classWithGenericProperty = "[\"SOME_TYPE\"] = <\n" +
            "\tname = <\"SOME_TYPE\">\n" +
            "\tancestors = <\"Any\",...>\n" +
            "\tproperties = <\n" +
            "\t\t[\"qty_interval_attr\"] = (P_BMM_GENERIC_PROPERTY) <\n" +
            "\t\t\tname = <\"qty_interval_attr\">\n" +
            "\t\t\ttype_def = <\n" +
            "\t\t\t\troot_type = <\"DV_INTERVAL\">\n" +
            "\t\t\t\tgeneric_parameters = <\"DV_QUANTITY\",...>\n" +
            "\t\t\t>\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    private final String classWithContainerProperty2 = "[\"Patient\"] = <\n" +
            "\tname = <\"Patient\">\n" +
            "\tancestors = <\"Any\",...>\n" +
            "\tproperties = <\n" +
            "\t\t[\"careProvider\"] = (P_BMM_CONTAINER_PROPERTY) <\n" +
            "\t\t\tname = <\"careProvider\">\n" +
            "\t\t\ttype_def = <\n" +
            "\t\t\t\tcontainer_type = <\"List\">\n" +
            "\t\t\t\ttype_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\t\t\t\t\troot_type = <\"ResourceReference\">\n" +
            "\t\t\t\t\tgeneric_parameters = <\"Party\",...>\n" +
            "\t\t\t\t>\n" +
            "\t\t\t>\n" +
            "\t\t\tcardinality = <|>=0|>\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    private final String classWithHighlyParameterizedProperty = "[\"CRAZY_TYPE\"] = <\n" +
            "\tname = <\"CRAZY_TYPE\">\n" +
            "\tancestors = <\"Any\",...>\n" +
            "\tproperties = <\n" +
            "\t\t[\"range\"] = (P_BMM_GENERIC_PROPERTY) <\n" +
            "\t\t\tname = <\"range\">\n" +
            "\t\t\ttype_def = <\n" +
            "\t\t\t\troot_type = <\"REFERENCE_RANGE\">\n" +
            "\t\t\t\tgeneric_parameter_defs = <\n" +
            "\t\t\t\t\t[\"T\"] = (P_BMM_GENERIC_TYPE) <\n" +
            "\t\t\t\t\t\troot_type = <\"DV_INTERVAL\">\n" +
            "\t\t\t\t\t\tgeneric_parameters = <\"DV_QUANTITY\",...>\n" +
            "\t\t\t\t\t>\n" +
            "\t\t\t\t\t[\"U\"] = (P_BMM_SIMPLE_TYPE) <\n" +
            "\t\t\t\t\t\ttype = <\"Integer\">\n" +
            "\t\t\t\t\t>\n" +
            "\t\t\t\t\t[\"V\"] = (P_BMM_CONTAINER_TYPE) <\n" +
            "\t\t\t\t\t\tcontainer_type = <\"List\">\n" +
            "\t\t\t\t\t\ttype = <\"DV_QUANTITY\">\n" +
            "\t\t\t\t\t>\n" +
            "\t\t\t\t\t[\"W\"] = (P_BMM_CONTAINER_TYPE) <\n" +
            "\t\t\t\t\t\tcontainer_type = <\"List\">\n" +
            "\t\t\t\t\t\ttype_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\t\t\t\t\t\t\troot_type = <\"DV_INTERVAL\">\n" +
            "\t\t\t\t\t\t\tgeneric_parameters = <\"DV_QUANTITY\",...>\n" +
            "\t\t\t\t\t\t>\n" +
            "\t\t\t\t\t>\n" +
            "\t\t\t\t>\n" +
            "\t\t\t>\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    private final String classWithOrderedProperty = "[\"CRAZY_TYPE\"] = <\n" +
            "\tname = <\"CRAZY_TYPE\">\n" +
            "\tancestors = <\"Any\",...>\n" +
            "\tproperties = <\n" +
            "\t\t[\"range\"] = (P_BMM_GENERIC_PROPERTY) <\n" +
            "\t\t\tname = <\"range\">\n" +
            "\t\t\ttype_def = <\n" +
            "\t\t\t\troot_type = <\"REFERENCE_RANGE\">\n" +
            "\t\t\t\tgeneric_parameter_defs = <\n" +
            "\t\t\t\t\ttype_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\t\t\t\t\t\troot_type = <\"DV_INTERVAL\">\n" +
            "\t\t\t\t\t\tgeneric_parameters = <\"DV_QUANTITY\",...>\n" +
            "\t\t\t\t\t>\n" +
            "\t\t\t\t\ttype_def = (P_BMM_SIMPLE_TYPE) <\n" +
            "\t\t\t\t\t\ttype = <\"Integer\">\n" +
            "\t\t\t\t\t>\n" +
            "\t\t\t\t\ttype_def = (P_BMM_CONTAINER_TYPE) <\n" +
            "\t\t\t\t\t\tcontainer_type = <\"List\">\n" +
            "\t\t\t\t\t\ttype = <\"DV_QUANTITY\">\n" +
            "\t\t\t\t\t>\n" +
            "\t\t\t\t\ttype_def = (P_BMM_CONTAINER_TYPE) <\n" +
            "\t\t\t\t\t\tcontainer_type = <\"List\">\n" +
            "\t\t\t\t\t\ttype_def = (P_BMM_GENERIC_TYPE) <\n" +
            "\t\t\t\t\t\t\troot_type = <\"DV_INTERVAL\">\n" +
            "\t\t\t\t\t\t\tgeneric_parameters = <\"DV_QUANTITY\",...>\n" +
            "\t\t\t\t\t\t>\n" +
            "\t\t\t\t\t>\n" +
            "\t\t\t\t>\n" +
            "\t\t\t>\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    private final String classWithSingleParameterizedProperty = "[\"RANGE_OF_INTERVAL_OF_QUANTITY\"] = <\n" +
            "\tname = <\"RANGE_OF_INTERVAL_OF_QUANTITY\">\n" +
            "\tancestors = <\"Any\",...>\n" +
            "\tproperties = <\n" +
            "\t\t[\"range\"] = (P_BMM_GENERIC_PROPERTY) <\n" +
            "\t\t\tname = <\"range\">\n" +
            "\t\t\ttype_def = <\n" +
            "\t\t\t\troot_type = <\"REFERENCE_RANGE\">\n" +
            "\t\t\t\tgeneric_parameter_defs = <\n" +
            "\t\t\t\t\t[\"T\"] = (P_BMM_GENERIC_TYPE) <\n" +
            "\t\t\t\t\t\troot_type = <\"DV_INTERVAL\">\n" +
            "\t\t\t\t\t\tgeneric_parameters = <\"DV_QUANTITY\",...>\n" +
            "\t\t\t\t\t>\n" +
            "\t\t\t\t>\n" +
            "\t\t\t>\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    private final String genericClassWithNestedGenericType = "[\"REFERENCE_RANGE\"] = <\n" +
            "\tname = <\"REFERENCE_RANGE\">\n" +
            "\tancestors = <\"Any\",...>\n" +
            "\tgeneric_parameter_defs = <\n" +
            "\t\t[\"T\"] = <\n" +
            "\t\t\tname = <\"T\">\n" +
            "\t\t\tconforms_to_type = <\"DV_ORDERED\">\n" +
            "\t\t>\n" +
            "\t>\n" +
            "\tproperties = <\n" +
            "\t\t[\"range\"] = (P_BMM_GENERIC_PROPERTY) <\n" +
            "\t\t\tname = <\"range\">\n" +
            "\t\t\ttype_def = <\n" +
            "\t\t\t\troot_type = <\"DV_INTERVAL\">\n" +
            "\t\t\t\tgeneric_parameters = <\"T\",...>\n" +
            "\t\t\t>\n" +
            "\t\t\tis_mandatory = <True>\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    @Test
    public void serializeAsString1() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildSimpleClass());
        assertEquals(basicClass, serializer.serialize(0));
    }

    @Test
    public void serializeAsString2() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildSimpleClassWithSingleAncestor());
        assertEquals(basicClassWithOneAncestor, serializer.serialize(0));
    }

    @Test
    public void serializeAsString3() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildSimpleClassWithTwoAncestors());
        assertEquals(basicClassWithTwoAncestors, serializer.serialize(0));
    }

    @Test
    public void serializeAsString4() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildGenericClass());
        assertEquals(genericClass, serializer.serialize(0));
    }

    @Test
    public void serializeAsString5() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildClassWith2SimpleProperties());
        assertEquals(classWithTwoSimpleProps, serializer.serialize(0));
    }

    @Test
    public void serializeAsString6() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildClassWithContainerProperty());
        assertEquals(classWithContainerProperty, serializer.serialize(0));
    }

    @Test
    public void serializeAsString7() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildClassWithGenericProperty());
        assertEquals(classWithGenericProperty, serializer.serialize(0));
    }

    @Test
    public void serializeAsString8() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildClassWithContainerProperty2());
        assertEquals(classWithContainerProperty2, serializer.serialize(0));
    }

    @Test
    public void serializeAsString9() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildClassWithParameterizedProperty());
        assertEquals(classWithHighlyParameterizedProperty, serializer.serialize(0));
    }

    @Test
    public void serializeAsString10() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildClassWithParameterizedProperty());
        assertEquals(classWithHighlyParameterizedProperty, serializer.serialize(0));
    }

    @Test
    public void serializeAsString11() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildClassWithSingleParameterizedProperty());
        assertEquals(classWithSingleParameterizedProperty, serializer.serialize(0));
    }

    @Test
    public void serializeAsString12() throws Exception {
        BmmClassSerializer serializer = new BmmClassSerializer(buildGenericClassWithNestedGenericType());
        assertEquals(genericClassWithNestedGenericType, serializer.serialize(0));
    }

    @Test
    public void serializeAsBuilder1() throws Exception {
        StringBuilder builder = new StringBuilder();
        BmmClassSerializer serializer = new BmmClassSerializer(buildSimpleClass());
        assertEquals(basicClass, serializer.serialize(0));
    }

    protected PersistedBmmClass buildSimpleClass() {
        return new PersistedBmmClass("ITEM");
    }

    protected PersistedBmmClass buildSimpleClassWithSingleAncestor() {
        PersistedBmmClass persistedClass = buildSimpleClass();
        persistedClass.addAncestor("Parent1");
        return persistedClass;
    }

    protected PersistedBmmClass buildSimpleClassWithTwoAncestors() {
        PersistedBmmClass persistedClass = buildSimpleClassWithSingleAncestor();
        persistedClass.addAncestor("Parent2");
        return persistedClass;
    }

    protected PersistedBmmClass buildGenericClass() {
        PersistedBmmClass persistedClass = new PersistedBmmClass("Interval");
        persistedClass.addAncestor("Any");
        PersistedBmmGenericParameter parameter = new PersistedBmmGenericParameter("T", "Ordered");
        persistedClass.addGenericParameterDefinition(parameter);
        PersistedBmmSinglePropertyOpen lower = new PersistedBmmSinglePropertyOpen("lower", "T");
        PersistedBmmSinglePropertyOpen upper = new PersistedBmmSinglePropertyOpen("upper", "T");
        persistedClass.addProperty(lower);
        persistedClass.addProperty(upper);
        return persistedClass;
    }

    protected PersistedBmmClass buildClassWith2SimpleProperties() {
        PersistedBmmClass persistedBmmClass = new PersistedBmmClass("ELEMENT");
        persistedBmmClass.addAncestor("ITEM");
        PersistedBmmSingleProperty property1 = new PersistedBmmSingleProperty("null_flavour", true, "DV_CODED_TEXT");
        PersistedBmmSingleProperty property2 = new PersistedBmmSingleProperty("value", false, "DATA_VALUE");
        persistedBmmClass.addProperty(property1);
        persistedBmmClass.addProperty(property2);
        return persistedBmmClass;
    }

    protected PersistedBmmClass buildClassWithContainerProperty() {
        PersistedBmmClass persistedBmmClass = new PersistedBmmClass("ELEMENT");
        persistedBmmClass.addAncestor("ITEM");
        PersistedBmmContainerProperty containerProperty = new PersistedBmmContainerProperty("items", true);
        containerProperty.setCardinality(new BmmMultiplicityInterval(1, false, null, true));
        PersistedBmmContainerType typeDefinition = new PersistedBmmContainerType("List", "ITEM");
        containerProperty.setTypeDefinition(typeDefinition);
        persistedBmmClass.addProperty(containerProperty);
        return persistedBmmClass;
    }

    protected PersistedBmmClass buildClassWithGenericProperty() {
        PersistedBmmClass persistedBmmClass = new PersistedBmmClass("SOME_TYPE");
        persistedBmmClass.addAncestor("Any");
        PersistedBmmGenericProperty genericProperty = new PersistedBmmGenericProperty("qty_interval_attr", false);
        PersistedBmmGenericType typeDefinition = new PersistedBmmGenericType();
        typeDefinition.setRootType("DV_INTERVAL");
        typeDefinition.addGenericParameter("DV_QUANTITY");
        genericProperty.setTypeDefinition(typeDefinition);
        persistedBmmClass.addProperty(genericProperty);
        return persistedBmmClass;
    }

    protected PersistedBmmClass buildClassWithContainerProperty2() {
        PersistedBmmClass persistedBmmClass = new PersistedBmmClass("Patient");
        persistedBmmClass.addAncestor("Any");
        PersistedBmmContainerProperty containerProperty = new PersistedBmmContainerProperty("careProvider", false);
        containerProperty.setCardinality(new BmmMultiplicityInterval(0, false, null, true));
        PersistedBmmContainerType typeDefinition = new PersistedBmmContainerType();
        typeDefinition.setContainerType("List");
        PersistedBmmGenericType genType = new PersistedBmmGenericType();
        genType.setRootType("ResourceReference");
        genType.addGenericParameter("Party");
        typeDefinition.setTypeDefinition(genType);
        containerProperty.setTypeDefinition(typeDefinition);
        persistedBmmClass.addProperty(containerProperty);
        return persistedBmmClass;
    }

    protected PersistedBmmClass buildClassWithParameterizedProperty() {
        PersistedBmmClass persistedBmmClass = new PersistedBmmClass("CRAZY_TYPE");
        persistedBmmClass.addAncestor("Any");
        PersistedBmmGenericProperty genericProperty = new PersistedBmmGenericProperty("range", false);
        PersistedBmmGenericType typeDefinition1 = new PersistedBmmGenericType();
        typeDefinition1.setRootType("REFERENCE_RANGE");
        //T
        PersistedBmmGenericType t1 = new PersistedBmmGenericType();
        t1.setRootType("DV_INTERVAL");
        t1.addGenericParameter("DV_QUANTITY");
        typeDefinition1.addGenericParameterDefinition("T", t1);
        //U
        PersistedBmmSimpleType t2 = new PersistedBmmSimpleType();
        t2.setType("Integer");
        typeDefinition1.addGenericParameterDefinition("U", t2);
        //V
        PersistedBmmContainerType t3 = new PersistedBmmContainerType();
        t3.setContainerType("List");
        t3.setType("DV_QUANTITY");
        typeDefinition1.addGenericParameterDefinition("V", t3);
        //W
        PersistedBmmContainerType t4 = new PersistedBmmContainerType();
        t4.setContainerType("List");
        PersistedBmmGenericType t4_1 = new PersistedBmmGenericType();
        t4_1.setRootType("DV_INTERVAL");
        t4_1.addGenericParameter("DV_QUANTITY");
        t4.setTypeDefinition(t4_1);
        typeDefinition1.addGenericParameterDefinition("W", t4);

        genericProperty.setTypeDefinition(typeDefinition1);
        persistedBmmClass.addProperty(genericProperty);
        return persistedBmmClass;
    }

    protected PersistedBmmClass buildClassWithOrderedProperty() {
        PersistedBmmClass persistedBmmClass = new PersistedBmmClass("CRAZY_TYPE");
        persistedBmmClass.addAncestor("Any");
        PersistedBmmGenericProperty genericProperty = new PersistedBmmGenericProperty("range", false);
        PersistedBmmGenericType typeDefinition1 = new PersistedBmmGenericType();
        typeDefinition1.setRootType("REFERENCE_RANGE");
        //0
        PersistedBmmGenericType t1 = new PersistedBmmGenericType();
        t1.setRootType("DV_INTERVAL");
        t1.addGenericParameter("DV_QUANTITY");
        typeDefinition1.addGenericParameterDefinition(t1);
        //1
        PersistedBmmSimpleType t2 = new PersistedBmmSimpleType();
        t2.setType("Integer");
        typeDefinition1.addGenericParameterDefinition(t2);
        //2
        PersistedBmmContainerType t3 = new PersistedBmmContainerType();
        t3.setContainerType("List");
        t3.setType("DV_QUANTITY");
        typeDefinition1.addGenericParameterDefinition(t3);
        //3
        PersistedBmmContainerType t4 = new PersistedBmmContainerType();
        t4.setContainerType("List");
        PersistedBmmGenericType t4_1 = new PersistedBmmGenericType();
        t4_1.setRootType("DV_INTERVAL");
        t4_1.addGenericParameter("DV_QUANTITY");
        t4.setTypeDefinition(t4_1);
        typeDefinition1.addGenericParameterDefinition(t4);

        genericProperty.setTypeDefinition(typeDefinition1);
        persistedBmmClass.addProperty(genericProperty);
        return persistedBmmClass;
    }

    protected PersistedBmmClass buildClassWithSingleParameterizedProperty() {
        PersistedBmmClass persistedBmmClass = new PersistedBmmClass("RANGE_OF_INTERVAL_OF_QUANTITY");
        persistedBmmClass.addAncestor("Any");
        PersistedBmmGenericProperty genericProperty = new PersistedBmmGenericProperty("range", false);
        PersistedBmmGenericType typeDefinition1 = new PersistedBmmGenericType();
        typeDefinition1.setRootType("REFERENCE_RANGE");
        //T
        PersistedBmmGenericType t1 = new PersistedBmmGenericType();
        t1.setRootType("DV_INTERVAL");
        t1.addGenericParameter("DV_QUANTITY");
        typeDefinition1.addGenericParameterDefinition("T", t1);
        genericProperty.setTypeDefinition(typeDefinition1);
        persistedBmmClass.addProperty(genericProperty);
        return persistedBmmClass;
    }

    protected PersistedBmmClass buildGenericClassWithNestedGenericType() {
        PersistedBmmClass genericClass = new PersistedBmmClass("REFERENCE_RANGE");
        genericClass.addAncestor("Any");
        genericClass.addGenericParameterDefinition(new PersistedBmmGenericParameter("T", "DV_ORDERED"));
        PersistedBmmGenericProperty range = new PersistedBmmGenericProperty("range", true);
        PersistedBmmGenericType interval = new PersistedBmmGenericType();
        interval.setRootType("DV_INTERVAL");
        interval.addGenericParameter("T");
        range.setTypeDefinition(interval);
        genericClass.addProperty(range);
        return genericClass;
    }
}