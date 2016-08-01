package com.nedap.archie.xml.adapters;

/**
 * Created by pieter.bos on 28/07/16.
 */
public class DurationIntervalXmlAdapter extends AbstractIntervalAdapter {

    public DurationIntervalXmlAdapter() {
        super(new DurationXmlAdapter());
    }
}
