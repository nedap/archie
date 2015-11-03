package com.nedap.archie.base.terminology;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class TerminologyCode {
    private String terminologyId;
    private String terminologyVersion;
    private String codeString;

    public TerminologyCode() {
    }

    @JsonCreator
    public static TerminologyCode createFromString(String terminologyString) {
        if(terminologyString.isEmpty()) {
            return null;
        }
        //'[' NAME_CHAR+ ( '(' NAME_CHAR+ ')' )? '::' NAME_CHAR+ ']' ;
        Pattern pattern = Pattern.compile("\\[(?<terminologyId>.+)(\\((?<terminologyVersion>.+)\\))?::(?<codeString>.+)\\]");
        Matcher matcher = pattern.matcher(terminologyString);
        TerminologyCode result = new TerminologyCode();
        if(matcher.matches()) {
            result.terminologyId = matcher.group("terminologyId");
            result.codeString = matcher.group("codeString");
            result.terminologyVersion = matcher.group("terminologyVersion");

        } else {
            result.codeString = terminologyString;
        }
        return result;
    }

    public String getTerminologyId() {
        return terminologyId;
    }

    public void setTerminologyId(String terminologyId) {
        this.terminologyId = terminologyId;
    }

    public String getTerminologyVersion() {
        return terminologyVersion;
    }

    public void setTerminologyVersion(String terminologyVersion) {
        this.terminologyVersion = terminologyVersion;
    }

    public String getCodeString() {
        return codeString;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString;
    }
}
