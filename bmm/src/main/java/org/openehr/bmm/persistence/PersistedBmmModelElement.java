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

import java.io.Serializable;

/**
 * Persistent form of BMM_MODEL_ELEMENT.
 *
 * Created by cnanjo on 4/11/16.
 */
public abstract class PersistedBmmModelElement implements Serializable {

    /**
     * Optional documentation of this element.
     */
    private String documentation;

    /**
     * Method returns the documentation associated with this element.
     *
     * @return
     */
    public String getDocumentation() {
        return documentation;
    }

    /**
     * Method sets the documentation associated with this element.
     *
     * @param documentation
     */
    public void setDocumentation(String documentation) {
        if(documentation != null) {
            this.documentation = generateSafeString(documentation);
        }
    }

    public String generateSafeString(String documentation) {
        String safeString = documentation;
        safeString = safeString.replaceAll("\"", "'");
        safeString = safeString.replaceAll("<", "&lt;");
        safeString = safeString.replaceAll(">", "&gt;");
        safeString = safeString.replaceAll("/", "&#47;");
        return safeString;
    }
}
