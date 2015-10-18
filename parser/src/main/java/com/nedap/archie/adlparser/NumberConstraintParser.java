package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.primitives.CInteger;
import com.nedap.archie.aom.primitives.CReal;
import com.nedap.archie.base.Interval;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 18/10/15.
 */
public class NumberConstraintParser {

    public static CInteger parseCInteger(AdlParser.C_integerContext integerContext) {
        // ( integer_value | integer_list_value | integer_interval_value | integer_interval_list_value ) ( ';' integer_value )? ;
        CInteger result = new CInteger();

        List<Interval<Long>> constraints = new ArrayList<>();
        result.setConstraint(constraints);

        List<AdlParser.Integer_valueContext> integerValueContextList = integerContext.integer_value();
        if(integerValueContextList != null) {
            parseIntegerValueList(constraints, integerValueContextList);

            //int value = IntegerintegerContext.integer_value();
            //result.setConstraint(new Interval<Integer>());
        }
        AdlParser.Integer_list_valueContext integerListValueContext = integerContext.integer_list_value();
        if(integerListValueContext != null) {
            parseIntegerValueList(constraints, integerListValueContext.integer_value());
        }
        AdlParser.Integer_interval_valueContext intervalContext = integerContext.integer_interval_value();

        if(intervalContext != null) {
            constraints.add(parseIntegerInterval(intervalContext));
        }
        if(integerContext.integer_interval_list_value() != null) {
            for(AdlParser.Integer_interval_valueContext intervalListContext:integerContext.integer_interval_list_value().integer_interval_value()) {
                constraints.add(parseIntegerInterval(intervalListContext));
            }
        }
        //TODO: set enumeratedTypeConstraint if only integers?
        //TODO: set assumedValue if there's only one interval with upper=lower, bounded
        if(constraints.size() == 1) {
            Interval<Long> interval = constraints.get(0);
            if(interval.getLower() == interval.getUpper()) {
                result.setAssumedValue(interval.getLower());
                result.setDefaultValue(interval.getLower());
            }
        }
        return result;
    }

    private static void parseIntegerValueList(List<Interval<Long>> constraints, List<AdlParser.Integer_valueContext> integerValueContextList) {
        for(AdlParser.Integer_valueContext integerValueContext:integerValueContextList) {
            long integer = Long.parseLong(integerValueContext.getText());
            Interval<Long> interval = new Interval<>();
            interval.setLower(integer);
            interval.setUpper(integer);
            constraints.add(interval);
        }
    }

    private static Interval<Long> parseIntegerInterval(AdlParser.Integer_interval_valueContext intervalContext) {
        Interval<Long> interval = null;
        if(intervalContext.relop() != null) {
            interval = parseRelOpIntegerInterval(intervalContext);
        } else {
            interval = new Interval<>();
            interval.setLower(Long.parseLong(intervalContext.integer_value(0).getText()));
            interval.setUpper(Long.parseLong(intervalContext.integer_value(1).getText()));
            //TODO: lower and upper included. Generic interval parsing?
        }
        return interval;
    }

    private static Interval<Long> parseRelOpIntegerInterval(AdlParser.Integer_interval_valueContext intervalContext) {
        Interval<Long> interval = new Interval<>();
        long integer = Long.parseLong(intervalContext.integer_value().get(0).getText());
        switch(intervalContext.relop().getText()) {
            case "<":
                interval.setUpperIncluded(false);
            case "<=":
                interval.setLowerUnbounded(true);
                interval.setUpper(integer);
                break;
            case ">":
                interval.setLowerIncluded(false);
            case ">=":
                interval.setUpperUnbounded(true);
                interval.setLower(integer);
                break;
        }
        return interval;
    }

    public static CReal parseCReal(AdlParser.C_realContext realContext) {

        CReal result = new CReal();

        List<Interval<Double>> constraints = new ArrayList<>();
        result.setConstraint(constraints);

        List<AdlParser.Real_valueContext> realValueContextList = realContext.real_value();
        if(realValueContextList != null) {
            parseRealValueList(constraints, realValueContextList);

            //int value = IntegerrealContext.real_value();
            //result.setConstraint(new Interval<Integer>());
        }
        AdlParser.Real_list_valueContext realListValueContext = realContext.real_list_value();
        if(realListValueContext != null) {
            parseRealValueList(constraints, realListValueContext.real_value());
        }
        AdlParser.Real_interval_valueContext intervalContext = realContext.real_interval_value();

        if(intervalContext != null) {
            constraints.add(parseRealInterval(intervalContext));
        }
        if(realContext.real_interval_list_value() != null) {
            for(AdlParser.Real_interval_valueContext intervalListContext:realContext.real_interval_list_value().real_interval_value()) {
                constraints.add(parseRealInterval(intervalListContext));
            }
        }
        //TODO: set enumeratedTypeConstraint if only reals?
        //TODO: set assumedValue if there's only one interval with upper=lower, bounded
        if(constraints.size() == 1) {
            Interval<Double> interval = constraints.get(0);
            if(interval.getLower() == interval.getUpper()) { //TODO: double == double... check with a delta?
                result.setAssumedValue(interval.getLower());
                result.setDefaultValue(interval.getLower());
            }
        }
        return result;
    }

    private static void parseRealValueList(List<Interval<Double>> constraints, List<AdlParser.Real_valueContext> realValueContextList) {
        for(AdlParser.Real_valueContext realValueContext:realValueContextList) {
            double real = Double.parseDouble(realValueContext.getText());
            Interval<Double> interval = new Interval<>();
            interval.setLower(real);
            interval.setUpper(real);
            constraints.add(interval);
        }
    }

    private static Interval<Double> parseRealInterval(AdlParser.Real_interval_valueContext intervalContext) {
        Interval<Double> interval = null;
        if(intervalContext.relop() != null) {
            interval = parseRelOpRealInterval(intervalContext);
        } else {
            interval = new Interval<>();
            interval.setLower(Double.parseDouble(intervalContext.real_value(0).getText()));
            interval.setUpper(Double.parseDouble(intervalContext.real_value(1).getText()));
            //TODO: lower and upper included. Generic interval parsing?
        }
        return interval;
    }

    private static Interval<Double> parseRelOpRealInterval(AdlParser.Real_interval_valueContext intervalContext) {
        Interval<Double> interval = new Interval<>();
        double real = Double.parseDouble(intervalContext.real_value().get(0).getText());
        switch(intervalContext.relop().getText()) {
            case "<":
                interval.setUpperIncluded(false);
            case "<=":
                interval.setLowerUnbounded(true);
                interval.setUpper(real);
                break;
            case ">":
                interval.setLowerIncluded(false);
            case ">=":
                interval.setUpperUnbounded(true);
                interval.setLower(real);
                break;
        }
        return interval;
    }
}
