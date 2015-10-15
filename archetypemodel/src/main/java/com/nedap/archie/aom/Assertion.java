package com.nedap.archie.aom;

/**
 * This should be an assertion, but for now it will contain its text-content
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
