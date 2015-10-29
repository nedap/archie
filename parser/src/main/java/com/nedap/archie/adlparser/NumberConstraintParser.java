package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlLexer;
import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.primitives.CInteger;
import com.nedap.archie.aom.primitives.CReal;
import com.nedap.archie.base.Interval;
import org.antlr.runtime.tree.BaseTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 18/10/15.
 */
public class NumberConstraintParser extends BaseTreeWalker {

    public NumberConstraintParser(ADLParserErrors errors) {
        super(errors);
    }

    public CInteger parseCInteger(AdlParser.C_integerContext integerContext) {
        CInteger result = new CInteger();

        if(integerContext.assumed_integer_value() != null) {
            result.setAssumedValue(Long.parseLong(integerContext.assumed_integer_value().integer_value().getText()));
        }

        Integer_valueContext integerValueContext = integerContext.integer_value();
        if(integerValueContext != null) {
            parseIntegerConstraint(result, integerValueContext);
        }

        AdlParser.Integer_list_valueContext integerListValueContext = integerContext.integer_list_value();
        if(integerListValueContext != null) {
            for(Integer_valueContext integerValueContext1:integerListValueContext.integer_value()) {
                parseIntegerConstraint(result, integerValueContext1);
            }
        }
        Integer_interval_valueContext intervalContext = integerContext.integer_interval_value();

        if(intervalContext != null) {
            result.addConstraint(parseIntegerInterval(intervalContext));
        }
        if(integerContext.integer_interval_list_value() != null) {
            for(AdlParser.Integer_interval_valueContext intervalListContext:integerContext.integer_interval_list_value().integer_interval_value()) {
                result.addConstraint(parseIntegerInterval(intervalListContext));
            }
        }
        //TODO: set enumeratedTypeConstraint if only integers?
        //TODO: set assumedValue if there's only one interval with upper=lower, bounded
        if(result.getConstraint().size() == 1) {
            Interval<Long> interval = result.getConstraint().get(0);
            if(interval.getLower() == interval.getUpper()) {
                result.setAssumedValue(interval.getLower());
                result.setDefaultValue(interval.getLower());
            }
        }
        return result;
    }

    private void parseIntegerValueList(CInteger cInteger, List<AdlParser.Integer_valueContext> integerValueContextList) {
        for(AdlParser.Integer_valueContext integerValueContext:integerValueContextList) {
            parseIntegerConstraint(cInteger, integerValueContext);
        }
    }

    private void parseIntegerConstraint(CInteger cInteger, Integer_valueContext integerValueContext) {
        long integer = Long.parseLong(integerValueContext.getText());
        Interval<Long> interval = new Interval<>();
        interval.setLower(integer);
        interval.setUpper(integer);
        cInteger.addConstraint(interval);
    }

    private Interval<Long> parseIntegerInterval(AdlParser.Integer_interval_valueContext intervalContext) {
        Interval<Long> interval = null;
        if(intervalContext.relop() != null) {
            interval = parseRelOpIntegerInterval(intervalContext);
        } else {
            interval = new Interval<>();
            if(intervalContext.integer_value().size() == 1) {
                interval.setLower(Long.parseLong(intervalContext.integer_value(0).getText()));
                interval.setUpper(interval.getLower());
            } else {
                interval.setLower(Long.parseLong(intervalContext.integer_value(0).getText()));
                interval.setUpper(Long.parseLong(intervalContext.integer_value(1).getText()));
            }
            //TODO: lower and upper included. Generic interval parsing?
        }
        return interval;
    }

    private Interval<Long> parseRelOpIntegerInterval(AdlParser.Integer_interval_valueContext intervalContext) {
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

    public CReal parseCReal(AdlParser.C_realContext realContext) {
        // ( real_value | real_list_value | real_interval_value | real_interval_list_value ) ( ';' real_value )? ;
        CReal result = new CReal();

        if(realContext.assumed_real_value() != null) {
            result.setAssumedValue(Double.parseDouble(realContext.assumed_real_value().real_value().getText()));
        }

        Real_valueContext realValueContext = realContext.real_value();
        if(realValueContext != null) {
            parseRealConstraint(result, realValueContext);
        }

        AdlParser.Real_list_valueContext realListValueContext = realContext.real_list_value();
        if(realListValueContext != null) {
            for(Real_valueContext realValueContext1:realListValueContext.real_value()) {
                parseRealConstraint(result, realValueContext1);
            }
        }
        Real_interval_valueContext intervalContext = realContext.real_interval_value();

        if(intervalContext != null) {
            result.addConstraint(parseRealInterval(intervalContext));
        }
        if(realContext.real_interval_list_value() != null) {
            for(AdlParser.Real_interval_valueContext intervalListContext:realContext.real_interval_list_value().real_interval_value()) {
                result.addConstraint(parseRealInterval(intervalListContext));
            }
        }
        //TODO: set enumeratedTypeConstraint if only reals?
        //TODO: set assumedValue if there's only one interval with upper=lower, bounded
        if(result.getConstraint().size() == 1) {
            Interval<Double> interval = result.getConstraint().get(0);
            if(interval.getLower() == interval.getUpper()) {//TODO: check with a very small delta instead of ==?
                result.setAssumedValue(interval.getLower());
                result.setDefaultValue(interval.getLower());
            }
        }
        return result;
    }

    private void parseRealValueList(CReal cReal, List<AdlParser.Real_valueContext> realValueContextList) {
        for(AdlParser.Real_valueContext realValueContext:realValueContextList) {
            parseRealConstraint(cReal, realValueContext);
        }
    }

    private void parseRealConstraint(CReal cReal, Real_valueContext realValueContext) {
        double real = Double.parseDouble(realValueContext.getText());
        Interval<Double> interval = new Interval<>();
        interval.setLower(real);
        interval.setUpper(real);
        cReal.addConstraint(interval);
    }

    private Interval<Double> parseRealInterval(AdlParser.Real_interval_valueContext intervalContext) {
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

    private Interval<Double> parseRelOpRealInterval(AdlParser.Real_interval_valueContext intervalContext) {
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
