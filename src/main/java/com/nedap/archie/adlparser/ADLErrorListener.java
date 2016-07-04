package com.nedap.archie.adlparser;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.BitSet;

import org.antlr.v4.runtime.misc.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pieter.bos on 19/10/15.
 */
public class ADLErrorListener implements ANTLRErrorListener {

    private static final Logger logger = LoggerFactory.getLogger(ADLErrorListener.class);
    private final ADLParserErrors errors;

    public ADLErrorListener(ADLParserErrors errors) {
        this.errors = errors;
    }

    @Override
    public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        String error = String.format("syntax error at %d:%d: %s. msg: %s", line, charPositionInLine, offendingSymbol, msg);
        logger.warn(error);
        errors.addError(error);
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        String input = recognizer.getInputStream().getText(new Interval(startIndex, stopIndex));
        String warning = String.format("FULL AMBIGUITY: %d-%d, exact: %b, input: %s", startIndex, stopIndex, exact, input);
        logger.warn(warning);
        errors.addWarning(warning);
    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        String input = recognizer.getInputStream().getText(new Interval(startIndex, stopIndex));
        logger.warn("FULL CONTEXT: {}-{}, alts: {}, {}", startIndex, stopIndex, conflictingAlts, input);
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        logger.warn("CONTEXT SENSITIVITY: {}-{}, prediction: {}", startIndex, stopIndex, prediction);
    }

    public ADLParserErrors getErrors() {
        return errors;
    }
}
