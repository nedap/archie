package com.nedap.archie.rm.datavalues;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class DvText extends DataValue implements SingleValuedDataValue<String> {

    private List<TermMapping> mappings = new ArrayList<>();
    private String value;

    public List<TermMapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<TermMapping> mappings) {
        this.mappings = mappings;
    }

    public void addMapping(TermMapping mapping) {
        this.mappings.add(mapping);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
