package com.nedap.archie.rm.datavalues;

import com.nedap.archie.rm.datatypes.CodePhrase;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlType(name = "DV_CODED_TEXT", propOrder = {
        "definingCode"
})
public class DvCodedText extends DvText {
    @XmlElement(name = "defining_code")
    private CodePhrase definingCode;

    public CodePhrase getDefiningCode() {
        return definingCode;
    }

    public void setDefiningCode(CodePhrase definingCode) {
        this.definingCode = definingCode;
    }
}
