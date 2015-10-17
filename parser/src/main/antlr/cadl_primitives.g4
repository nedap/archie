//
// description: Antlr4 grammar for cADL primitives sub-syntax of Archetype Definition Language (ADL2)
// author:      Thomas Beale <thomas.beale@openehr.org>
// support:     openEHR Specifications PR tracker <https://openehr.atlassian.net/projects/SPECPR/issues>
// copyright:   Copyright (c) 2015 openEHR Foundation
// license:     Apache 2.0 License <http://www.apache.org/licenses/LICENSE-2.0.html>
//

grammar cadl_primitives;

import odin_values, AdlVocabulary;

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

c_integer: ( integer_value | integer_list_value | integer_interval_value | integer_interval_list_value ) ( ';' integer_value )? ;

c_real: ( real_value | real_list_value | real_interval_value | real_interval_list_value ) ( ';' real_value )? ;

c_date: ( ISO8601_DATE | date_value | date_list_value | date_interval_value | date_interval_list_value ) ( ';' date_value )? ;

c_time: ( ISO8601_TIME | time_value | time_list_value | time_interval_value | time_interval_list_value ) ( ';' time_value )? ;

c_date_time: ( ISO8601_DATE_TIME | date_time_value | date_time_list_value | date_time_interval_value | date_time_interval_list_value ) ( ';' date_time_value )? ;

c_duration: (
      ISO8601_DURATION ( '/' ( duration_interval_value | duration_value ))?
    | duration_value | duration_list_value | duration_interval_value | duration_interval_list_value ) ( ';' duration_value )?
    ;

c_string: 
    ( STRING 
    | string_list_value
    | '/' ~('/') '/'    // regexp, todo, add fragment
    | '^' ~('^') '^'    // regexp, todo, add fragment
    ) ( ';' string_value )? 
    ;

// ADL2 term types: [ac3], [ac3; at5], [at5]
c_terminology_code: '[' ( ( AC_CODE ( ';' AT_CODE )? ) | AT_CODE ) ']' ;

c_boolean: ( boolean_value | boolean_list_value ) ( ';' boolean_value )? ;

adl_path : adl_path_segment+ ;
adl_relative_path : adl_path_element adl_path ;  // TODO: remove when current slots no longer needed
adl_path_segment  : '/' adl_path_element ;
adl_path_element  : attribute_id ( '[' ID_CODE ']' )? ;


//
//  ======================= Lexical rules ========================
//

// ---------- various ADL2 codes




