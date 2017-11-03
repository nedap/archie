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
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmPackage;
import org.openehr.bmm.persistence.PersistedBmmPackage;
import org.openehr.bmm.persistence.PersistedBmmPackageContainer;

import static org.junit.Assert.*;

/**
 *
 *
 *  Created by cnanjo on 1/25/17.
 */
public class BmmPackageContainerSerializerTest {

    private BmmPackage packageContainer;
    private String testOutput1 = "packages = <\n" +
            "\t[\"core\"] = <\n" +
            "\t\tname = <\"core\">\n" +
            "\t\tclasses = <\"Class1\", \"Class2\">\n" +
            "\t>\n" +
            "\t[\"main\"] = <\n" +
            "\t\tname = <\"main\">\n" +
            "\t\tclasses = <\"Class3\", \"Class4\">\n" +
            "\t>\n" +
            ">\n";

    @Before
    public void setUp() throws Exception {
        packageContainer = new BmmPackage("parent.package");
        BmmPackage testPackage1 = new BmmPackage("core");
        BmmClass class1 = new BmmClass("Class1");
        BmmClass class2 = new BmmClass("Class2");
        testPackage1.addClass(class1);
        testPackage1.addClass(class2);

        BmmPackage testPackage2 = new BmmPackage("main");
        BmmClass class3 = new BmmClass("Class3");
        BmmClass class4 = new BmmClass("Class4");
        testPackage2.addClass(class3);
        testPackage2.addClass(class4);

        packageContainer.addPackage(testPackage1);
        packageContainer.addPackage(testPackage2);
    }

    @Test
    public void serializeAsString() throws Exception {
        PersistedBmmPackageContainer persistedBmmPackageContainer = new PersistedBmmPackage();
        BmmPackageContainerSerializer serializer = new BmmPackageContainerSerializer(persistedBmmPackageContainer);
        assertEquals("packages = <\n>\n", serializer.serialize(0));
    }
    @Test
    public void serializeAsString2() throws Exception {
        PersistedBmmPackage persistedBmmPackage = new PersistedBmmPackage();
        persistedBmmPackage.configureFrom(packageContainer);
        BmmPackageContainerSerializer serializer = new BmmPackageContainerSerializer(persistedBmmPackage);
        assertEquals(testOutput1, serializer.serialize(0));
    }

    @Test
    public void serializeAsBuilder() throws Exception {
        PersistedBmmPackage persistedBmmPackage = new PersistedBmmPackage();
        persistedBmmPackage.configureFrom(packageContainer);
        BmmPackageContainerSerializer serializer = new BmmPackageContainerSerializer(persistedBmmPackage);
        StringBuilder builder = new StringBuilder();
        serializer.serialize(0, builder);
        assertEquals(testOutput1, builder.toString());
    }

}