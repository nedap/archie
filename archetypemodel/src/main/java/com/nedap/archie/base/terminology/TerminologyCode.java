package com.nedap.archie.base.terminology;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datatypes.TerminologyId;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class TerminologyCode extends CodePhrase {
    private String terminologyVersion;

    public TerminologyCode() {
        super();
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
            result.setTerminologyId(matcher.group("terminologyId"));
            result.setCodeString(matcher.group("codeString"));
            result.setTerminologyVersion(matcher.group("terminologyVersion"));

        } else {
            result.setCodeString(terminologyString);
        }
        return result;
    }

    public void setTerminologyId(String terminologyIdString) {
        TerminologyId terminologyId = new TerminologyId();
        terminologyId.setValue(terminologyIdString);
        setTerminologyId(terminologyId);
    }

    public String getTerminologyVersion() {
        return terminologyVersion;
    }

    public void setTerminologyVersion(String terminologyVersion) {
        this.terminologyVersion = terminologyVersion;
    }

    public String getTerminologyIdString() {
        TerminologyId terminologyId = getTerminologyId();
        return terminologyId == null ? null : terminologyId.getValue();
    }

    public String toString() {
        return terminologyVersion == null ?
                "[" + getTerminologyId() + "::" + getCodeString() + "]" :
                "[" + getTerminologyId() + "(" + terminologyVersion + ")::" + getCodeString() + "]";
    }
}
