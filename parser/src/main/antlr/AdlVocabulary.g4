//
// description: Antlr4 lexer grammar for keywords of Archetype Definition Language (ADL2)
// author:      Thomas Beale <thomas.beale@openehr.org>
// support:     openEHR Specifications PR tracker <https://openehr.atlassian.net/projects/SPECPR/issues>
// copyright:   Copyright (c) 2015 openEHR Foundation
// license:     Apache 2.0 License <http://www.apache.org/licenses/LICENSE-2.0.html>
//

lexer grammar AdlVocabulary;

MATCHES_REGEXP : SYM_MATCHES WS? '{' WS? REGEXP WS? '}' ;
fragment REGEXP : '/' SLASH_REGEXP_CHAR* '/' | '^' REGEXP_CHAR* '^';
fragment REGEXP_CHAR : ~('\\'|[^\n\r]) | REGEXP_ESCAPE_SEQ | UTF8CHAR ;
fragment REGEXP_ESCAPE_SEQ : '\\' [?abfnrtv\\^] ;


fragment SLASH_REGEXP_CHAR : ~[/\n\r] ;
// ~('\\'|[/\n\r]) | SLASH_REGEXP_ESCAPE_SEQ | UTF8CHAR ;
fragment SLASH_REGEXP_ESCAPE_SEQ : '\\' [?abfnrtv\/\\] ;

// TODO: should these be matched in the lexer, or should we just match any identifier and do things in parser rules?
ROOT_ID_CODE : 'id1' ('.1')* ;
ID_CODE      : 'id' CODE_STR ;
AT_CODE      : 'at' CODE_STR ;
AC_CODE      : 'ac' CODE_STR ;
fragment CODE_STR : ('0' | [1-9][0-9]*) ( '.' ('0' | [1-9][0-9]* ))* ;

// ADL keywords
SYM_ARCHETYPE            : [Aa][Rr][Cc][Hh][Ee][Tt][Yy][Pp][Ee] ;
SYM_TEMPLATE_OVERLAY     : [Tt][Ee][Mm][Pp][Ll][Aa][Tt][Ee]'_'[Oo][Vv][Ee][Rr][Ll][Aa][Yy] ;
SYM_TEMPLATE             : [Tt][Ee][Mm][Pp][Ll][Aa][Tt][Ee] ;
SYM_OPERATIONAL_TEMPLATE : [Oo][Pp][Ee][Rr][Aa][Tt][Ii][Oo][Nn][Aa][Ll]'_'[Tt][Ee][Mm][Pp][Ll][Aa][Tt][Ee] ;

SYM_SPECIALIZE  : '\n'[Ss][Pp][Ee][Cc][Ii][Aa][Ll][Ii][SsZz][Ee] ;
SYM_LANGUAGE    : '\n'[Ll][Aa][Nn][Gg][Uu][Aa][Gg][Ee] ;
SYM_DESCRIPTION : '\n'[Dd][Ee][Ss][Cc][Rr][Ii][Pp][Tt][Ii][Oo][Nn] ;
SYM_DEFINITION  : '\n'[Dd][Ee][Ff][Ii][Nn][Ii][Tt][Ii][Oo][Nn] ;
SYM_RULES       : '\n'[Rr][Uu][Ll][Ee][Ss] ;
SYM_TERMINOLOGY : '\n'[Tt][Ee][Rr][Mm][Ii][Nn][Oo][Ll][Oo][Gg][Yy] ;
SYM_ANNOTATIONS : '\n'[Aa][Nn][Nn][Oo][Tt][Aa][Tt][Ii][Oo][Nn][Ss] ;
SYM_COMPONENT_TERMINOLOGIES : '\n'[Cc][Oo][Mm][Pp][Oo][Nn][Ee][Nn][Tt]'_'[Tt][Ee][Rr][Mm][Ii][Nn][Oo][Ll][Oo][Gg][Ii][Ee][Ss] ;

// meta-data keywords
SYM_ADL_VERSION     : [Aa][Dd][Ll]'_'[Vv][Ee][Rr][Ss][Ii][Oo][Nn] ;
SYM_RM_RELEASE      : [Rr][Mm]'_'[Rr][Ee][Ll][Ee][Aa][Ss][Ee] ;
SYM_IS_CONTROLLED   : [Cc][Oo][Nn][Nn][Tt][Rr][Oo][Ll][Ll][Ee][Dd] ;
SYM_IS_GENERATED    : [Gg][Ee][Nn][Ee][Rr][Aa][Tt][Ee][Dd] ;
SYM_UID             : [Uu][Ii][Dd] ;
SYM_BUILD_UID       : [Bb][Uu][Ii][Ll][Dd]'_'[Uu][Ii][Dd] ;

// CADL keywords
SYM_EXISTENCE   : [Ee][Xx][Ii][Ss][Tt][Ee][Nn][Cc][Ee] ;
SYM_OCCURRENCES : [Oo][Cc][Cc][Uu][Rr][Rr][Ee][Nn][Cc][Ee][Ss] ;
SYM_CARDINALITY : [Cc][Aa][Rr][Dd][Ii][Nn][Aa][Ll][Ii][Tt][Yy] ;
SYM_ORDERED     : [Oo][Rr][Dd][Ee][Rr][Ee][Dd] ;
SYM_UNORDERED   : [Uu][Nn][Oo][Rr][Dd][Ee][Rr][Ee][Dd] ;
SYM_UNIQUE      : [Uu][Nn][Ii][Qq][Uu][Ee] ;
SYM_USE_NODE    : [Uu][Ss][Ee][_][Nn][Oo][Dd][Ee] ;
SYM_USE_ARCHETYPE : [Uu][Ss][Ee][_][Aa][Rr][Cc][Hh][Ee][Tt][Yy][Pp][Ee] ;
SYM_ALLOW_ARCHETYPE : [Aa][Ll][Ll][Oo][Ww][_][Aa][Rr][Cc][Hh][Ee][Tt][Yy][Pp][Ee] ;
SYM_INCLUDE     : [Ii][Nn][Cc][Ll][Uu][Dd][Ee] ;
SYM_EXCLUDE     : [Ee][Xx][Cc][Ll][Uu][Dd][Ee] ;
SYM_AFTER       : [Aa][Ff][Tt][Ee][Rr] ;
SYM_BEFORE      : [Bb][Ee][Ff][Oo][Rr][Ee] ;
SYM_CLOSED      : [Cc][Ll][Oo][Ss][Ee][Dd] ;

SYM_THEN     : [Tt][Hh][Ee][Nn] ;
SYM_AND      : [Aa][Nn][Dd] ;
SYM_OR       : [Oo][Rr] ;
SYM_XOR      : [Xx][Oo][Rr] ;
SYM_NOT      : [Nn][Oo][Tt] ;
SYM_IMPLIES  : [Ii][Mm][Pp][Ll][Ii][Ee][Ss] ;
SYM_FOR_ALL  : [Ff][Oo][Rr][_][Aa][Ll][Ll] ;
SYM_EXISTS   : [Ee][Xx][Ii][Ss][Tt][Ss] ;

SYM_MATCHES : ([Mm][Aa][Tt][Cc][Hh][Ee][Ss] | [Ii][Ss]'_'[Ii][Nn] | '\u2208');

SYM_TRUE : [Tt][Rr][Uu][Ee] ;
SYM_FALSE : [Ff][Aa][Ll][Ss][Ee] ;

// ---------- whitespace & comments ----------

WS :        [ \t\r]+      -> skip ;
LINE :      '\n'          -> skip ;    // increment line count
HLINE :     '--------------------*' ;  // special comment line used as a horizontal separator
CMT_LINE :  '--'.*?'\n'   -> skip ;    // (increment line count)

// ---------- ISO8601 Date/Time values ----------

ISO8601_DATE      : YEAR '-' MONTH ( '-' DAY )? ;
ISO8601_TIME      : HOUR ':' MINUTE ( ':' SECOND ( ',' INTEGER )?)? ( TIMEZONE )? ;
ISO8601_DATE_TIME : YEAR '-' MONTH '-' DAY 'T' HOUR (':' MINUTE (':' SECOND ( ',' DIGIT+ )?)?)? ( TIMEZONE )? ;
fragment TIMEZONE : 'Z' | ('+'|'-') HOUR_MIN ;   // hour offset, e.g. `+0930`, or else literal `Z` indicating +0000.
fragment YEAR     : [1-9][0-9]* ;
fragment MONTH    : ( [0][0-9] | [1][0-2] ) ;    // month in year
fragment DAY      : ( [012][0-9] | [3][0-2] ) ;  // day in month
fragment HOUR     : ( [01]?[0-9] | [2][0-3] ) ;  // hour in 24 hour clock
fragment MINUTE   : [0-5][0-9] ;                 // minutes
fragment HOUR_MIN : ( [01]?[0-9] | [2][0-3] ) [0-5][0-9] ;  // hour / minutes quad digit pattern
fragment SECOND   : [0-5][0-9] ;                 // seconds

// ISO8601 DURATION PnYnMnWnDTnnHnnMnn.nnnS 
// here we allow a deviation from the standard to allow weeks to be // mixed in with the rest since this commonly occurs in medicine
// TODO: the following will incorrectly match just 'P'
ISO8601_DURATION : 'P'(DIGIT+[yY])?(DIGIT+[mM])?(DIGIT+[wW])?(DIGIT+[dD])?('T'(DIGIT+[hH])?(DIGIT+[mM])?(DIGIT+('.'DIGIT+)?[sS])?)? ;

// ---------------------- Identifiers ---------------------

ARCHETYPE_HRID      : ARCHETYPE_HRID_ROOT '.v' VERSION_ID ;
ARCHETYPE_REF       : ARCHETYPE_HRID_ROOT '.v' INTEGER ( '.' DIGIT+ )* ;
ARCHETYPE_HRID_ROOT : (NAMESPACE '::')? IDENTIFIER '-' IDENTIFIER '-' IDENTIFIER '.' IDENTIFIER ;

fragment NAMESPACE  : LABEL ('.' LABEL)+ ;
fragment LABEL      : ALPHA_CHAR ( NAME_CHAR* ALPHANUM_CHAR )? ;
fragment IDENTIFIER : ALPHA_CHAR WORD_CHAR* ;
VERSION_ID : DIGIT+ DOT_SEGMENT DOT_SEGMENT (('-rc' | '-alpha') DOT_SEGMENT? )? ;
fragment DOT_SEGMENT : '.' DIGIT+ ;

GUID : HEX_DIGIT+ '-' HEX_DIGIT+ '-' HEX_DIGIT+ '-' HEX_DIGIT+ '-' HEX_DIGIT+ ;

ALPHA_UC_ID : ALPHA_UCHAR WORD_CHAR* ;                      // used for type ids
ALPHA_LC_ID : ALPHA_LCHAR WORD_CHAR* ;                      // used for attribute / method ids

// --------------------- primitive types -------------------

TERM_CODE_REF : '[' NAME_CHAR+ ( '(' NAME_CHAR+ ')' )? '::' NAME_CHAR+ ']' ;  // e.g. [ICD10AM(1998)::F23]; [ISO_639-1::en]
URI : [a-z]+ ':' ( '//' | '/' )? (~[ \t\n>]+)? ; // just a simple recogniser, the full thing isn't required

INTEGER : DIGIT+ E_SUFFIX? ;
REAL :    DIGIT+ '.' DIGIT+ E_SUFFIX? ;
fragment E_SUFFIX : [eE][+-]? DIGIT+ ;

STRING : '"' STRING_CHAR*? '"' ;
fragment STRING_CHAR : ~[\\"] | ESCAPE_SEQ | UTF8CHAR ;


CHARACTER : '\'' CHAR '\'' ;
fragment CHAR : ~[\\'\r\n] | ESCAPE_SEQ | UTF8CHAR  ;

fragment ESCAPE_SEQ: '\\' ['"?abfnrtv\\] ;

// ------------------- character fragments ------------------

fragment NAME_CHAR     : WORD_CHAR | '-' ;
fragment WORD_CHAR     : ALPHANUM_CHAR | '_' ;
fragment ALPHANUM_CHAR : ALPHA_CHAR | DIGIT ;

fragment ALPHA_CHAR  : [a-zA-Z] ;
fragment ALPHA_UCHAR : [A-Z] ;
fragment ALPHA_LCHAR : [a-z] ;
fragment UTF8CHAR    : '\\u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT ;

fragment DIGIT     : [0-9] ;
fragment HEX_DIGIT : [0-9a-fA-F] ;

BRACKETS : [()<>\[\]{}];
ARITHMETIC_OPERATORS : [-+*/%^=];
MISC : [;];

SYM_LIST_CONTINUE: '...' ;
SYM_INTERVAL_SEP: '..' ;


DATE_CONSTRAINT_PATTERN :       YEAR_PATTERN '-' MONTH_PATTERN '-' DAY_PATTERN ;
TIME_CONSTRAINT_PATTERN :       HOUR_PATTERN ':' MINUTE_PATTERN ':' SECOND_PATTERN ;
DATE_TIME_CONSTRAINT_PATTERN :  DATE_CONSTRAINT_PATTERN 'T' TIME_CONSTRAINT_PATTERN ;
DURATION_CONSTRAINT_PATTERN :   'P' [yY]?[mM]?[Ww]?[dD]? ('T' [hH]?[mM]?[sS]?)? ;

// date time pattern
fragment YEAR_PATTERN:	 		('yyy' 'y'?) | ('YYY' 'Y'?);
fragment MONTH_PATTERN:	        'mm' | 'MM' | '??' | 'XX';
fragment DAY_PATTERN:			'dd' | 'DD' | '??' | 'XX';
fragment HOUR_PATTERN:			'hh' | 'HH' | '??' | 'XX';
fragment MINUTE_PATTERN:	    'mm' | 'MM' | '??' | 'XX';
fragment SECOND_PATTERN:		'ss' | 'SS' | '??' | 'XX';



