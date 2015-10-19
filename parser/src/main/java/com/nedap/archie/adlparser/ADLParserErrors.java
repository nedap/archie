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

    public List<ADLParserMessage> errors = new ArrayList<>();
    public List<ADLParserMessage> warnings = new ArrayList<>();

    public void addError(String error) {
        errors.add(new ADLParserMessage(error));
    }

    public void addWarning(String error) {
        errors.add(new ADLParserMessage(error));
    }

    public void logToLogger() {
        for(ADLParserMessage message:warnings) {
            logger.warn(message.getMessage());
        }
        for(ADLParserMessage message:errors) {
            logger.error(message.getMessage());
        }
    }
}
