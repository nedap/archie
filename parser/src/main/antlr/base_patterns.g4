//
//  General purpose patterns used in all openEHR parser and lexer tools
//

grammar base_patterns;
import AdlVocabulary;

//
// -------------------------- Parse Rules --------------------------
//

type_id      : ALPHA_UC_ID ( '<' type_id ( ',' type_id )* '>' )? ;
attribute_id : ALPHA_LC_ID ;
identifier   : ALPHA_UC_ID | ALPHA_LC_ID ;