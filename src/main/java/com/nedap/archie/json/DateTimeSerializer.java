package com.nedap.archie.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.nedap.archie.adlparser.treewalkers.TemporalConstraintParser;

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

    private static final DateTimeFormatter ISO_8601_DATE_TIME = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .appendValue(ChronoField.YEAR)
        .appendLiteral('-')
        .appendValue(ChronoField.MONTH_OF_YEAR, 2)
        .appendLiteral('-')
        .appendValue(ChronoField.DAY_OF_MONTH, 2)
        .appendLiteral('T')
        .appendValue(ChronoField.HOUR_OF_DAY, 2)
        .optionalStart()
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .optionalStart()
                .appendLiteral(':')
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .optionalStart()
                    .appendLiteral(',')
                    .appendFraction(ChronoField.MICRO_OF_SECOND, 1, 6, false)
                .optionalEnd()
            .optionalEnd()
        .optionalEnd()
        .optionalStart()
            .appendOffset("+HHMM", "Z")
        .optionalEnd()
        .toFormatter();

    public static final DateTimeFormatter ISO_8601_DATE_TIME_WITHOUT_MICROS = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .appendValue(ChronoField.YEAR)
        .appendLiteral('-')
        .appendValue(ChronoField.MONTH_OF_YEAR, 2)
        .appendLiteral('-')
        .appendValue(ChronoField.DAY_OF_MONTH, 2)
        .appendLiteral('T')
        .appendValue(ChronoField.HOUR_OF_DAY, 2)
        .optionalStart()
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .optionalStart()
                .appendLiteral(':')
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .optionalEnd()
        .optionalEnd()
        .optionalStart()
            .appendOffset("+HHMM", "Z")
        .optionalEnd()
        .toFormatter();


    @Override
    public void serialize(TemporalAccessor temporalAccessor, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if(temporalAccessor == null) {
            jsonGenerator.writeString("");
        }
        if(temporalAccessor.isSupported(ChronoField.MICRO_OF_SECOND) && temporalAccessor.get(ChronoField.MICRO_OF_SECOND) != 0l) {
            jsonGenerator.writeString(ISO_8601_DATE_TIME.format(temporalAccessor));
        } else {
            jsonGenerator.writeString(ISO_8601_DATE_TIME_WITHOUT_MICROS.format(temporalAccessor));
        }
    }

}
