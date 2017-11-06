package com.nedap.archie.adlparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 19/10/15.
 */
public class ADLParserErrors {

    private static final Logger logger = LoggerFactory.getLogger(ADLParserErrors.class);

    private List<ADLParserMessage> errors = new ArrayList<>();
    private List<ADLParserMessage> warnings = new ArrayList<>();

    public void addError(String error) {
        errors.add(new ADLParserMessage(error));
    }

    public void addWarning(String error) {
        warnings.add(new ADLParserMessage(error));
    }

    public void logToLogger() {
        for(ADLParserMessage message:warnings) {
            logger.warn(message.getMessage());
        }
        for(ADLParserMessage message:errors) {
            logger.error(message.getMessage());
        }
    }

    public List<ADLParserMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<ADLParserMessage> errors) {
        this.errors = errors;
    }

    public List<ADLParserMessage> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<ADLParserMessage> warnings) {
        this.warnings = warnings;
    }

    public boolean hasNoMessages() {
        return this.getErrors().isEmpty() && this.getWarnings().isEmpty();
    }

    public boolean hasNoErrors() {
        return this.getErrors().isEmpty();
    }

    public boolean hasErrors() {
        return !getErrors().isEmpty();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        append(result, "Warning", warnings);
        append(result, "Error", errors);
        return result.toString();
    }

    private void append(StringBuilder result, String level, List<ADLParserMessage> messages) {
        for(ADLParserMessage message:messages) {
            result.append(level);
            result.append(": ");
            result.append(message.getMessage());
            result.append("\n");
        }
    }
}
