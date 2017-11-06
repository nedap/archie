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
package org.openehr.utils.message;

/**
 * enum for error code management
 */
public enum MessageSeverity {
    DEBUG(9000), INFO(9001), WARNING(9002), ERROR(9003);


    private final int level;

    MessageSeverity(int level) {
        this.level = level;
    }

    /**
    /**
     * Method returns true if a valid error code is passed in as a method argument.
     *
     * @param anErrorType
     * @return
     */
    public static boolean isValidErrorType(int anErrorType) {
        return anErrorType >= DEBUG.level && anErrorType <= ERROR.level;
    }

    public int getLevel() {
        return level;
    }

}
