package com.nedap.archie.adlparser.odin;

import com.fasterxml.jackson.databind.JavaType;
import com.nedap.archie.adlparser.antlr.AdlLexer;
import com.nedap.archie.adlparser.antlr.AdlParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;

import java.io.IOException;

/**
 * Created by pieter.bos on 02/11/15.
 */
public class OdinObjectParser {

    public static <T> T convert(AdlParser.Odin_textContext odin, Class<T> clazz) {
        try {
            return OdinToJsonConverter.getObjectMapper().readValue(new OdinToJsonConverter().convert(odin), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T convert(AdlParser.Odin_textContext odin, JavaType clazz) {
        try {
            return OdinToJsonConverter.getObjectMapper().readValue(new OdinToJsonConverter().convert(odin), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T convert(String odin, Class<T> clazz) {
        AdlLexer adlLexer = new AdlLexer(new ANTLRInputStream(odin));
        AdlParser parser = new AdlParser(new CommonTokenStream(adlLexer));
        return convert(parser.odin_text(), clazz);
    }

    public static <T> T convert(String odin, JavaType clazz) {
        AdlLexer adlLexer = new AdlLexer(new ANTLRInputStream(odin));
        AdlParser parser = new AdlParser(new CommonTokenStream(adlLexer));
        return convert(parser.odin_text(), clazz);
    }
}
