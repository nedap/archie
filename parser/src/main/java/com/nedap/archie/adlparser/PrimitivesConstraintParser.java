package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlLexer;
import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.primitives.CBoolean;
import com.nedap.archie.aom.primitives.CDate;
import com.nedap.archie.aom.primitives.CDateTime;
import com.nedap.archie.aom.primitives.CDuration;
import com.nedap.archie.aom.primitives.CInteger;
import com.nedap.archie.aom.primitives.CReal;
import com.nedap.archie.aom.primitives.CString;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.aom.primitives.CTime;
import com.nedap.archie.base.terminology.TerminologyCode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class PrimitivesConstraintParser {

    public static CBoolean parseCBoolean(AdlParser.C_booleanContext booleanContext) {
        CBoolean result = new CBoolean();
        if(booleanContext.assumed_boolean_value() != null) {
            result.setAssumedValue(parseBoolean(booleanContext.assumed_boolean_value().boolean_value()));
        }
        Boolean_list_valueContext booleanListValue = booleanContext.boolean_list_value();
        if(booleanListValue != null) {
            parseBooleanValues(result, booleanListValue.boolean_value());
        }
        Boolean_valueContext booleanValueContext = booleanContext.boolean_value();
        if(booleanValueContext != null) {
            result.addConstraint(parseBoolean(booleanValueContext));
        }
        return result;
    }

    private static boolean parseBoolean(Boolean_valueContext context) {
        if(context.SYM_FALSE() != null) {
            return false;
        } else {
            return true;
        }
    }

    private static void parseBooleanValues(CBoolean result, List<Boolean_valueContext> booleanValues) {
        for(Boolean_valueContext booleanValue:booleanValues) {
            result.addConstraint(parseBoolean(booleanValue));
        }
    }

    public static CTerminologyCode parseCTerminologyCode(AdlParser.C_terminology_codeContext terminologyCodeContext) {
        CTerminologyCode result = new CTerminologyCode();
        boolean containsAssumedValue = !terminologyCodeContext.getTokens(AdlLexer.SYM_SEMICOLON).isEmpty();

        if(containsAssumedValue) {
            String terminologyId = terminologyCodeContext.AC_CODE().getText();
            TerminologyCode assumedValue = new TerminologyCode();
            assumedValue.setTerminologyId(terminologyId);
            String assumedValueString = terminologyCodeContext.AT_CODE().getText();
            assumedValue.setCodeString(assumedValueString);
            result.setAssumedValue(assumedValue);
            result.addConstraint(assumedValue.getTerminologyId());
        } else {
            if(terminologyCodeContext.AC_CODE() != null) {
                result.addConstraint(terminologyCodeContext.AC_CODE().getText());
            } else {
                result.addConstraint(terminologyCodeContext.AT_CODE().getText());
            }
        }
        return result;
    }

    public static CString parseCString(AdlParser.C_stringContext stringContext) {

        CString result = new CString();
        if(stringContext.assumed_string_value() != null) {
            result.setAssumedValue(OdinValueParser.parseOdinStringValue(stringContext.assumed_string_value().string_value()));
        }

        String_valueContext stringValueContext = stringContext.string_value();
        String_list_valueContext stringListValueContext = stringContext.string_list_value();

        if(stringListValueContext != null) {
            for(String_valueContext string:stringListValueContext.string_value()) {
                result.addConstraint(OdinValueParser.parseOdinStringValue(string));
            }
        }
        if(stringValueContext != null) {
            result.addConstraint(OdinValueParser.parseOdinStringValue(stringValueContext));
        }
        return result;
    }

    public static CDuration parseCDuration(AdlParser.C_durationContext c_durationContext) {
        return new CDuration();
    }

    public static CDateTime parseCDateTime(AdlParser.C_date_timeContext c_date_timeContext) {
        return new CDateTime();
    }

    public static CTime parseCTime(AdlParser.C_timeContext c_timeContext) {
        return new CTime();
    }

    public static CDate parseCDate(AdlParser.C_dateContext c_dateContext) {
        return new CDate();
    }

}
