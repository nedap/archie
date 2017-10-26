package org.openehr.docgen.model;

import java.util.ArrayList;
import java.util.List;

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
 * Created by cnanjo on 3/7/17.
 */

public class ClassDetails extends ClassListItem {
    private String documentation;
    private List<PropertyDetails> properties;

    public ClassDetails() {
        properties = new ArrayList<>();
    }

    public ClassDetails(String classDetailsUrl, String classPackageString, String className) {
        super(classDetailsUrl, classPackageString, className);
        properties = new ArrayList<>();
    }

    public ClassDetails(ClassListItem item) {
        super(item.getClassDetailsUrl(), item.getClassPackageString(), item.getClassName());
        properties = new ArrayList<>();
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        if(documentation != null) {
            this.documentation = documentation;
        } else {
            this.documentation = "Documentation missing";
        }
    }

    public List<PropertyDetails> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyDetails> properties) {
        this.properties = properties;
    }

    public void addProperty(PropertyDetails property) {
        this.properties.add(property);
    }

    public String toString() {
        return getClassName();
    }
}
