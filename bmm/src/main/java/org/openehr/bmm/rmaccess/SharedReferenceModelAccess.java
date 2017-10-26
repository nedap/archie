package org.openehr.bmm.rmaccess;

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

import org.openehr.bmm.core.BmmModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Shared access to service interface to object model
 */
public class SharedReferenceModelAccess {
    private ReferenceModelAccess referenceModelAccess;

    /**
     * Method returns a validated top-level schema for schema id `an_id'
     * or null if none is found
     *
     * @param aReferenceModelId
     * @return
     */
    public BmmModel getReferenceModelById(String aReferenceModelId) {
        return this.referenceModelAccess.getValidModels().get(aReferenceModelId);
    }

    /**
     * Method returns all reference model IDs associated with this ReferenceModelAccess instance.
     *
     * @return
     */
    public List<String> getAllReferenceModelsIdsAsList() {
        List<String> retVal = new ArrayList<>();
        retVal.addAll(this.referenceModelAccess.getValidModels().keySet());
        return retVal;
    }

    public boolean hasReferenceModelForId(String aReferenceModelId) {
        return this.referenceModelAccess.getValidModels().containsKey(aReferenceModelId) && this.referenceModelAccess.getValidModels().get(aReferenceModelId) != null;
    }
}
