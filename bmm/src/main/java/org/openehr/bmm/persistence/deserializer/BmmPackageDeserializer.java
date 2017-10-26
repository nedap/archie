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

import org.openehr.bmm.BmmConstants;
import org.openehr.bmm.persistence.PersistedBmmPackage;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.OdinAttribute;
import java.util.List;



public class BmmPackageDeserializer {
    public PersistedBmmPackage deserialize(CompositeOdinObject packageDefinition) {
        PersistedBmmPackage bmmPackage = new PersistedBmmPackage();
        String name = packageDefinition.getAttribute("name").getStringValue();
        bmmPackage.setName(name);
        OdinAttribute classesAttribute = packageDefinition.getAttribute(BmmConstants.BMM_PROPERTY_CLASSES);
        if (classesAttribute != null) {
            List<String> classes = classesAttribute.getChildrenAsStringList(true);
            bmmPackage.setClasses(classes);
        }
        OdinAttribute packagesDefinition = packageDefinition.getAttribute(BmmConstants.BMM_PROPERTY_PACKAGES);
        if (packagesDefinition != null) {
            CompositeOdinObject packagesDef = packagesDefinition.getSoleCompositeObjectBody();
            BmmPackageContainerDeserializer deserializer = new BmmPackageContainerDeserializer();
            List<PersistedBmmPackage> packages = deserializer.deserialize(packagesDef);
            bmmPackage.setPackages(packages);
        }
        return bmmPackage;
    }
}
