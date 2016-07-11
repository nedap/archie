package com.nedap.archie.xml.adapters;

import com.nedap.archie.base.Interval;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.temporal.TemporalAmount;

/**
 * Created by pieter.bos on 28/07/16.
 */
public class DurationIntervalXmlAdapter extends AbstractIntervalAdapter {

    public DurationIntervalXmlAdapter() {
        super(new DurationXmlAdapter());
    }
}
