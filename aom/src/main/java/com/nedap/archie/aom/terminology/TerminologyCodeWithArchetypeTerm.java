package com.nedap.archie.aom.terminology;

/**
 * Created by pieter.bos on 22/04/16.
 */
public class TerminologyCodeWithArchetypeTerm {

    private String code;
    private ArchetypeTerm term;

    public TerminologyCodeWithArchetypeTerm(String code, ArchetypeTerm term) {
        this.code = code;
        this.term = term;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArchetypeTerm getTerm() {
        return term;
    }

    public void setTerm(ArchetypeTerm term) {
        this.term = term;
    }
}
