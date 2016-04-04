//
//  description: Antlr4 grammar for Rules sub-syntax of Archetype Definition Language (ADL2)
//  author:      Thomas Beale <thomas.beale@openehr.org>
//  support:     openEHR Specifications PR tracker <https://openehr.atlassian.net/projects/SPECPR/issues>
//  copyright:   Copyright (c) 2015 openEHR Foundation
//  license:     Apache 2.0 License <http://www.apache.org/licenses/LICENSE-2.0.html>
//

grammar adl_rules;
import cadl_primitives;

//
//  ============== Parser rules ==============
//

assertion: variable_declaration | boolean_assertion;

variable_declaration: '$' identifier ':' identifier '::=' (boolean_expression | arithmetic_expression | adl_path | adl_relative_path);

boolean_assertion: ( identifier ':' )? boolean_expression ;

//
// Expressions evaluating to boolean values
//

boolean_expression
    : boolean_or_expression
    | boolean_expression SYM_IMPLIES boolean_or_expression
    ;

boolean_or_expression
    : boolean_and_expression
    | boolean_or_expression SYM_OR boolean_and_expression
    ;

boolean_and_expression
	:	boolean_xor_expression
	|	boolean_and_expression SYM_AND boolean_xor_expression
	;

boolean_xor_expression
	:	boolean_leaf
	|	boolean_xor_expression SYM_XOR boolean_leaf
	;


boolean_leaf:
      boolean_literal
    | adl_path
    | SYM_EXISTS adl_path
    | boolean_constraint
    | '(' boolean_expression ')'
    | arithmetic_relop_expr
    | SYM_NOT boolean_leaf
    ;

boolean_constraint: ( adl_path | adl_relative_path ) SYM_MATCHES ('{' c_primitive_object '}' | CONTAINED_REGEXP );

boolean_literal:
      SYM_TRUE
    | SYM_FALSE
    ;

//
// Expressions evaluating to arithmetic values
//

arithmetic_relop_expr: arithmetic_expression relational_binop arithmetic_expression ;


arithmetic_expression
   : multiplying_expression
   | arithmetic_expression plus_minus_binop multiplying_expression
   ;

multiplying_expression
   : pow_expression
   | multiplying_expression mult_binop pow_expression
   ;

pow_expression
   : arithmetic_leaf
   | pow_expression pow_binop arithmetic_leaf
   ;

arithmetic_leaf:
      integer_value
    | real_value
    | adl_path
    | variable_reference
    | '(' arithmetic_expression ')'
    | '-' arithmetic_leaf
    ;

variable_reference: '$' identifier;

plus_minus_binop: '+' | '-';
mult_binop: '*' | '/' | '%';
pow_binop: '^';

relational_binop:
      '='
    | '!='
    | '<='
    | '<'
    | '>='
    | '>'
    ;

