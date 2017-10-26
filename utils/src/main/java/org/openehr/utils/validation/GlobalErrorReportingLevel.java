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
package org.openehr.utils.validation;

import org.openehr.utils.error.ErrorSeverityTypes;

public class GlobalErrorReportingLevel {
    /**
     * At this level and above, list entries are included in `as_string' and any other output function
     */
    private static Integer globalErrorReportingLevel = ErrorSeverityTypes.ERROR_TYPE_WARNING;

    public static Integer getGlobalErrorReportingLevel() {
        return globalErrorReportingLevel;
    }

    public static void setGlobalErrorReportingLevel(Integer globalErrorReportingLevel) {
        if(ErrorSeverityTypes.isValidErrorType(globalErrorReportingLevel)) {
            GlobalErrorReportingLevel.globalErrorReportingLevel = globalErrorReportingLevel;
        } else {
            throw new IllegalArgumentException("Invalid global error reporting level " + globalErrorReportingLevel);
        }
    }
}
