package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlParser;
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

/**
 * Created by pieter.bos on 15/10/15.
 */
public class PrimitivesConstraintParser {

    public static CObject parseCInteger(AdlParser.C_integerContext integerContext) {
        // ( integer_value | integer_list_value | integer_interval_value | integer_interval_list_value ) ( ';' integer_value )? ;
        CInteger result = new CInteger();

        if(integerContext.integer_value() != null) {
            //TODO
        }
        if(integerContext.integer_list_value() != null) {

        }
        if(integerContext.integer_interval_value() != null) {

        }
        if(integerContext.integer_interval_list_value() != null) {

        }

        return result;
    }

    public static CBoolean parseCBoolean(AdlParser.C_booleanContext c_booleanContext) {
        return new CBoolean();
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

    public static CReal parseCReal(AdlParser.C_realContext c_realContext) {
        return new CReal();
    }
}
