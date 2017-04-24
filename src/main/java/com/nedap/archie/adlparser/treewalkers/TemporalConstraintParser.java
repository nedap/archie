package com.nedap.archie.adlparser.treewalkers;

import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.aom.primitives.CDate;
import com.nedap.archie.aom.primitives.CDateTime;
import com.nedap.archie.aom.primitives.CDuration;
import com.nedap.archie.aom.primitives.CTime;
import com.nedap.archie.base.Interval;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

/**
 * Created by pieter.bos on 29/10/15.
 */
public class TemporalConstraintParser extends BaseTreeWalker {

    public TemporalConstraintParser(ADLParserErrors errors) {
        super(errors);
    }

    public static final DateTimeFormatter ISO_8601_DATE;
    static {
        ISO_8601_DATE = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendValue(ChronoField.YEAR)
                .optionalStart()
                    .appendLiteral('-')
                    .appendValue(ChronoField.MONTH_OF_YEAR)
                    .optionalStart()
                        .appendLiteral('-')
                        .appendValue(ChronoField.DAY_OF_MONTH)
                    .optionalEnd()
                .optionalEnd()
                .toFormatter();
    }

    public static final DateTimeFormatter ISO_8601_DATE_TIME;
    static {
        ISO_8601_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendValue(ChronoField.YEAR)
                .appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .appendLiteral('-')
                .appendValue(ChronoField.DAY_OF_MONTH)
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
                            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, false)
                        .optionalEnd()
                    .optionalEnd()
                .optionalEnd()
                .optionalStart()
                    .appendOffset("+HHmm", "Z")
                .optionalEnd()
                .toFormatter();
    }

    public static final DateTimeFormatter ISO_8601_TIME;
    static {
        ISO_8601_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendValue(ChronoField.HOUR_OF_DAY)
                .optionalStart()
                    .appendLiteral(':')
                    .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                    .optionalStart()
                        .appendLiteral(':')
                        .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                        .optionalStart()
                            .appendLiteral(',')
                            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, false)
                        .optionalEnd()
                    .optionalEnd()
                .optionalEnd()
                .optionalStart()
                    .appendOffset("+HHmm", "Z")
                .optionalEnd()
                .toFormatter();
    }

    public CDuration parseCDuration(C_durationContext context) {
        //TODO: surround with try catch, do a nice error reporting with line numbers and other nice messages here :)
        CDuration result = new CDuration();
        if(context.DURATION_CONSTRAINT_PATTERN() != null) {

            String durationPattern = context.DURATION_CONSTRAINT_PATTERN().getText();
            //This is a bit of a hack - the duration pattern can be followed by a '/'.
            // To not clash with path, the lexer has to parse this '/' and include it in the pattern
            //so remove it here.
            if(durationPattern.endsWith("/")) {
                durationPattern = durationPattern.substring(0, durationPattern.length()-1);
            }
            result.setPatternedConstraint(durationPattern);
        }
        if(context.assumed_duration_value() != null) {
            result.setAssumedValue(parseDurationValue(context.assumed_duration_value().duration_value().getText()));
        }

        Duration_valueContext durationValueContext = context.duration_value();
        if(durationValueContext != null) {
            parseDuration(result, durationValueContext);
        }

        Duration_list_valueContext durationListValueContext = context.duration_list_value();
        if(durationListValueContext != null) {
            for(Duration_valueContext durationValueContext1:durationListValueContext.duration_value()) {
                parseDuration(result, durationValueContext1);
            }
        }
        Duration_interval_valueContext intervalContext = context.duration_interval_value();

        if(intervalContext != null) {
            result.addConstraint(parseDurationInterval(intervalContext));
        }
        if(context.duration_interval_list_value() != null) {
            for(Duration_interval_valueContext intervalListContext:context.duration_interval_list_value().duration_interval_value()) {
                result.addConstraint(parseDurationInterval(intervalListContext));
            }
        }

        if(result.getConstraint().size() == 1) {
            Interval<TemporalAmount> interval = result.getConstraint().get(0);
        }

        return result;
    }

    private Interval<TemporalAmount> parseDurationInterval(Duration_interval_valueContext context) {
        Interval<TemporalAmount> interval = null;
        if(context.relop() != null) {
            interval = parseRelOpDurationInterval(context);
        } else {
            interval = new Interval<>();
            if(context.duration_value().size() == 1) {
                interval.setLower(parseDurationValue(context.duration_value(0).getText()));
                interval.setUpper(interval.getLower());
            } else {
                interval.setLower(parseDurationValue(context.duration_value(0).getText()));
                interval.setUpper(parseDurationValue(context.duration_value(1).getText()));
            }
            if(context.SYM_GT() != null) {//'|>a..b|'
                interval.setLowerIncluded(false);
            }
            if(context.SYM_LT() != null) {//'|a..<b|
                interval.setUpperIncluded(false);
            }
        }
        return interval;
    }

    private Interval<TemporalAmount> parseRelOpDurationInterval(Duration_interval_valueContext context) {
        Interval<TemporalAmount> interval = new Interval<>();
        TemporalAmount duration = parseDurationValue(context.duration_value().get(0).getText());
        switch(context.relop().getText()) {
            case "<":
                interval.setUpperIncluded(false);
            case "<=":
                interval.setLowerUnbounded(true);
                interval.setUpper(duration);
                break;
            case ">":
                interval.setLowerIncluded(false);
            case ">=":
                interval.setUpperUnbounded(true);
                interval.setLower(duration);
                break;
            default:
                throw new RuntimeException("Unexpected operator: " + context.relop().getText());
        }
        return interval;
    }

    private void parseDuration(CDuration result, Duration_valueContext durationValueContext) {
        TemporalAmount duration = parseDurationValue(durationValueContext.getText());
        Interval<TemporalAmount> constraint = new Interval<>();
        constraint.setLower(duration);
        constraint.setUpper(duration);
        result.addConstraint(constraint);
    }

    public static TemporalAmount parseDurationValue(String text) {
        try {
            if(text.startsWith("PT")) {
                return Duration.parse(text);
            } else {
                return Period.parse(text);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage() + ":" + text);
        }
    }

    public CDateTime parseCDateTime(C_date_timeContext context) {
        //TODO: surround with try catch, do a nice error reporting with line numbers and other nice messages here :)
        CDateTime result = new CDateTime();
        if(context.DATE_TIME_CONSTRAINT_PATTERN() != null) {
            result.setPatternedConstraint(context.DATE_TIME_CONSTRAINT_PATTERN().getText());
        }
        if(context.assumed_date_time_value() != null) {
            result.setAssumedValue(parseDateTimeValue(context.assumed_date_time_value().date_time_value().getText()));
        }

        Date_time_valueContext datetimeValueContext = context.date_time_value();
        if(datetimeValueContext != null) {
            parseDateTime(result, datetimeValueContext);
        }

        Date_time_list_valueContext datetimeListValueContext = context.date_time_list_value();
        if(datetimeListValueContext != null) {
            for(Date_time_valueContext datetimeValueContext1:datetimeListValueContext.date_time_value()) {
                parseDateTime(result, datetimeValueContext1);
            }
        }
        Date_time_interval_valueContext intervalContext = context.date_time_interval_value();

        if(intervalContext != null) {
            result.addConstraint(parseDateTimeInterval(intervalContext));
        }
        if(context.date_time_interval_list_value() != null) {
            for(Date_time_interval_valueContext intervalListContext:context.date_time_interval_list_value().date_time_interval_value()) {
                result.addConstraint(parseDateTimeInterval(intervalListContext));
            }
        }

        if(result.getConstraint().size() == 1) {
            Interval<TemporalAccessor> interval = result.getConstraint().get(0);
        }

        return result;
    }

    private Interval<TemporalAccessor> parseDateTimeInterval(Date_time_interval_valueContext context) {
        Interval<TemporalAccessor> interval = null;
        if(context.relop() != null) {
            interval = parseRelOpDateTimeInterval(context);
        } else {
            interval = new Interval<>();
            if(context.date_time_value().size() == 1) {
                interval.setLower(parseDateTimeValue(context.date_time_value(0).getText()));
                interval.setUpper(interval.getLower());
            } else {
                interval.setLower(parseDateTimeValue(context.date_time_value(0).getText()));
                interval.setUpper(parseDateTimeValue(context.date_time_value(1).getText()));
            }
            if(context.SYM_GT() != null) {//'|>a..b|'
                interval.setLowerIncluded(false);
            }
            if(context.SYM_LT() != null) {//'|a..<b|
                interval.setUpperIncluded(false);
            }
        }
        return interval;
    }

    private Interval<TemporalAccessor> parseRelOpDateTimeInterval(Date_time_interval_valueContext context) {
        Interval<TemporalAccessor> interval = new Interval<>();
        TemporalAccessor datetime = parseDateTimeValue(context.date_time_value(0).getText());
        switch(context.relop().getText()) {
            case "<":
                interval.setUpperIncluded(false);
            case "<=":
                interval.setLowerUnbounded(true);
                interval.setUpper(datetime);
                break;
            case ">":
                interval.setLowerIncluded(false);
            case ">=":
                interval.setUpperUnbounded(true);
                interval.setLower(datetime);
                break;
        }
        return interval;
    }

    private void parseDateTime(CDateTime result, Date_time_valueContext datetimeValueContext) {
        TemporalAccessor datetime = parseDateTimeValue(datetimeValueContext.getText());
        Interval<TemporalAccessor> constraint = new Interval<>();
        constraint.setLower(datetime);
        constraint.setUpper(datetime);
        result.addConstraint(constraint);
    }


    public CTime parseCTime(C_timeContext context) {
        //TODO: surround with try catch, do a nice error reporting with line numbers and other nice messages here :)
        CTime result = new CTime();
        if(context.TIME_CONSTRAINT_PATTERN() != null) {
            result.setPatternedConstraint(context.TIME_CONSTRAINT_PATTERN().getText());
        }
        if(context.assumed_time_value() != null) {
            result.setAssumedValue(parseTimeValue(context.assumed_time_value().time_value().getText()));
        }

        Time_valueContext timeValueContext = context.time_value();
        if(timeValueContext != null) {
            parseTime(result, timeValueContext);
        }

        Time_list_valueContext timeListValueContext = context.time_list_value();
        if(timeListValueContext != null) {
            for(Time_valueContext timeValueContext1:timeListValueContext.time_value()) {
                parseTime(result, timeValueContext1);
            }
        }
        Time_interval_valueContext intervalContext = context.time_interval_value();

        if(intervalContext != null) {
            result.addConstraint(parseTimeInterval(intervalContext));
        }
        if(context.time_interval_list_value() != null) {
            for(Time_interval_valueContext intervalListContext:context.time_interval_list_value().time_interval_value()) {
                result.addConstraint(parseTimeInterval(intervalListContext));
            }
        }

        if(result.getConstraint().size() == 1) {
            Interval<TemporalAccessor> interval = result.getConstraint().get(0);
        }

        return result;
    }

    private Interval<TemporalAccessor> parseTimeInterval(Time_interval_valueContext context) {
        Interval<TemporalAccessor> interval = null;
        if(context.relop() != null) {
            interval = parseRelOpTimeInterval(context);
        } else {
            interval = new Interval<>();
            if(context.time_value().size() == 1) {
                interval.setLower(parseTimeValue(context.time_value(0).getText()));
                interval.setUpper(interval.getLower());
            } else {
                interval.setLower(parseTimeValue(context.time_value(0).getText()));
                interval.setUpper(parseTimeValue(context.time_value(1).getText()));
            }
            if(context.SYM_GT() != null) {//'|>a..b|'
                interval.setLowerIncluded(false);
            }
            if(context.SYM_LT() != null) {//'|a..<b|
                interval.setUpperIncluded(false);
            }
        }
        return interval;
    }

    private Interval<TemporalAccessor> parseRelOpTimeInterval(Time_interval_valueContext context) {
        Interval<TemporalAccessor> interval = new Interval<>();
        TemporalAccessor datetime = parseTimeValue(context.time_value(0).getText());
        switch(context.relop().getText()) {
            case "<":
                interval.setUpperIncluded(false);
            case "<=":
                interval.setLowerUnbounded(true);
                interval.setUpper(datetime);
                break;
            case ">":
                interval.setLowerIncluded(false);
            case ">=":
                interval.setUpperUnbounded(true);
                interval.setLower(datetime);
                break;
        }
        return interval;
    }

    private void parseTime(CTime result, Time_valueContext context) {
        TemporalAccessor datetime = parseTimeValue(context.getText());
        Interval<TemporalAccessor> constraint = new Interval<>();
        constraint.setLower(datetime);
        constraint.setUpper(datetime);
        result.addConstraint(constraint);
    }



    public CDate parseCDate(C_dateContext context) {
        //TODO: surround with try catch, do a nice error reporting with line numbers and other nice messages here :)
        CDate result = new CDate();
        if(context.DATE_CONSTRAINT_PATTERN() != null) {
            result.setPatternedConstraint(context.DATE_CONSTRAINT_PATTERN().getText());
        }
        if(context.assumed_date_value() != null) {
            result.setAssumedValue(parseDateValue(context.assumed_date_value().date_value().getText()));
        }

        Date_valueContext durationValueContext = context.date_value();
        if(durationValueContext != null) {
            parseDate(result, durationValueContext);
        }

        Date_list_valueContext durationListValueContext = context.date_list_value();
        if(durationListValueContext != null) {
            for(Date_valueContext durationValueContext1:durationListValueContext.date_value()) {
                parseDate(result, durationValueContext1);
            }
        }
        Date_interval_valueContext intervalContext = context.date_interval_value();

        if(intervalContext != null) {
            result.addConstraint(parseDateInterval(intervalContext));
        }
        if(context.date_interval_list_value() != null) {
            for(Date_interval_valueContext intervalListContext:context.date_interval_list_value().date_interval_value()) {
                result.addConstraint(parseDateInterval(intervalListContext));
            }
        }

        if(result.getConstraint().size() == 1) {
            Interval<Temporal> interval = result.getConstraint().get(0);
        }

        return result;
    }

    private Interval<Temporal> parseDateInterval(Date_interval_valueContext context) {
        Interval<Temporal> interval = null;
        if(context.relop() != null) {
            interval = parseRelOpDateInterval(context);
        } else {
            interval = new Interval<>();
            if(context.date_value().size() == 1) {
                interval.setLower(parseDateValue(context.date_value(0).getText()));
                interval.setUpper(interval.getLower());
            } else {
                interval.setLower(parseDateValue(context.date_value(0).getText()));
                interval.setUpper(parseDateValue(context.date_value(1).getText()));
            }
            if(context.SYM_GT() != null) {//'|>a..b|'
                interval.setLowerIncluded(false);
            }
            if(context.SYM_LT() != null) {//'|a..<b|
                interval.setUpperIncluded(false);
            }
        }
        return interval;
    }

    private Interval<Temporal> parseRelOpDateInterval(Date_interval_valueContext context) {
        Interval<Temporal> interval = new Interval<>();
        Temporal duration = parseDateValue(context.date_value().get(0).getText());
        switch(context.relop().getText()) {
            case "<":
                interval.setUpperIncluded(false);
            case "<=":
                interval.setLowerUnbounded(true);
                interval.setUpper(duration);
                break;
            case ">":
                interval.setLowerIncluded(false);
            case ">=":
                interval.setUpperUnbounded(true);
                interval.setLower(duration);
                break;
        }
        return interval;
    }

    private void parseDate(CDate result, Date_valueContext durationValueContext) {
        Temporal duration = parseDateValue(durationValueContext.getText());
        Interval<Temporal> constraint = new Interval<>();
        constraint.setLower(duration);
        constraint.setUpper(duration);
        result.addConstraint(constraint);
    }

    public static TemporalAccessor parseDateTimeValue(String text) {
        try {
            return ISO_8601_DATE_TIME.parseBest(text, OffsetDateTime::from, LocalDateTime::from);
        } catch (DateTimeParseException e) {
            try {
                //Not parseable as a standard public object from datetime. We do not implement our own yet (we could!)
                //so fallback to the Parsed object. The Parsed object is package-private, so cannot be added as a reference
                //to the parseBest query, unfortunately.
                return ISO_8601_DATE_TIME.parse(text);
            } catch (DateTimeParseException e1) {
                throw new IllegalArgumentException(e1.getMessage() + ":" + text);
            }
        }
    }

    public static TemporalAccessor parseTimeValue(String text) {
        try {
            return ISO_8601_TIME.parseBest(text, OffsetTime::from, LocalTime::from);
        } catch (DateTimeParseException e) {
            try {
                //Not parseable as a standard public object from datetime. We do not implement our own yet (we could!)
                //so fallback to the Parsed object. The Parsed object is package-private, so cannot be added as a reference
                //to the parseBest query, unfortunately.
                return ISO_8601_TIME.parse(text);
            } catch (DateTimeParseException e1) {
                throw new IllegalArgumentException(e1.getMessage() + ":" + text);
            }
        }
    }

    public static Temporal parseDateValue(String text) {
        try {
            return (Temporal) ISO_8601_DATE.parseBest(text, LocalDate::from, YearMonth::from, Year::from);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage() + ":" + text);
        }
    }
}
