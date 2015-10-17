//
//  description: Antlr4 grammar for Rules sub-syntax of Archetype Definition Language (ADL2)
//  author:      Thomas Beale <thomas.beale@openehr.org>
//  support:     openEHR Specifications PR tracker <https://openehr.atlassian.net/projects/SPECPR/issues>
//  copyright:   Copyright (c) 2015 openEHR Foundation
//  license:     Apache 2.0 License <http://www.apache.org/licenses/LICENSE-2.0.html>
//

grammar adl_rules;


import cadl_primitives, AdlVocabulary;

//
//  ============== Parser rules ==============
//

assertion: ( identifier ':' )? boolean_expr ;

//
// Expressions evaluating to boolean values
//

boolean_expr: boolean_expr boolean_binop boolean_leaf
    | boolean_leaf
    ;

boolean_leaf:
      boolean_literal
    | adl_path
    | SYM_EXISTS adl_path
    | boolean_constraint
    | '(' boolean_expr ')'
    | arithmetic_relop_expr
    | SYM_NOT boolean_leaf
    ;

boolean_constraint: ( adl_path | adl_relative_path ) (matches_regexp_constraint | matches_primitive_constraint ) ;

matches_regexp_constraint: MATCHES_REGEXP;
matches_primitive_constraint: SYM_MATCHES '{' c_primitive_object '}';

boolean_binop:
      SYM_OR
    | SYM_AND
    | SYM_XOR
    | SYM_IMPLIES
    ;

boolean_literal:
      SYM_TRUE
    | SYM_FALSE
    ;

//
// Expressions evaluating to arithmetic values
//

arithmetic_relop_expr: arithmetic_arith_expr relational_binop arithmetic_arith_expr ;

arithmetic_leaf:
      integer_value
    | real_value
    | adl_path
    | '(' arithmetic_arith_expr ')'
    | '-' arithmetic_leaf
    ;

arithmetic_arith_expr: arithmetic_arith_expr arithmetic_binop arithmetic_leaf
    | arithmetic_leaf
    ;

relational_binop:
      '='
    | '!='
    | '<='
    | '<'
    | '>='
    | '>'
    ;

arithmetic_binop:
      '/'
    | '*'
    | '+'
    | '-'
    | '^'
    ;
