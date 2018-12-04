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

    /**
     * Mark the position in the stream. After marking, clearMark or revert MUST be called to commit the marked string.
     *
     * @return an integer uniquely identifying the marked position, valid unti either revert or clearMark has been called
     */
    int mark();

    void revert(int previousMark);

    void clearMark();

    StructuredStringAppendable ensureSpace();
}
