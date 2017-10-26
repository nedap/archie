package org.openehr.bmm.persistence.deserializer;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017  Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 * Author: Claude Nanjo
 */

import org.junit.Before;
import org.junit.Test;
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.persistence.*;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.antlr.OdinVisitorImpl;
import org.openehr.odin.loader.OdinLoaderImpl;

import java.util.Map;

import static org.junit.Assert.*;

public class BmmSchemaDeserializerTest {


    private PersistedBmmSchema persistedBmmSchema;

    @Before
    public void setup() {
        OdinLoaderImpl loader = new OdinLoaderImpl();
        OdinVisitorImpl visitor = loader.loadOdinFile(BmmSchemaDeserializerTest.class.getResourceAsStream("/cimi/CIMI-RM-3.0.5.bmm"));
        CompositeOdinObject root = visitor.getAstRootNode();
        BmmSchemaDeserializer deserializer = new BmmSchemaDeserializer();
        persistedBmmSchema = deserializer.deserialize(root);
    }

    @Test
    public void testTopLevelPackage() {
        Map<String, PersistedBmmPackage> packages = persistedBmmSchema.getPackages();
        assertEquals(1, packages.size());
        PersistedBmmPackage bmmPackage = packages.get("CIMI_REFERENCE_MODEL");
        assertEquals("CIMI_Reference_Model", bmmPackage.getName());
        testSecondLevelPackages(bmmPackage);
        testClassDefinitions(persistedBmmSchema);
    }

    public void testSecondLevelPackages(PersistedBmmPackage bmmParentPackage) {
        Map<String, PersistedBmmPackage> packages = bmmParentPackage.getPackages();
        assertEquals(4, packages.size());
        PersistedBmmPackage bmmPackage = packages.get("CORE");
        assertEquals("Core", bmmPackage.getName());
        assertEquals(7, bmmPackage.getClasses().size());
        bmmPackage = packages.get("DATA_VALUE_TYPES");
        assertEquals("Data_Value_Types", bmmPackage.getName());
        assertEquals(26, bmmPackage.getClasses().size());
        bmmPackage = packages.get("PARTY");
        assertEquals("Party", bmmPackage.getName());
        assertEquals(4, bmmPackage.getClasses().size());
        bmmPackage = packages.get("PRIMITIVE_TYPES");
        assertEquals("Primitive_Types", bmmPackage.getName());
        assertEquals(10, bmmPackage.getClasses().size());
    }

    public void testClassDefinitions(PersistedBmmSchema model) {
        assertEquals(37, model.getClassDefinitions().size());
        assertEquals(10, model.getPrimitives().size());
        testLocatable(model);
        testIntervalValue(model);
    }

    public void testLocatable(PersistedBmmSchema model) {
        PersistedBmmClass locatable = model.getClassDefinition("LOCATABLE");
        assertNotNull(locatable);
        assertTrue(locatable.isAbstract());
        assertEquals("LOCATABLE", locatable.getName());
        assertEquals(4, locatable.getProperties().size());

        PersistedBmmSingleProperty archetypeNodeId = (PersistedBmmSingleProperty)locatable.getPropertyByName("archetype_node_id");
        assertEquals("archetype_node_id", archetypeNodeId.getName());
        assertTrue(archetypeNodeId.getImInfrastructure());
        assertTrue(archetypeNodeId.getMandatory());
        assertEquals("String", archetypeNodeId.getType());

        PersistedBmmSingleProperty name = (PersistedBmmSingleProperty)locatable.getPropertyByName("name");
        assertEquals("name", name.getName());
        assertFalse(name.getImInfrastructure());
        assertTrue(name.getMandatory());
        assertEquals("String", name.getType());

        PersistedBmmSingleProperty archetypeDetails = (PersistedBmmSingleProperty)locatable.getPropertyByName("archetype_details");
        assertEquals("archetype_details", archetypeDetails.getName());
        assertTrue(archetypeDetails.getImInfrastructure());
        assertFalse(archetypeDetails.getMandatory());
        assertEquals("ARCHETYPED", archetypeDetails.getType());

        PersistedBmmContainerProperty link = (PersistedBmmContainerProperty)locatable.getPropertyByName("link");
        assertEquals("link", link.getName());
        assertFalse(link.getImInfrastructure());
        assertFalse(link.getMandatory());
        PersistedBmmContainerType containerType = (PersistedBmmContainerType)link.getTypeDefinition();
        assertEquals("List", containerType.getContainerType());
        assertEquals("LINK", containerType.getType());
    }

    public void testIntervalValue(PersistedBmmSchema model) {
        PersistedBmmClass intervalValue = model.getClassDefinition("INTERVAL_VALUE");
        assertNotNull(intervalValue);
        assertFalse(intervalValue.isAbstract());
        assertEquals("INTERVAL_VALUE", intervalValue.getName());
        assertEquals(6, intervalValue.getProperties().size());

        PersistedBmmGenericParameter genericParameter = intervalValue.getGenericParameterDefinitions().get("T");
        assertNotNull(genericParameter);
        assertEquals("T", genericParameter.getName());
        assertEquals("ORDERED_VALUE", genericParameter.getConformsToType());

        PersistedBmmSinglePropertyOpen lower = (PersistedBmmSinglePropertyOpen)intervalValue.getPropertyByName("lower");
        assertEquals("lower", lower.getName());
        assertFalse(lower.getImInfrastructure());
        assertFalse(lower.getMandatory());
        assertEquals("T", lower.getType());
    }

    @Test
    public void testLoad() {
        assertNotNull(persistedBmmSchema);
        assertEquals("3.0.5", persistedBmmSchema.getRmRelease());
        assertEquals("CIMI", persistedBmmSchema.getRmPublisher());
        assertEquals("RM", persistedBmmSchema.getSchemaName());
//        assertEquals("2.0", persistedBmmSchema.getBmmInternalVersion());
        assertEquals("Monday, October 19, 2015", persistedBmmSchema.getSchemaRevision());
        assertEquals("dstu", persistedBmmSchema.getSchemaLifecycleState());
        assertEquals("CIMI_Reference_Model v3.0.5 schema generated from UML", persistedBmmSchema.getSchemaDescription());
        assertEquals("CIMI_Reference_Model.Core", persistedBmmSchema.getArchetypeRmClosurePackages().get(0));
    }

    @Test
    public void serialize() throws Exception {
        OdinLoaderImpl loader = new OdinLoaderImpl();
        OdinVisitorImpl visitor = loader.loadOdinFile(BmmSchemaDeserializerTest.class.getResourceAsStream("/cimi/CIMI-RM-3.0.5.bmm"));
        CompositeOdinObject root = visitor.getAstRootNode();
        BmmSchemaDeserializer deserializer = new BmmSchemaDeserializer();
        persistedBmmSchema = deserializer.deserialize(root);
        //BmmSchemaSerializer serializer = new BmmSchemaSerializer(persistedBmmSchema);
        System.out.println("---------------------------");
        //System.out.println(serializer.serialize());
    }

}