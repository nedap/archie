package com.nedap.archie.base.terminology;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class TerminologyCode {
    private String terminologyId;
    private String terminologyVersion;
    private String codeString;

    public TerminologyCode() {
    }

    //for json-mapping
    public TerminologyCode(String terminologyId) {//todo
        this.terminologyId = terminologyId;
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
