package com.nedap.archie.serializer.odin;

/**
 * @author markopi
 */
public interface StructuredStringAppendable {
    StructuredStringAppendable append(Object str);

    StructuredStringAppendable tryNewLine();

    StructuredStringAppendable newline();

    StructuredStringAppendable indent();

    StructuredStringAppendable newIndentedLine();

    StructuredStringAppendable unindent();

    int mark();

    void revert(int previousMark);

}
