package com.nedap.archie.adlparser;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pieter.bos on 19/10/15.
 */
public class ADLErrorListener implements ANTLRErrorListener {

    private static final Logger logger = LoggerFactory.getLogger(ADLErrorListener.class);

    private List<String> errors = new ArrayList<>();
    private List<String> warnings = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        errors.add(String.format("syntax error at %d:%d: %s. msg: %s", line, charPositionInLine, offendingSymbol, msg));
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        warnings.add(String.format("FULL AMBIGUITY: %n-%n, exact: %b", startIndex, stopIndex, exact));
    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        logger.warn("FULL CONTEXT: {}-{}, alts: {}", startIndex, stopIndex, conflictingAlts);
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        logger.warn("CONTEXT SENSITIVITY: {}-{}, prediction: {}", startIndex, stopIndex, prediction);
    }

    public List<String> getErrors() {
        return errors;
    }
}
