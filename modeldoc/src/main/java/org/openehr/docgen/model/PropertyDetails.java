package org.openehr.docgen.model;

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

public class PropertyDetails {
    private String name;
    private String documentation;
    private String type;
    private String existence;
    private String cardinality;

    public PropertyDetails(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(this.name != null) {
            this.name = name;
        } else {
            this.name = "Not specified";
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if(type != null) {
            this.type = type;
        } else {
            this.type = "Not specified";
        }
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

    public String getCardinality() {
        return cardinality;
    }

    public void setCardinality(String cardinality) {
        if(cardinality != null) {
            this.cardinality = cardinality;
        } else {
            this.cardinality = "N/A";
        }
    }

    public String getExistence() {
        return this.existence;
    }

    public void setExistence(String existence) {
        if(existence != null) {
            this.existence = existence;
        } else {
            this.existence = "Unknown";
        }
    }
}
