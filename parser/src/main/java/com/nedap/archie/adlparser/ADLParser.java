package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.*;
import com.nedap.archie.aom.Archetype;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

public class ADLParser {

    public Lexer lexer;
    public AdlParser parser;
    public ADLListener listener;
    public ParseTreeWalker walker;
    public AdlParser.AdlContext tree;
    public ADLErrorListener errorListener;

    public Archetype parse(String adl) throws IOException {
        return parse(new ANTLRInputStream(adl));
    }

    public Archetype parse(InputStream stream) throws IOException {
        return parse(new ANTLRInputStream(stream));
    }

    public Archetype parse(CharStream stream) {
        errorListener = new ADLErrorListener();

        lexer = new AdlLexer(stream);
        lexer.addErrorListener(errorListener);
        parser = new AdlParser(new CommonTokenStream(lexer));
        parser.addErrorListener(errorListener);
        tree = parser.adl(); // parse

        ADLListener listener = new ADLListener();
        walker= new ParseTreeWalker();
        walker.walk(listener, tree);
        return listener.getArchetype();

    }

}