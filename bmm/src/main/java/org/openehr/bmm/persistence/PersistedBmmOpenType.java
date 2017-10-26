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

import org.openehr.bmm.core.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Open type reference to a single type parameter, i.e. typically 'T', 'V', 'K' etc. The parameter must be in the
 * type declaration of the owning BMM_class.
 *
 * Created by cnanjo on 4/11/16.
 */
public class PersistedBmmOpenType extends PersistedBmmType<BmmOpenType> implements Serializable {

    public static final String P_BMM_OPEN_TYPE = "P_BMM_OPEN_TYPE";

    public PersistedBmmOpenType(String type) {
        this.type = type;
    }

    /**
     * Simple type parameter as a single letter like 'T', 'G' etc.
     */
    private String type;

    /**
     * Returns simple type parameter as a single letter like 'T', 'G' etc.
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets simple type parameter as a single letter like 'T', 'G' etc.
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
        if(classDefinition instanceof BmmGenericClass && ((BmmGenericClass)classDefinition).getGenericParameter(type) != null) {
            BmmOpenType openType = new BmmOpenType();
            openType.setGenericConstraint(((BmmGenericClass)classDefinition).getGenericParameter(type));
            setBmmType(openType);
        } else {
            throw new RuntimeException("Unable to initialize BmmOpenType of type " + type + ". Have you defined generic parameters in the class definition " + classDefinition.getName() + " for type " + type + "?");
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

    /**
     * Flattened list of type names making up full type.
     * @return
     */
    @Override
    public List<String> flattenedTypeList() {
        List<String> retVal = new ArrayList<>();
        retVal.add(type);
        return retVal;
    }
}
