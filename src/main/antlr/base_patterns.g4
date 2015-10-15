//
//  General purpose patterns used in all openEHR parser and lexer tools
//

grammar base_patterns;

//
// -------------------------- Parse Rules --------------------------
//

type_id      : ALPHA_UC_ID ( '<' type_id ( ',' type_id )* '>' )? ;
attribute_id : ALPHA_LC_ID ;
identifier   : ALPHA_UC_ID | ALPHA_LC_ID ;

//
// -------------------------- Lexer patterns --------------------------
//

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

NAMESPACE           : LABEL ('.' LABEL)+ ;
fragment LABEL      : ALPHA_CHAR ( NAME_CHAR* ALPHANUM_CHAR )? ;
fragment IDENTIFIER : ALPHA_CHAR WORD_CHAR* ;
VERSION_ID : DIGIT+ DOT_SEGMENT DOT_SEGMENT (('-rc' | '-alpha') DOT_SEGMENT? )? ;
fragment DOT_SEGMENT : '.' DIGIT+ ;

GUID : HEX_DIGIT+ '-' HEX_DIGIT+ '-' HEX_DIGIT+ '-' HEX_DIGIT+ '-' HEX_DIGIT+ ;

ALPHA_UC_ID : ALPHA_UCHAR WORD_CHAR* ;                      // used for type ids
ALPHA_LC_ID : ALPHA_LCHAR WORD_CHAR* ;                      // used for attribute / method ids

// --------------------- primitive types -------------------

TERM_CODE_REF : '[' NAME_CHAR+ ( '(' NAME_CHAR+ ')' )? '::' NAME_CHAR+ ']' ;  // e.g. [ICD10AM(1998)::F23]; [ISO_639-1::en]
URI : [a-z]+ ':' ( '//' | '/' )? ~[ \t\n]+? ; // just a simple recogniser, the full thing isn't required

INTEGER : DIGIT+ E_SUFFIX? ;
REAL :    DIGIT+ '.' DIGIT+ E_SUFFIX? ;
fragment E_SUFFIX : [eE][+-]? DIGIT+ ;

STRING : '"' STRING_CHAR*? '"' ;
fragment STRING_CHAR : ~["\\\r\n] | ESCAPE_SEQ | UTF8CHAR ;

CHARACTER : '\'' CHAR '\'' ;
fragment CHAR : ~['\\\r\n] | ESCAPE_SEQ | UTF8CHAR  ;

fragment ESCAPE_SEQ: '\\' ['"?abfnrtv\\] ;

SYM_TRUE : [Tt][Rr][Uu][Ee] ;
SYM_FALSE : [Ff][Aa][Ll][Ss][Ee] ;

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
