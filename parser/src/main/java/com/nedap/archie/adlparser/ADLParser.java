package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.*;
import com.nedap.archie.aom.Archetype;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

public class ADLParser {


    public static Archetype parse(String adl) throws IOException {
        return parse(new ANTLRInputStream(adl));
    }

    public static Archetype parse(InputStream stream) throws IOException {
        return parse(new ANTLRInputStream(stream));
    }

    public static Archetype parse(CharStream stream) {
        Lexer lexer = new adlLexer(stream);
        adlParser parser = new adlParser(new CommonTokenStream(lexer));
        adlParser.AdlContext tree = parser.adl(); // parse
        ADLTreeWalker walker = new ADLTreeWalker();
        return walker.parse(tree);
    }

}