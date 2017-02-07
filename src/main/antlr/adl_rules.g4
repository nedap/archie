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

assertion: variableDeclaration | booleanAssertion;

variableDeclaration: SYM_VARIABLE_START identifier SYM_COLON identifier SYM_ASSIGNMENT (booleanExpression | plusExpression);

booleanAssertion: ( identifier SYM_COLON )? booleanExpression ;

//
// Expressions evaluating to boolean values
//


booleanExpression
    : booleanForAllExpression
    | booleanExpression SYM_IMPLIES booleanForAllExpression
    ;

booleanForAllExpression
    : booleanOrExpression
    | SYM_FOR_ALL SYM_VARIABLE_START identifier SYM_IN (adlRulesPath | variableReference) SYM_SATISFIES? booleanForAllExpression;

booleanOrExpression
    : booleanAndExpression
    | booleanOrExpression SYM_OR booleanAndExpression
    ;

booleanAndExpression
	:	booleanXorExpression
	|	booleanAndExpression SYM_AND booleanXorExpression
	;

booleanXorExpression
	:	booleanConstraintExpression
	|	booleanXorExpression SYM_XOR booleanConstraintExpression
	;

booleanConstraintExpression
    : booleanConstraint
    | equalityExpression;


booleanConstraint: ( adlRulesPath | adlRulesRelativePath ) SYM_MATCHES ('{' c_primitive_object '}' | CONTAINED_REGEXP );

equalityExpression:
    relOpExpression
    | equalityExpression equalityBinop relOpExpression ;

relOpExpression:
    plusExpression
    | relOpExpression relationalBinop plusExpression ;


//
// Expressions evaluating to all kinds of value types
//

plusExpression
   : multiplyingExpression
   | plusExpression plusMinusBinop multiplyingExpression
   ;

multiplyingExpression
   : powExpression
   | multiplyingExpression multBinop powExpression
   ;

powExpression
   : expressionLeaf
   | <assoc=right> powExpression '^' expressionLeaf
   ;

expressionLeaf:
      booleanLiteral
    | integer_value
    | real_value
    | adlRulesPath
    | SYM_EXISTS adlRulesPath
    | SYM_NOT booleanExpression
    | variableReference
    | '(' booleanExpression ')'
    | '-' expressionLeaf
    ;

adlRulesPath          : variableReference? adlRulesPathSegment+;
adlRulesRelativePath : adlRulesPathElement adlRulesPath ;
adlRulesPathSegment  : ('/' | '//') adlRulesPathElement;
adlRulesPathElement  : attribute_id ( '[' (ID_CODE | ARCHETYPE_REF) ']' )?;

variableReference: SYM_VARIABLE_START identifier;

plusMinusBinop: '+' | '-';
multBinop: '*' | '/' | '%';

equalityBinop:
      '='
    | '!='
    ;

relationalBinop:
    | '<='
    | '<'
    | '>='
    | '>'
    ;

booleanLiteral:
      SYM_TRUE
    | SYM_FALSE
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



