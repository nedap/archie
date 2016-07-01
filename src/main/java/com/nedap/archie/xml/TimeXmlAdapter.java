package com.nedap.archie.xml;

import com.nedap.archie.adlparser.treewalkers.TemporalConstraintParser;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

/**
 * Created by pieter.bos on 24/06/16.
 */
public class TimeXmlAdapter extends XmlAdapter<String, TemporalAccessor> {


    @Override
    public TemporalAccessor unmarshal(String stringValue) {
        return stringValue != null? TemporalConstraintParser.parseTimeValue(stringValue):null;
    }

    @Override
    public String marshal(TemporalAccessor value) {
        return value.toString();
    }
}
