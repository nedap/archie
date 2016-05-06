package com.nedap.archie.rm.datavalues;


import com.nedap.archie.rm.datatypes.CodePhrase;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DV_TEXT", propOrder = {
        "value",
        "hyperlink",
        "formatting",
        "mappings",
        "language",
        "encoding"
})
public class DvText extends DataValue implements SingleValuedDataValue<String> {


    private String value;
    @Nullable
    private DvURI hyperlink;
    @Nullable
    private String formatting;
    @Nullable
    private List<TermMapping> mappings = new ArrayList<>();
    @Nullable
    private CodePhrase language;
    @Nullable
    private CodePhrase encoding;


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

    public DvURI getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(DvURI hyperlink) {
        this.hyperlink = hyperlink;
    }

    @Nullable
    public String getFormatting() {
        return formatting;
    }

    public void setFormatting(@Nullable String formatting) {
        this.formatting = formatting;
    }

    public CodePhrase getLanguage() {
        return language;
    }

    public void setLanguage(CodePhrase language) {
        this.language = language;
    }

    public CodePhrase getEncoding() {
        return encoding;
    }

    public void setEncoding(CodePhrase encoding) {
        this.encoding = encoding;
    }
}
