package com.nedap.archie.serializer.odin;

import java.util.Arrays;

/**
 * @author markopi
 */
public class StructureStringBuilder implements StructuredStringAppendable {
    private static final int INDENTATION_SIZE = 4;

    private final StringBuilder builder = new StringBuilder();
    private int indentDepth = 0;
    private boolean startOfLine = true;
    private int startOfLineIndex=0;

    public StructureStringBuilder() {
    }

    @Override
    public StructureStringBuilder append(Object str) {
        builder.append(str);
        startOfLine = false;
        return this;
    }

    @Override
    public StructureStringBuilder tryNewLine() {
        if (!startOfLine) {
            return newline();
        } else {
            resetLineIndentation();
        }
        return this;
    }

    private void resetLineIndentation() {
        builder.setLength(startOfLineIndex);
        appendIndentation();
    }

    private void appendIndentation() {
        builder.append(padding(' ', indentDepth * INDENTATION_SIZE));
    }

    @Override
    public StructureStringBuilder newline() {
        builder.append("\n");
        startOfLineIndex = builder.length();
        appendIndentation();
        startOfLine = true;
        return this;
    }

    @Override
    public StructureStringBuilder indent() {
        indentDepth++;
        return this;
    }

    @Override
    public StructureStringBuilder newIndentedLine() {
        return indent().newline();
    }

    @Override
    public StructureStringBuilder unindent() {
        indentDepth--;
        if (indentDepth < 0) throw new AssertionError("Already on root indentation");
        if (startOfLine) {
            resetLineIndentation();
        }

        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    @Override
    public int mark() {
        return builder.length();
    }

    @Override
    public void revert(int previousMark) {
        builder.setLength(previousMark);
    }

    public static String padRight(String str, int size) {
        return padRight(str, size, ' ');
    }

    private static String padRight(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        return str + padding(padChar, pads);
    }

    private static String padding(char padChar, int repeat) throws IndexOutOfBoundsException {
        final char[] buf = new char[repeat];
        Arrays.fill(buf, padChar);
        return new String(buf);
    }

}
