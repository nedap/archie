package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.aom.primitives.CDate;
import com.nedap.archie.aom.primitives.CDateTime;
import com.nedap.archie.aom.primitives.CDuration;
import com.nedap.archie.aom.primitives.CTime;
import com.nedap.archie.base.Interval;
import org.antlr.runtime.tree.BaseTree;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.Duration;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAmount;

/**
 * Created by pieter.bos on 29/10/15.
 */
public class TemporalConstraintParser extends BaseTreeWalker {

    public TemporalConstraintParser(ADLParserErrors errors) {
        super(errors);
    }

    public CDuration parseCDuration(C_durationContext context) {
        //TODO: surround with try catch, do a nice error reporting with line numbers and other nice messages here :)
        CDuration result = new CDuration();
        if(context.DURATION_CONSTRAINT_PATTERN() != null) {
            result.setPatternedConstraint(context.DURATION_CONSTRAINT_PATTERN().getText());
        }
        if(context.assumed_duration_value() != null) {
            result.setAssumedValue(parseDurationValue(context.assumed_duration_value().duration_value()));
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
            if(interval.getLower() != null && interval.getUpper() != null && interval.getLower().equals(interval.getUpper())) {
                result.setAssumedValue(interval.getLower());
                result.setDefaultValue(interval.getLower());
            }
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
                interval.setLower(parseDurationValue(context.duration_value(0)));
                interval.setUpper(interval.getLower());
            } else {
                interval.setLower(parseDurationValue(context.duration_value(0)));
                interval.setUpper(parseDurationValue(context.duration_value(1)));
            }
            if(context.SYM_GT() != null) {//'|>a..b|'
                interval.setLowerIncluded(false);
            }
            if(context.SYM_LT() != null) {//'|a..<b|
                interval.setUpperIncluded(false);
            }
            //TODO: lower and upper included. Generic interval parsing?
        }
        return interval;
    }

    private Interval<TemporalAmount> parseRelOpDurationInterval(Duration_interval_valueContext context) {
        Interval<TemporalAmount> interval = new Interval<>();
        TemporalAmount duration = parseDurationValue(context.duration_value().get(0));
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

    private void parseDuration(CDuration result, Duration_valueContext durationValueContext) {
        TemporalAmount duration = parseDurationValue(durationValueContext);
        Interval<TemporalAmount> constraint = new Interval<>();
        constraint.setLower(duration);
        constraint.setUpper(duration);
        result.addConstraint(constraint);
    }

    private TemporalAmount parseDurationValue(AdlParser.Duration_valueContext context) {
        try {
            String text = context.getText();
            if(text.startsWith("PT")) {
                return Duration.parse(text);
            } else {
                return Period.parse(text);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e.getMessage() + ":" + context.getText());
        }
    }

    public CDateTime parseCDateTime(C_date_timeContext c_date_timeContext) {
        return new CDateTime();
    }

    public CTime parseCTime(C_timeContext c_timeContext) {
        return new CTime();
    }

    public CDate parseCDate(C_dateContext c_dateContext) {
        return new CDate();
    }
}
