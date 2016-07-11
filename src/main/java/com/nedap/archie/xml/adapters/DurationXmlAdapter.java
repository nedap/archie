package com.nedap.archie.xml.adapters;

import com.nedap.archie.adlparser.treewalkers.TemporalConstraintParser;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.temporal.TemporalAmount;

/**
 * Created by pieter.bos on 30/06/16.
 */
public class DurationXmlAdapter extends XmlAdapter<String, TemporalAmount> {

    @Override
    public TemporalAmount unmarshal(String stringValue) {
        return stringValue != null? TemporalConstraintParser.parseDurationValue(stringValue):null;
    }

    @Override
    public String marshal(TemporalAmount value) {
        return value.toString();//java toString of Period and Duration is the ISO-8601 format
    }
}