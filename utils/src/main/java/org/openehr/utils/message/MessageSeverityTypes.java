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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Static class for error code management
 */
public class MessageSeverityTypes {
    //Definitions
    public static final Integer ERROR_TYPE_DEBUG = 9000;
    public static final Integer ERROR_TYPE_INFO = 9001;
    public static final Integer ERROR_TYPE_WARNING = 9002;
    public static final Integer ERROR_TYPE_ERROR = 9003;

    public static final String ERROR_TYPE_DEBUG_NAME = "DEBUG";
    public static final String ERROR_TYPE_INFO_NAME = "INFO";
    public static final String ERROR_TYPE_WARNING_NAME = "WARNING";
    public static final String ERROR_TYPE_ERROR_NAME = "ERROR";

    /**
     * Names of message types keyed by error type code
     */
    private static final Map<Integer,String> errorTypeNameTable = new LinkedHashMap<>();
    /**
     * Codes of message types keyed by message type name
     */
    private static final Map<String,Integer> errorTypeIdTable = new LinkedHashMap<>();
    /**
     * Names of message types
     */
    private static final List<String> errorTypeNames = new ArrayList<>();

    static {
        errorTypeIdTable.put(ERROR_TYPE_DEBUG_NAME,ERROR_TYPE_DEBUG);
        errorTypeIdTable.put(ERROR_TYPE_INFO_NAME, ERROR_TYPE_INFO);
        errorTypeIdTable.put(ERROR_TYPE_WARNING_NAME, ERROR_TYPE_WARNING);
        errorTypeIdTable.put(ERROR_TYPE_ERROR_NAME, ERROR_TYPE_ERROR);

        errorTypeNameTable.put(ERROR_TYPE_DEBUG, ERROR_TYPE_DEBUG_NAME);
        errorTypeNameTable.put(ERROR_TYPE_INFO, ERROR_TYPE_INFO_NAME);
        errorTypeNameTable.put(ERROR_TYPE_WARNING, ERROR_TYPE_WARNING_NAME);
        errorTypeNameTable.put(ERROR_TYPE_ERROR, ERROR_TYPE_ERROR_NAME);

        errorTypeNames.add(ERROR_TYPE_DEBUG_NAME);
        errorTypeNames.add(ERROR_TYPE_INFO_NAME);
        errorTypeNames.add(ERROR_TYPE_WARNING_NAME);
        errorTypeNames.add(ERROR_TYPE_ERROR_NAME);
    }

    /**
     * Method returns true if a valid error code is passed in as a method argument.
     *
     * @param anErrorType
     * @return
     */
    public static boolean isValidErrorType(int anErrorType) {
        return anErrorType >= ERROR_TYPE_DEBUG && anErrorType <= ERROR_TYPE_ERROR;
    }

    /**
     * Method returns the name associated with the given error code.
     *
     * @param anErrorType
     * @return
     */
    public static String errorTypeName(int anErrorType) {
        if(isValidErrorType(anErrorType)) {
            return errorTypeNameTable.get(anErrorType);
        } else {
            throw new IllegalArgumentException("Invalid error type " + anErrorType);
        }
    }
}
