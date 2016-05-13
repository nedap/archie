package com.nedap.archie.rm.datavalues;

import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.datatypes.CodePhrase;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TERM_MAPPING", propOrder = {
        "match",
        "purpose",
        "target"
})
public class TermMapping extends RMObject {
    /**
     * This is an interesting one, that could be implemented with an enum
     * //TODO: look at it
     * < = narrower term
     * = = equals term
     * > = broader term
     * ? = the kind of mapping is unknown
     */
    private char match = '?';
    @Nullable
    private DvCodedText purpose;
    private CodePhrase target;

    public char getMatch() {
        return match;
    }

    public void setMatch(char match) {
        this.match = match;
    }

    @Nullable
    public DvCodedText getPurpose() {
        return purpose;
    }

    public void setPurpose(@Nullable DvCodedText purpose) {
        this.purpose = purpose;
    }

    public CodePhrase getTarget() {
        return target;
    }

    public void setTarget(CodePhrase target) {
        this.target = target;
    }
}
