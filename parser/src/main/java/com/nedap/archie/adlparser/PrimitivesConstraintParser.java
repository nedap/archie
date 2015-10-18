package com.nedap.archie.adlparser;

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

import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class PrimitivesConstraintParser {

    public static CBoolean parseCBoolean(AdlParser.C_booleanContext booleanContext) {
        CBoolean result = new CBoolean();
        List<Boolean_valueContext> booleanValues = booleanContext.boolean_value();
        Boolean_list_valueContext booleanListValue = booleanContext.boolean_list_value();
        if(booleanValues != null) {
            parseBooleanValues(result, booleanValues);
        }
        if(booleanListValue != null) {
            parseBooleanValues(result, booleanListValue.boolean_value());
        }
        return result;
    }

    private static void parseBooleanValues(CBoolean result, List<Boolean_valueContext> booleanValues) {
        for(Boolean_valueContext booleanValue:booleanValues) {
            if(booleanValue.SYM_FALSE() != null) {
                result.addConstraint(false);
            } else if(booleanValue.SYM_TRUE() != null) {
                result.addConstraint(true);
            }
        }
    }

    public static CTerminologyCode parseCTerminologyCode(AdlParser.C_terminology_codeContext c_terminology_codeContext) {
        return new CTerminologyCode();
    }

    public static CString parseCString(AdlParser.C_stringContext c_stringContext) {
        return new CString();
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
