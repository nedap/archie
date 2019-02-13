package org.openehr.odin.jackson.serializers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.nedap.archie.base.terminology.TerminologyCode;

public class TermCodeAsStringConverter implements Converter<TerminologyCode, String> {
    @Override
    public String convert(TerminologyCode value) {
        return value.getCodeString();
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(TerminologyCode.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }
}
