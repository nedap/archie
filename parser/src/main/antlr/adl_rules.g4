//
//  description: Antlr4 grammar for Rules sub-syntax of Archetype Definition Language (ADL2)
//  author:      Thomas Beale <thomas.beale@openehr.org>
//  support:     openEHR Specifications PR tracker <https://openehr.atlassian.net/projects/SPECPR/issues>
//  copyright:   Copyright (c) 2015 openEHR Foundation
//  license:     Apache 2.0 License <http://www.apache.org/licenses/LICENSE-2.0.html>
//
//TODO: We could rebuild this based on a modified xpath-grammar. Will make this easier to comply with xpath syntax

grammar adl_rules;
import cadl_primitives;

//
//  ============== Parser rules ==============
//

assertion_list: (assertion (';'))+ ;// {_input.LA(1) == WS || _input.LA(1) == LINE}?) +;//whitespace parsing to prevent ambiguity

assertion: variable_declaration | boolean_assertion;

variable_declaration: SYM_VARIABLE_START identifier SYM_COLON identifier SYM_ASSIGNMENT (boolean_expression | arithmetic_expression);

boolean_assertion: ( identifier SYM_COLON )? boolean_expression ;

//
// Expressions evaluating to boolean values
//


boolean_expression
    : boolean_for_all_expression
    | boolean_expression SYM_IMPLIES boolean_for_all_expression
    ;

boolean_for_all_expression
    : boolean_or_expression
    | SYM_FOR_ALL SYM_VARIABLE_START identifier SYM_IN (adl_rules_path | variable_reference) SYM_SATISFIES? boolean_for_all_expression;

boolean_or_expression
    : boolean_and_expression
    | boolean_or_expression SYM_OR boolean_and_expression
    ;

boolean_and_expression
	:	boolean_xor_expression
	|	boolean_and_expression SYM_AND boolean_xor_expression
	;

boolean_xor_expression
	:	boolean_constraint_expression
	|	boolean_xor_expression SYM_XOR boolean_constraint_expression
	;

boolean_constraint_expression
    : boolean_constraint
    | boolean_leaf;


boolean_constraint: ( adl_rules_path | adl_rules_relative_path ) SYM_MATCHES ('{' c_primitive_object '}' | CONTAINED_REGEXP );

boolean_leaf:
      boolean_literal
    //| adl_path
    | variable_reference
    | SYM_EXISTS adl_rules_path
    | '(' boolean_expression ')'
    | arithmetic_relop_expr
    | SYM_NOT boolean_leaf
    ;

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
   | <assoc=right> pow_expression '^' arithmetic_leaf
   ;

arithmetic_leaf:
      integer_value
    | real_value
    | adl_rules_path
    | variable_reference
    | '(' arithmetic_expression ')'
    | '-' arithmetic_leaf
    ;

adl_rules_path          : variable_reference? adl_rules_path_segment+;//(adl_path_segment ({_input.LA(-1) != WS && _input.LA(-1) != LINE}?))+ adl_path_segment? ;
adl_rules_relative_path : adl_rules_path_element adl_rules_path ;  // TODO: remove when current slots no longer needed
adl_rules_path_segment  : ('/' | '//') adl_rules_path_element;
adl_rules_path_element  : attribute_id ( '[' (ID_CODE | ARCHETYPE_REF) ']' )?;

variable_reference: SYM_VARIABLE_START identifier;

plus_minus_binop: '+' | '-';
mult_binop: '*' | '/' | '%';

relational_binop:
      '='
    | '!='
    | '<='
    | '<'
    | '>='
    | '>'
    ;

SYM_FOR_ALL:
    'for_all'
    | '∀'
    | 'every' //if we follow xpath syntax, let's do that here as well (xpath 2 and xpath 3)
    ;

SYM_IN:
    'in'; //should be | '∈';, but that clashes with SYM_MATCHES, wich is also '∈'.

SYM_SATISFIES:
    'satisfies'; //from xpath - solves some parser ambiguity in future cases!



