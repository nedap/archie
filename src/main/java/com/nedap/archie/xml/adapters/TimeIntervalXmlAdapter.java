package com.nedap.archie.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by pieter.bos on 28/07/16.
 */
public class TimeIntervalXmlAdapter extends AbstractIntervalAdapter {
    public TimeIntervalXmlAdapter() {
        super(new TimeXmlAdapter());
    }
}
