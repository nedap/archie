package com.nedap.archie.xml.adapters;

/**
 * Created by pieter.bos on 28/07/16.
 */
public class DateIntervalXmlAdapter extends AbstractIntervalAdapter {

    public DateIntervalXmlAdapter() {
        super(new DateTimeXmlAdapter());
    }

}
