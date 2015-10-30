package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlLexer;
import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.Boolean_list_valueContext;
import com.nedap.archie.adlparser.antlr.AdlParser.Boolean_valueContext;
import com.nedap.archie.adlparser.antlr.AdlParser.String_list_valueContext;
import com.nedap.archie.adlparser.antlr.AdlParser.String_valueContext;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.primitives.CBoolean;
import com.nedap.archie.aom.primitives.CDate;
import com.nedap.archie.aom.primitives.CDateTime;
import com.nedap.archie.aom.primitives.CDuration;
import com.nedap.archie.aom.primitives.CString;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.aom.primitives.CTime;
import com.nedap.archie.base.terminology.TerminologyCode;

import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class PrimitivesConstraintParser extends BaseTreeWalker {

    private final NumberConstraintParser numberConstraintParser;
    private final TemporalConstraintParser temporalConstraintParser;

    public PrimitivesConstraintParser(ADLParserErrors errors) {
        super(errors);
        numberConstraintParser = new NumberConstraintParser(errors);
        temporalConstraintParser = new TemporalConstraintParser(errors);
    }

    public CPrimitiveObject parsePrimitiveObject(AdlParser.C_primitive_objectContext objectContext) {
        /*c_integer
                | c_real
                | c_date
                | c_time
                | c_date_time
                | c_duration
                | c_string
                | c_terminology_code
                | c_boolean*/
        if(objectContext.c_integer() != null) {
            return numberConstraintParser.parseCInteger(objectContext.c_integer());
        } else if (objectContext.c_real() != null) {
            return numberConstraintParser.parseCReal(objectContext.c_real());
        } else if (objectContext.c_date() != null) {
            return parseCDate(objectContext.c_date());
        } else if (objectContext.c_time() != null) {
            return parseCTime(objectContext.c_time());
        } else if (objectContext.c_date_time() != null) {
            return parseCDateTime(objectContext.c_date_time());
        } else if (objectContext.c_duration() != null) {
            return parseCDuration(objectContext.c_duration());
        } else if (objectContext.c_string() != null) {
            return parseCString(objectContext.c_string());
        } else if (objectContext.c_terminology_code() != null) {
            return parseCTerminologyCode(objectContext.c_terminology_code());
        } else if (objectContext.c_boolean() != null) {
            return parseCBoolean(objectContext.c_boolean());
        }
        return null;
    }

    public CBoolean parseCBoolean(AdlParser.C_booleanContext booleanContext) {
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

    private boolean parseBoolean(Boolean_valueContext context) {
        if(context.SYM_FALSE() != null) {
            return false;
        } else {
            return true;
        }
    }

    private void parseBooleanValues(CBoolean result, List<Boolean_valueContext> booleanValues) {
        for(Boolean_valueContext booleanValue:booleanValues) {
            result.addConstraint(parseBoolean(booleanValue));
        }
    }

    public CTerminologyCode parseCTerminologyCode(AdlParser.C_terminology_codeContext terminologyCodeContext) {
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

    public CString parseCString(AdlParser.C_stringContext stringContext) {

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
        if(stringContext.regex_constraint() != null) {
            result.addConstraint(stringContext.regex_constraint().getText());
        }
        return result;
    }

    public CDuration parseCDuration(AdlParser.C_durationContext context) {
        return temporalConstraintParser.parseCDuration(context);
    }

    public  CDateTime parseCDateTime(AdlParser.C_date_timeContext context) {
        return temporalConstraintParser.parseCDateTime(context);
    }

    public static CTime parseCTime(AdlParser.C_timeContext c_timeContext) {
        return new CTime();
    }

    public CDate parseCDate(AdlParser.C_dateContext context) {
        return temporalConstraintParser.parseCDate(context);
    }

}
