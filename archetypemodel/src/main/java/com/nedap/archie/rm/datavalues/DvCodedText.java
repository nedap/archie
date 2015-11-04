package com.nedap.archie.rm.datavalues;

import com.nedap.archie.rm.datatypes.CodePhrase;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class DvCodedText extends DvText {
    private CodePhrase definingCode;

    public CodePhrase getDefiningCode() {
        return definingCode;
    }

    public void setDefiningCode(CodePhrase definingCode) {
        this.definingCode = definingCode;
    }
}
