package org.openehr.referencemodel.loader;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017 Cognitive Medical Systems
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

import org.openehr.bmm.persistence.PersistedBmmSchema;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReferenceModelLoaderTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void loadCimiReferenceModel() {
//        ReferenceModelLoader loader = new ReferenceModelLoader();
//        PersistedBmmSchema cimiRm = loader.loadCimiReferenceModel(ReferenceModelLoaderTest.class.getResourceAsStream("/cimi/CIMI-RM-3.0.5.bmm"));
//        assertNotNull(cimiRm);
//        assertEquals("3.0.5", cimiRm.getRmRelease());
//        assertEquals("CIMI", cimiRm.getRmPublisher());
//        assertEquals("RM", cimiRm.getSchemaName());
//        assertEquals("2.0", cimiRm.getBmmVersion());
//        assertEquals("Monday, October 19, 2015", cimiRm.getSchemaRevision());
//        assertEquals("dstu", cimiRm.getSchemaLifecycleState());
//        assertEquals("CIMI_Reference_Model v3.0.5 schema generated from UML", cimiRm.getSchemaDescription());
//        assertEquals("CIMI_Reference_Model.Core", cimiRm.getArchetypeRmClosurePackages().get(0));

    }
}