package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datatypes.ObjectRef;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class Entry extends ContentItem {

    private CodePhrase language;
    private CodePhrase encoding;
    @Nullable
    private ObjectRef workflowId;

    public CodePhrase getLanguage() {
        return language;
    }

    public void setLanguage(CodePhrase language) {
        this.language = language;
    }

    public CodePhrase getEncoding() {
        return encoding;
    }

    public void setEncoding(CodePhrase encoding) {
        this.encoding = encoding;
    }

    @Nullable
    public ObjectRef getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(@Nullable ObjectRef workflowId) {
        this.workflowId = workflowId;
    }
}
