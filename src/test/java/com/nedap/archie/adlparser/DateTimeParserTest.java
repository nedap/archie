package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.treewalkers.TemporalConstraintParser;
import com.nedap.archie.rm.archetypes.Locatable;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDate;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvTime;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;

/**
 * Created by pieter.bos on 02/03/16.
 */
public class DateTimeParserTest {
    @Test
    public void dateCorrect() {
        assertEquals(LocalDate.of(2015, 1, 1), TemporalConstraintParser.parseDateValue("2015-1-1"));
        assertEquals(YearMonth.of(2015, 1), TemporalConstraintParser.parseDateValue("2015-1"));
        assertEquals(Year.of(2015), TemporalConstraintParser.parseDateValue("2015"));
    }

    @Test
    public void datetimeCorrect() {
        assertEquals(OffsetDateTime.of(2015, 1, 1, 12, 1, 1, 100000000, ZoneOffset.of("+0100")), TemporalConstraintParser.parseDateTimeValue("2015-1-1T12:01:01,1+0100"));
        assertEquals(OffsetDateTime.of(2015, 1, 1, 12, 1, 1, 0, ZoneOffset.of("+0100")), TemporalConstraintParser.parseDateTimeValue("2015-1-1T12:01:01+0100"));
        assertEquals(LocalDateTime.of(2015, 1, 1, 12, 1, 1, 100000000), TemporalConstraintParser.parseDateTimeValue("2015-1-1T12:01:01,1"));
        assertEquals(LocalDateTime.of(2015, 1, 1, 12, 1, 1, 0), TemporalConstraintParser.parseDateTimeValue("2015-1-1T12:01:01"));
        assertEquals(LocalDateTime.of(2015, 1, 1, 12, 1, 0, 0), TemporalConstraintParser.parseDateTimeValue("2015-1-1T12:01"));
        assertEquals(LocalDateTime.of(2015, 1, 1, 12, 0, 0, 0), TemporalConstraintParser.parseDateTimeValue("2015-1-1T12"));
        assertEquals(OffsetDateTime.of(2015, 1, 1, 12, 0, 0, 0, ZoneOffset.of("+0100")), TemporalConstraintParser.parseDateTimeValue("2015-1-1T12+0100"));
    }

   @Test
   public void timeCorrect() {
       assertEquals(OffsetTime.of(12, 1, 1, 100000000, ZoneOffset.of("+0100")), TemporalConstraintParser.parseTimeValue("12:01:01,1+0100"));
       assertEquals(OffsetTime.of(12, 1, 1, 0, ZoneOffset.of("+0100")), TemporalConstraintParser.parseTimeValue("12:01:01+0100"));
       assertEquals(LocalTime.of(12, 1, 1, 100000000), TemporalConstraintParser.parseTimeValue("12:01:01,1"));
       assertEquals(LocalTime.of(12, 1, 1, 0), TemporalConstraintParser.parseTimeValue("12:01:01"));
       assertEquals(LocalTime.of(12, 1), TemporalConstraintParser.parseTimeValue("12:01"));
       assertEquals(OffsetTime.of(12, 1, 0, 0, ZoneOffset.of("+0100")), TemporalConstraintParser.parseTimeValue("12:01+0100"));
   }

}
