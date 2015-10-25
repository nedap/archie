//
// description: Antlr4 grammar for cADL primitives sub-syntax of Archetype Definition Language (ADL2)
// author:      Thomas Beale <thomas.beale@openehr.org>
// support:     openEHR Specifications PR tracker <https://openehr.atlassian.net/projects/SPECPR/issues>
// copyright:   Copyright (c) 2015 openEHR Foundation
// license:     Apache 2.0 License <http://www.apache.org/licenses/LICENSE-2.0.html>
//

grammar cadl_primitives;
import adl_keywords, odin_values;

//
//  ======================= Parser rules ========================
//

c_primitive_object:
      c_integer
    | c_real
    | c_date
    | c_time
    | c_date_time
    | c_duration
    | c_string
    | c_terminology_code
    | c_boolean
    ;

c_integer: ( integer_value | integer_list_value | integer_interval_value | integer_interval_list_value ) assumed_integer_value? ;
assumed_integer_value: ';' integer_value ;

c_real: ( real_value | real_list_value | real_interval_value | real_interval_list_value ) assumed_real_value? ;
assumed_real_value: ';' real_value ;

c_date_time: ( DATE_TIME_CONSTRAINT_PATTERN | date_time_value | date_time_list_value | date_time_interval_value | date_time_interval_list_value ) assumed_date_time_value? ;
assumed_date_time_value: ';' date_time_value ;

c_date: ( DATE_CONSTRAINT_PATTERN | date_value | date_list_value | date_interval_value | date_interval_list_value ) assumed_date_value? ;
assumed_date_value: ';' date_value ;

c_time: ( TIME_CONSTRAINT_PATTERN | time_value | time_list_value | time_interval_value | time_interval_list_value ) assumed_time_value? ;
assumed_time_value: ';' time_value ;

c_duration: (
      DURATION_CONSTRAINT_PATTERN ( '/' ( duration_interval_value | duration_value ))?
    | duration_value | duration_list_value | duration_interval_value | duration_interval_list_value ) assumed_duration_value?
    ;
assumed_duration_value: ';' duration_value ;

// for REGEX: strip first and last char, and then process with PCRE grammar
c_string: ( string_value | string_list_value | regex_constraint ) assumed_string_value? ;
assumed_string_value: ';' string_value ;
regex_constraint: '/' regex1 '/' | '^' regex2 '^' ;
regex1: ( '_' | '.*' | '\\.' | '\\/' | ~'/' )+ ; // TODO: not clear why first 3 matches are needed, but they work.
regex2: ( '_' | '.*' | '\\.' | '\\/' | ~'^' )+ ;


// ADL2 term types: [ac3], [ac3; at5], [at5]
c_terminology_code: '[' ( ( AC_CODE ( ';' AT_CODE )? ) | AT_CODE ) ']' ;

c_boolean: ( boolean_value | boolean_list_value ) assumed_boolean_value? ;
assumed_boolean_value: ';' boolean_value ;

adl_path          : adl_path_segment+ ;
adl_relative_path : adl_path_element adl_path ;  // TODO: remove when current slots no longer needed
adl_path_segment  : '/' adl_path_element ;
adl_path_element  : attribute_id ( '[' ID_CODE ']' )? ;


//
//  ======================= Lexical rules ========================
//

// ---------- various ADL2 codes -------

ROOT_ID_CODE : 'id1' '.1'* ;
ID_CODE      : 'id' CODE_STR ;
AT_CODE      : 'at' CODE_STR ;
AC_CODE      : 'ac' CODE_STR ;
fragment CODE_STR : ('0' | [1-9][0-9]*) ( '.' ('0' | [1-9][0-9]* ))* ;

// ---------- ISO8601-based date/time/duration constraint patterns

DATE_CONSTRAINT_PATTERN      : YEAR_PATTERN '-' MONTH_PATTERN '-' DAY_PATTERN ;
TIME_CONSTRAINT_PATTERN      : HOUR_PATTERN ':' MINUTE_PATTERN ':' SECOND_PATTERN ;
DATE_TIME_CONSTRAINT_PATTERN : DATE_CONSTRAINT_PATTERN 'T' TIME_CONSTRAINT_PATTERN ;
DURATION_CONSTRAINT_PATTERN  : 'P' [yY]?[mM]?[Ww]?[dD]? ( 'T' [hH]?[mM]?[sS]? )? ;

// date time pattern
fragment YEAR_PATTERN   : ( 'yyy' 'y'? ) | ( 'YYY' 'Y'? ) ;
fragment MONTH_PATTERN  : 'mm' | 'MM' | '??' | 'XX' | 'xx' ;
fragment DAY_PATTERN    : 'dd' | 'DD' | '??' | 'XX' | 'xx'  ;
fragment HOUR_PATTERN   : 'hh' | 'HH' | '??' | 'XX' | 'xx'  ;
fragment MINUTE_PATTERN : 'mm' | 'MM' | '??' | 'XX' | 'xx'  ;
fragment SECOND_PATTERN : 'ss' | 'SS' | '??' | 'XX' | 'xx'  ;

