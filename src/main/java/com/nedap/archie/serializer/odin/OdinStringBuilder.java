package com.nedap.archie.serializer.odin;

import java.net.URI;

/**
 * @author markopi
 */
public class OdinStringBuilder {

    private final StructureStringBuilder builder;


    public OdinStringBuilder(StructureStringBuilder builder) {
        this.builder = builder;
    }

    public OdinStringBuilder() {
        this(new StructureStringBuilder());
    }

    public static String quoteText(String str) {
        return "\"" + escapeText(str) + "\"";
    }

    static String quoteChar(char c) {
        return "\'" + escapeText(Character.toString(c)) + "\'";
    }

    private static String escapeText(String str) {
        return str.replace("\\", "\\\\").
                replace("\"", "\\\"");
    }

    public OdinStringBuilder append(Object str) {
        builder.append(str);
        return this;
    }

    public OdinStringBuilder text(String str) {
        return append(quoteText(str));
    }

    public OdinStringBuilder tryNewLine() {
        builder.tryNewLine();
        return this;
    }

    public OdinStringBuilder newline() {
        builder.newline();
        return this;
    }

    public OdinStringBuilder indent() {
        builder.indent();
        return this;
    }

    public OdinStringBuilder newIndentedline() {
        return indent().newline();
    }

    public OdinStringBuilder unindent() {
        builder.unindent();
        return this;
    }

    public OdinStringBuilder newUnindentedLine() {
        return unindent().newline();
    }

    public OdinStringBuilder character(char c) {
        return append(quoteChar(c));
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    public OdinStringBuilder number(Number n) {
        return append(n);

    }

    public OdinStringBuilder bool(boolean bool) {
        return append(bool ? "True" : "False");
    }

    public OdinStringBuilder uri(URI uri) {
        return append(uri.toString());
    }
}
