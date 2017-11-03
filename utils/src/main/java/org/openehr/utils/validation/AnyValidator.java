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

import com.google.common.collect.Lists;
import org.openehr.utils.message.MessageCode;
import org.openehr.utils.message.MessageLogger;

import java.util.List;

/**
 * Base class for OpenEHR model validators
 */
public abstract class AnyValidator {
    /**
     * True if validation succeeded
     */
    protected boolean passed = true;

    /**
     * Error output of validator - things that must be corrected
     */
    private MessageLogger messageLogger = new MessageLogger();

    /**
     * Flag indicating that all validation passed. This flag is set to true in two cases:
     * 1. validation() was invoked and resulted in no messages with a severity of ERROR_TYPE_ERROR.
     * 2. merge() was invoked and the merged content contained no errors.
     *
     * @return
     */
    public boolean hasPassed() {
        return passed;
    }

    /**
     * Sets flag to indicate that all validation has passed.
     *
     * @param passed
     */
    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    /**
     * Returns the MessageLogger for this validator
     *
     * @return
     */
    public MessageLogger getMessageLogger() {
        if(messageLogger != null) {
            return messageLogger;
        } else {
            messageLogger = new MessageLogger();
            return messageLogger;
        }
    }

    public int getMessageCount() {
        return messageLogger.size();
    }

    public String getErrorStrings() {
        return messageLogger.toString();
    }

    /**
     * Resets the state of this validator for a new run.
     */
    public void reset() {
        passed = true;
        messageLogger = new MessageLogger();
    }

    /**
     * Returns 'True' if error cache is non-empty and has errors.
     *
     * @return
     */
    public boolean hasErrors() {
        return messageLogger.hasErrors();
    }

    /**
     * Returns true if error cache has no errors.
     * @return
     */
    public boolean hasNoErrors() {
        return !messageLogger.hasErrors();
    }

    /**
     * Returns true if the cache has an error with the given code argument.
     *
     * @param aCode
     * @return
     */
    public boolean hasError(MessageCode aCode) {
        return messageLogger.hasError(aCode);
    }

    /**
     * Returns true if the cache has a warning with the given code argument.
     * @param aCode
     * @return
     */
    public boolean hasWarning(MessageCode aCode) {
        return messageLogger.hasWarning(aCode);
    }

    /**
     * Returns true if the cache has an Info message with the given code argument.
     * @param aCode
     * @return
     */
    public boolean hasInfo(MessageCode aCode) {
        return messageLogger.hasInfo(aCode);
    }

    /**
     * Merges the content of the other error accumulator and, if an error is present
     * in the other error accumulator, hasPassed() will return false.
     *
     * @param other
     */
    public void mergeErrors(MessageLogger other) {
        messageLogger.append(other);
        passed = passed && !(other.hasErrors());
    }

    /**
     * Append an error with key `a_key' and `args' array to the `errors' string
     * @param aKey
     * @param args
     */
    public void addError(MessageCode aKey,String... args) {
        addErrorWithLocation(aKey, Lists.newArrayList(args), "");
    }

    /**
     * Append a warning with key `a_key' and `args' array to the `warnings' string
     * @param aKey
     * @param args
     */
    public void addWarning(MessageCode aKey, String... args) {
        addWarningWithLocation(aKey, Lists.newArrayList(args), "");
    }

    /**
     * Append an information message with key `a_key' and `args' array to the `information' string
     * @param aKey
     * @param args
     */
    public void addInfo(MessageCode aKey, String... args) {
        addInfoWithLocation(aKey, Lists.newArrayList(args), "");
    }

    /**
     * Append an error with key `a_key' and `args' array to the `errors' string
     * @param aKey
     * @param args
     * @param aLocation
     */
    public void addErrorWithLocation(MessageCode aKey, List<String> args, String aLocation) {
        messageLogger.addErrorWithLocation(aKey, aLocation, args);
    }

    /**
     * Append a warning with key `a_key' and `args' array to the `warnings' string
     * @param aKey
     * @param args
     * @param aLocation
     */
    public void addWarningWithLocation(MessageCode aKey, List<String> args, String aLocation) {
        messageLogger.addWarningWithLocation(aKey, aLocation, args);
    }

    /**
     * Append an information message with key `a_key' and `args' array to the `information' string
     * @param aKey
     * @param args
     * @param aLocation
     */
    public void addInfoWithLocation(MessageCode aKey, List<String> args, String aLocation) {
        messageLogger.addInfoWithLocation(aKey, aLocation, args);
    }

    public boolean readyToValidate() {
        return passed;
    }

    /**
     * Runs validation routine
     */
    public void validate() {
        if(readyToValidate()) {
            doValidation();
            if(messageLogger.hasErrorsOrWarnings()) {
                passed = false;
            }
        } else {
            throw new IllegalStateException("Error - not ready to validate");
        }
    }

    /**
     * Implementation of validation routine deferred to subclasses
     */
    public abstract void doValidation();
}
