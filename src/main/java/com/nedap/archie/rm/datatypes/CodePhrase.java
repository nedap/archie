package com.nedap.archie.rm.datatypes;

import com.nedap.archie.rm.RMObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: reuse archetype model TerminologyCode? Thing is, that doesn't constrain as nicely with the archetype model...
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CODE_PHRASE", propOrder = {
        "terminologyId",
        "codeString"
})
public class CodePhrase extends RMObject {

    @XmlElement(name = "terminology_id")
    private TerminologyId terminologyId;
    @XmlElement(name = "code_string")
    private String codeString;

    public CodePhrase() {

    }

    /**
     * Construct a code phrase in the form:
     * <br/>
     * [terminologyId::codeString]
     * <br/>
     * or
     * <br/>
     * terminologyId::codeString
     *
     * terminologyId can be just a a string, or contain a version as in  terminologyId(version)
     * @param phrase
     */
    public CodePhrase(String phrase) {
        //'[' NAME_CHAR+ ( '(' NAME_CHAR+ ')' )? '::' NAME_CHAR+ ']' ;
        Pattern pattern = Pattern.compile("\\[?(?<terminologyId>.+)(\\((?<terminologyVersion>.+)\\))?::(?<codeString>[^\\]]+)\\]?");
        Matcher matcher = pattern.matcher(phrase);

        if(matcher.matches()) {
            terminologyId = new TerminologyId(matcher.group("terminologyId"), matcher.group("terminologyVersion"));
            codeString = matcher.group("codeString");
        } else {
            terminologyId = new TerminologyId();
            terminologyId.setValue("UNKNOWN");
            codeString = phrase;//no id
        }
    }

    public TerminologyId getTerminologyId() {
        return terminologyId;
    }

    public void setTerminologyId(TerminologyId terminologyId) {
        this.terminologyId = terminologyId;
    }
    
    public String getCodeString() {
        return codeString;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString;
    }
}
