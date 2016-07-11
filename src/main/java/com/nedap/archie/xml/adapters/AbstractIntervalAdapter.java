package com.nedap.archie.xml.adapters;

import com.nedap.archie.base.Interval;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;

/**
 * Created by pieter.bos on 28/07/16.
 */
public abstract class AbstractIntervalAdapter extends XmlAdapter<Interval, Interval> {


    private final XmlAdapter innerAdapter;

    public AbstractIntervalAdapter(XmlAdapter innerAdapter) {
        this.innerAdapter = innerAdapter;
    }

    @Override
    public Interval unmarshal(Interval v) throws Exception {
        Interval result = new Interval();
        result.setLowerIncluded(v.isLowerIncluded());
        result.setUpperIncluded(v.isUpperIncluded());
        result.setLowerUnbounded(v.isLowerUnbounded());
        result.setUpperUnbounded(v.isUpperUnbounded());
        result.setLower(v.getLower() == null ? null : innerAdapter.unmarshal(v.getLower()));
        result.setUpper(v.getUpper() == null ? null : innerAdapter.unmarshal(v.getUpper()));
        return result;
    }

    @Override
    public Interval marshal(Interval v) throws Exception {
        Interval result = new Interval();
        result.setLowerIncluded(v.isLowerIncluded());
        result.setUpperIncluded(v.isUpperIncluded());
        result.setLowerUnbounded(v.isLowerUnbounded());
        result.setUpperUnbounded(v.isUpperUnbounded());

        result.setLower(v.getLower() == null ? null : innerAdapter.marshal(v.getLower()));
        result.setUpper(v.getUpper() == null ? null : innerAdapter.marshal(v.getUpper()));
        return result;
    }
}
