package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.*;
import com.nedap.archie.adlparser.modelconstraints.ModelConstraintImposer;
import com.nedap.archie.adlparser.treewalkers.ADLListener;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.utils.ArchetypeParsePostProcesser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.io.input.BOMInputStream;

import java.io.IOException;
import java.io.InputStream;


/**
 * Parses ADL files to Archetype objects.
 *
 */
public class ADLParser {

    private ModelConstraintImposer modelConstraintImposer;
    private ADLParserErrors errors;

    private Lexer lexer;
    private AdlParser parser;
    private ADLListener listener;
    private ParseTreeWalker walker;
    private AdlParser.AdlContext tree;
    public ADLErrorListener errorListener;

    /**
     * If true, write errors to the console, if false, do not
     */
    private boolean logEnabled = true;

    public ADLParser() {

    }

    public ADLParser(ModelConstraintImposer modelConstraintImposer) {
        this.modelConstraintImposer = modelConstraintImposer;
    }


    public Archetype parse(String adl) throws IOException {
        return parse(new ANTLRInputStream(adl));
    }

    public Archetype parse(InputStream stream) throws IOException {
        return parse(new ANTLRInputStream(new BOMInputStream(stream)));
    }

    public Archetype parse(CharStream stream) {

        errors = new ADLParserErrors();
        errorListener = new ADLErrorListener(errors);
        errorListener.setLogEnabled(logEnabled);

        lexer = new AdlLexer(stream);
        lexer.addErrorListener(errorListener);
        parser = new AdlParser(new CommonTokenStream(lexer));
        parser.addErrorListener(errorListener);
        tree = parser.adl(); // parse

        ADLListener listener = new ADLListener(errors);
        walker= new ParseTreeWalker();
        walker.walk(listener, tree);
        Archetype result = listener.getArchetype();
        //set some values that are not directly in ODIN or ADL
        ArchetypeParsePostProcesser.fixArchetype(result);

        if(modelConstraintImposer != null && result.getDefinition() != null) {
            modelConstraintImposer.imposeConstraints(result.getDefinition());
        }
        return result;

    }

    public ADLParserErrors getErrors() {
        return errors;
    }

    public Lexer getLexer() {
        return lexer;
    }

    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
    }

    public AdlParser getParser() {
        return parser;
    }

    public void setParser(AdlParser parser) {
        this.parser = parser;
    }

    public ADLListener getListener() {
        return listener;
    }

    public void setListener(ADLListener listener) {
        this.listener = listener;
    }

    public ParseTreeWalker getWalker() {
        return walker;
    }

    public void setWalker(ParseTreeWalker walker) {
        this.walker = walker;
    }

    public AdlParser.AdlContext getTree() {
        return tree;
    }

    public void setTree(AdlParser.AdlContext tree) {
        this.tree = tree;
    }

    public boolean isLogEnabled() {
        return logEnabled;
    }

    public void setLogEnabled(boolean logEnabled) {
        this.logEnabled = logEnabled;
    }
}