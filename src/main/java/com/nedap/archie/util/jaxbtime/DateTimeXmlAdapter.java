package com.nedap.archie.util.jaxbtime;

import com.nedap.archie.adlparser.treewalkers.TemporalConstraintParser;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Objects;

/**
 * Created by pieter.bos on 24/06/16.
 */
public class DateTimeXmlAdapter extends XmlAdapter<String, TemporalAccessor> {


    public DateTimeXmlAdapter() {

    }

    @Override
    public TemporalAccessor unmarshal(String stringValue) {
        return stringValue != null? TemporalConstraintParser.parseDateTimeValue(stringValue):null;
    }

    @Override
    public String marshal(TemporalAccessor value) {
        return value != null?TemporalConstraintParser.ISO_8601_DATE_TIME.format(value):null;
    }

}
