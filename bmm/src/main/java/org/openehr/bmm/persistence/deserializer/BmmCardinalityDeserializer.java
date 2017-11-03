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

import org.openehr.bmm.BmmMultiplicityInterval;
import org.openehr.bmm.persistence.PersistedBmmContainerProperty;
import org.openehr.bmm.persistence.PersistedBmmSchema;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.IntegerIntervalObject;
import org.openehr.odin.OdinAttribute;

public class BmmCardinalityDeserializer {
    public BmmMultiplicityInterval deserialize(OdinAttribute odinCardinalityAttribute) {
        BmmMultiplicityInterval returnValue = null;
        if(odinCardinalityAttribute != null) {
            IntegerIntervalObject odinCardinality = (IntegerIntervalObject) odinCardinalityAttribute.getChildren().get(0);
            Integer low = null;
            Integer high = null;
            if (odinCardinality.getLow() != null) {
                low = odinCardinality.getLow().getAsInteger();
            }
            if (odinCardinality.getHigh() != null) {
                high = odinCardinality.getHigh().getAsInteger();
            }
            returnValue = new BmmMultiplicityInterval(odinCardinality);
        }
        return returnValue;
    }
}
