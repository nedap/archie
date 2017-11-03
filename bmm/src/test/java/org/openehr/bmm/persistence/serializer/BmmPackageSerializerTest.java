package org.openehr.bmm.persistence.serializer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmPackage;
import org.openehr.bmm.persistence.PersistedBmmPackage;

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
 * Created by cnanjo on 1/25/17.
 */

public class BmmPackageSerializerTest {

    private BmmPackage testPackage;
    private String testString1 = "[\"core\"] = <\n\tname = <\"core\">\n\tclasses = <\"Class1\", \"Class2\">\n>\n";
    private String testString2 = "[\"parent\"] = <\n" +
            "\tname = <\"parent\">\n" +
            "\tpackages = <\n" +
            "\t\t[\"pkg1\"] = <\n" +
            "\t\t\tname = <\"pkg1\">\n" +
            "\t\t\tclasses = <\"Class1\", \"Class2\">\n" +
            "\t\t>\n" +
            "\t\t[\"pkg2\"] = <\n" +
            "\t\t\tname = <\"pkg2\">\n" +
            "\t\t\tclasses = <\"Class3\", \"Class4\">\n" +
            "\t\t\tpackages = <\n" +
            "\t\t\t\t[\"pkg3\"] = <\n" +
            "\t\t\t\t\tname = <\"pkg3\">\n" +
            "\t\t\t\t\tclasses = <\"Class5\", \"Class6\">\n" +
            "\t\t\t\t\tpackages = <\n" +
            "\t\t\t\t\t\t[\"pkg4\"] = <\n" +
            "\t\t\t\t\t\t\tname = <\"pkg4\">\n" +
            "\t\t\t\t\t\t\tclasses = <\"Class7\", \"Class8\">\n" +
            "\t\t\t\t\t\t>\n" +
            "\t\t\t\t\t>\n" +
            "\t\t\t\t>\n" +
            "\t\t\t>\n" +
            "\t\t>\n" +
            "\t>\n" +
            ">\n";

    @Test
    public void serializeAsString() throws Exception {
        testPackage = buildSimplePackage();
        PersistedBmmPackage persistedBmmPackage = new PersistedBmmPackage();
        persistedBmmPackage.configureFrom(testPackage);
        BmmPackageSerializer serializer = new BmmPackageSerializer(persistedBmmPackage);
        assertEquals(testString1, serializer.serialize(0));
    }

    @Test
    public void serializeAsString2() throws Exception {
        testPackage = buildNestedPackage();
        PersistedBmmPackage persistedBmmPackage = new PersistedBmmPackage();
        persistedBmmPackage.configureFrom(testPackage);
        BmmPackageSerializer serializer = new BmmPackageSerializer(persistedBmmPackage);
        assertEquals(testString2, serializer.serialize(0));
    }

    @Test
    public void serializeAsBuilder() throws Exception {
        StringBuilder builder = new StringBuilder();
        testPackage = buildSimplePackage();
        PersistedBmmPackage persistedBmmPackage = new PersistedBmmPackage();
        persistedBmmPackage.configureFrom(testPackage);
        BmmPackageSerializer serializer = new BmmPackageSerializer(persistedBmmPackage);
        serializer.serialize(0, builder);
        assertEquals(testString1, builder.toString());

    }

    protected BmmPackage buildSimplePackage() {
        BmmPackage testPackage = new BmmPackage("core");
        BmmClass class1 = new BmmClass("Class1");
        BmmClass class2 = new BmmClass("Class2");
        testPackage.addClass(class1);
        testPackage.addClass(class2);
        return testPackage;
    }

    protected BmmPackage buildNestedPackage() {
        BmmPackage childPackage1 = new BmmPackage("pkg1");
        BmmClass class1 = new BmmClass("Class1");
        BmmClass class2 = new BmmClass("Class2");
        childPackage1.addClass(class1);
        childPackage1.addClass(class2);

        BmmPackage childPackage2 = new BmmPackage("pkg2");
        BmmClass class3 = new BmmClass("Class3");
        BmmClass class4 = new BmmClass("Class4");
        childPackage2.addClass(class3);
        childPackage2.addClass(class4);

        BmmPackage childPackage3 = new BmmPackage("pkg3");
        BmmClass class5 = new BmmClass("Class5");
        BmmClass class6 = new BmmClass("Class6");
        childPackage3.addClass(class5);
        childPackage3.addClass(class6);

        BmmPackage childPackage4 = new BmmPackage("pkg4");
        BmmClass class7 = new BmmClass("Class7");
        BmmClass class8 = new BmmClass("Class8");
        childPackage4.addClass(class7);
        childPackage4.addClass(class8);

        BmmPackage testPackage = new BmmPackage("parent");
        childPackage2.addPackage(childPackage3);
        childPackage3.addPackage(childPackage4);
        testPackage.addPackage(childPackage1);
        testPackage.addPackage(childPackage2);
        return testPackage;
    }

}