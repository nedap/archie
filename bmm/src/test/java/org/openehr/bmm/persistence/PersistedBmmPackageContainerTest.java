package org.openehr.bmm.persistence;

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

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class PersistedBmmPackageContainerTest {

    @Test
    public void getPackagePathsTest() {
        PersistedBmmSchema container = new PersistedBmmSchema();
        PersistedBmmPackage leaf11 = new PersistedBmmPackage("leaf1-1");
        PersistedBmmPackage leaf12 = new PersistedBmmPackage("leaf1-2");
        PersistedBmmPackage leaf2 = new PersistedBmmPackage("leaf2");
        PersistedBmmPackage leaf3 = new PersistedBmmPackage("leaf3");
        PersistedBmmPackage mid1 = new PersistedBmmPackage("mid1");
        mid1.addPackage(leaf11);
        mid1.addPackage(leaf12);
        PersistedBmmPackage mid2 = new PersistedBmmPackage("mid2");
        mid2.addPackage(leaf2);
        PersistedBmmPackage mid3 = new PersistedBmmPackage("mid3");
        mid3.addPackage(leaf3);
        PersistedBmmPackage start1 = new PersistedBmmPackage("start1");
        start1.addPackage(mid3);
        container.addPackage(mid1);
        container.addPackage(mid2);
        container.addPackage(start1);
        List<String> paths = container.getPackagePaths();
        assertEquals(8, paths.size());
        System.out.println(paths);
        assertEquals("mid1", paths.get(0));
        assertEquals("mid1.leaf1-1", paths.get(1));
        assertEquals("mid1.leaf1-2", paths.get(2));
        assertEquals("mid2", paths.get(3));
        assertEquals("mid2.leaf2", paths.get(4));
        assertEquals("start1", paths.get(5));
        assertEquals("start1.mid3", paths.get(6));
        assertEquals("start1.mid3.leaf3", paths.get(7));
    }

}