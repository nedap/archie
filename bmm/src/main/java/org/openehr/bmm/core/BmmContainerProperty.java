package org.openehr.bmm.core;

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

import com.nedap.archie.base.MultiplicityInterval;
import org.openehr.bmm.BmmConstants;
import org.openehr.bmm.BmmMultiplicityInterval;
import org.openehr.odin.utils.OdinSerializationUtils;

/**
 * Subtype of BMM_PROPERTY that represents a container type based on one of the inbuilt types List <>, Set <>, Array <>.
 *
 * Created by cnanjo on 4/11/16.
 */
public class BmmContainerProperty extends BmmProperty<BmmContainerType> {

    /**
     * The cardinality of this container.
     */
    private MultiplicityInterval cardinality;

    public BmmContainerProperty() {
    }

    public BmmContainerProperty(String name, BmmContainerType type) {
        super(name, type);
    }

    /**
     * Returns the cardinality of this container.
     *
     * @return
     */
    public MultiplicityInterval getCardinality() {
        return cardinality;
    }

    /**
     * Sets the cardinality of this container.
     *
     * @param cardinality
     */
    public void setCardinality(MultiplicityInterval cardinality) {
        this.cardinality = cardinality;
    }

}
