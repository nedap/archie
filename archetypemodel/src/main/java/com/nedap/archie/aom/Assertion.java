package com.nedap.archie.aom;

/**
 * Assertion object. For now contains all the text of the assertions, and not the parsed form
 * Created by pieter.bos on 15/10/15.
 */
public class Assertion extends ArchetypeModelObject {
    private String text;

    public Assertion() {

    }

    public Assertion(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
