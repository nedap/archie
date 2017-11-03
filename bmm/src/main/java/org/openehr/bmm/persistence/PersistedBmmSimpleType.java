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

import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmSimpleType;
import org.openehr.bmm.core.BmmType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistent form of BMM_SIMPLE_TYPE.
 *
 * Created by cnanjo on 4/11/16.
 */
public class PersistedBmmSimpleType extends PersistedBmmType<BmmSimpleType> implements Serializable {

    public static final String P_BMM_SIMPLE_TYPE = "P_BMM_SIMPLE_TYPE";

    public PersistedBmmSimpleType() {
    }

    public PersistedBmmSimpleType(String type) {
        this.type = type;
    }

    /**
     * Name of type - must be a simple class name.
     */
    private String type;

    /**
     * Returns the name of type - must be a simple class name.
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the name of type - must be a simple class name.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Create appropriate BMM_ object; defined in descendants.
     *
     * @param schema
     * @param classDefinition
     */
    @Override
    public void createBmmType(BmmModel schema, BmmClass classDefinition) {
        BmmClass baseClass = schema.getClassDefinition(this.type);
        if(baseClass == null) {
            throw new RuntimeException("BmmClass " + this.type + " is not defined in this model");
        } else {
            BmmSimpleType simpleType = new BmmSimpleType();
            simpleType.setBaseClass(baseClass);
            setBmmType(simpleType);
        }
    }

    /**
     * Formal name of the type for display.
     *
     * @return
     */
    @Override
    public String asTypeString() {
        return type;
    }

    public List<String> flattenedTypeList() {
        List<String> retVal = new ArrayList<>();
        retVal.add(type);
        return retVal;
    }
}
