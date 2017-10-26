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
package org.openehr.utils.error;

public class ErrorDescriptor {

    private String code;
    private Integer severity;
    private String message;
    private String location;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ErrorDescriptor() {
    }

    public ErrorDescriptor(String aCode, Integer aSeverity, String aMessage, String aLoc) {
        code = aCode;
        severity = aSeverity;
        message = aMessage;
        location = aLoc;
    }

    public static ErrorDescriptor createError(String aCode, String aMessage, String aLoc) {
        return create(aCode, ErrorSeverityTypes.ERROR_TYPE_ERROR, aMessage, aLoc);
    }

    public static ErrorDescriptor createWarning(String aCode, String aMessage, String aLoc) {
        return create(aCode, ErrorSeverityTypes.ERROR_TYPE_WARNING, aMessage, aLoc);
    }

    public static ErrorDescriptor createInfo(String aCode, String aMessage, String aLoc) {
        return create(aCode, ErrorSeverityTypes.ERROR_TYPE_INFO, aMessage, aLoc);
    }

    public static ErrorDescriptor createDebug(String aCode, String aMessage, String aLoc) {
        return create(aCode, ErrorSeverityTypes.ERROR_TYPE_DEBUG, aMessage, aLoc);
    }

    public static ErrorDescriptor create(String aCode, Integer aSeverity, String aMessage, String aLoc) {
        if (ErrorSeverityTypes.isValidErrorType(aSeverity)) {
            return new ErrorDescriptor(aCode, aSeverity, aMessage, aLoc);
        } else {
            throw new IllegalArgumentException("Invalid severity code " + aSeverity);
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(ErrorSeverityTypes.errorTypeName(severity))
                .append(" ");
        if (location != null && location.trim().length() > 0) {
            builder.append(location).append(": ");
        }
        builder.append("(")
                .append(code)
                .append(") ")
                .append(message);
        return builder.toString();
    }
}
