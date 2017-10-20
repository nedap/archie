package com.nedap.archie.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nedap.archie.adlparser.treewalkers.TemporalConstraintParser;
import com.nedap.archie.datetime.DateTimeFormatters;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

/**
 * Created by pieter.bos on 30/09/16.
 */
public class DateTimeSerializer extends JsonSerializer<TemporalAccessor> {


    @Override
    public void serialize(TemporalAccessor temporalAccessor, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if(temporalAccessor == null) {
            jsonGenerator.writeString("");
        }
        if(temporalAccessor.isSupported(ChronoField.MICRO_OF_SECOND) && temporalAccessor.get(ChronoField.MICRO_OF_SECOND) != 0l) {
            jsonGenerator.writeString(DateTimeFormatters.ISO_8601_DATE_TIME.format(temporalAccessor));
        } else {
            jsonGenerator.writeString(DateTimeFormatters.ISO_8601_DATE_TIME_WITHOUT_MICROS.format(temporalAccessor));
        }
    }

}
