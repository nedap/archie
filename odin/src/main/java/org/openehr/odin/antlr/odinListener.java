package org.openehr.odin.antlr;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017 Cognitive Medical Systems
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 * Author: Claude Nanjo
 */

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link odinParser}.
 */
public interface odinListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link odinParser#odin_text}.
	 * @param ctx the parse tree
	 */
	void enterOdin_text(odinParser.Odin_textContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#odin_text}.
	 * @param ctx the parse tree
	 */
	void exitOdin_text(odinParser.Odin_textContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#attr_vals}.
	 * @param ctx the parse tree
	 */
	void enterAttr_vals(odinParser.Attr_valsContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#attr_vals}.
	 * @param ctx the parse tree
	 */
	void exitAttr_vals(odinParser.Attr_valsContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#attr_val}.
	 * @param ctx the parse tree
	 */
	void enterAttr_val(odinParser.Attr_valContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#attr_val}.
	 * @param ctx the parse tree
	 */
	void exitAttr_val(odinParser.Attr_valContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#object_block}.
	 * @param ctx the parse tree
	 */
	void enterObject_block(odinParser.Object_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#object_block}.
	 * @param ctx the parse tree
	 */
	void exitObject_block(odinParser.Object_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#object_value_block}.
	 * @param ctx the parse tree
	 */
	void enterObject_value_block(odinParser.Object_value_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#object_value_block}.
	 * @param ctx the parse tree
	 */
	void exitObject_value_block(odinParser.Object_value_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#keyed_object}.
	 * @param ctx the parse tree
	 */
	void enterKeyed_object(odinParser.Keyed_objectContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#keyed_object}.
	 * @param ctx the parse tree
	 */
	void exitKeyed_object(odinParser.Keyed_objectContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#primitive_object}.
	 * @param ctx the parse tree
	 */
	void enterPrimitive_object(odinParser.Primitive_objectContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#primitive_object}.
	 * @param ctx the parse tree
	 */
	void exitPrimitive_object(odinParser.Primitive_objectContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#primitive_value}.
	 * @param ctx the parse tree
	 */
	void enterPrimitive_value(odinParser.Primitive_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#primitive_value}.
	 * @param ctx the parse tree
	 */
	void exitPrimitive_value(odinParser.Primitive_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#primitive_list_value}.
	 * @param ctx the parse tree
	 */
	void enterPrimitive_list_value(odinParser.Primitive_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#primitive_list_value}.
	 * @param ctx the parse tree
	 */
	void exitPrimitive_list_value(odinParser.Primitive_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#primitive_interval_value}.
	 * @param ctx the parse tree
	 */
	void enterPrimitive_interval_value(odinParser.Primitive_interval_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#primitive_interval_value}.
	 * @param ctx the parse tree
	 */
	void exitPrimitive_interval_value(odinParser.Primitive_interval_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#object_reference_block}.
	 * @param ctx the parse tree
	 */
	void enterObject_reference_block(odinParser.Object_reference_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#object_reference_block}.
	 * @param ctx the parse tree
	 */
	void exitObject_reference_block(odinParser.Object_reference_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#odin_path_list}.
	 * @param ctx the parse tree
	 */
	void enterOdin_path_list(odinParser.Odin_path_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#odin_path_list}.
	 * @param ctx the parse tree
	 */
	void exitOdin_path_list(odinParser.Odin_path_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#odin_path}.
	 * @param ctx the parse tree
	 */
	void enterOdin_path(odinParser.Odin_pathContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#odin_path}.
	 * @param ctx the parse tree
	 */
	void exitOdin_path(odinParser.Odin_pathContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#odin_path_segment}.
	 * @param ctx the parse tree
	 */
	void enterOdin_path_segment(odinParser.Odin_path_segmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#odin_path_segment}.
	 * @param ctx the parse tree
	 */
	void exitOdin_path_segment(odinParser.Odin_path_segmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#odin_path_element}.
	 * @param ctx the parse tree
	 */
	void enterOdin_path_element(odinParser.Odin_path_elementContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#odin_path_element}.
	 * @param ctx the parse tree
	 */
	void exitOdin_path_element(odinParser.Odin_path_elementContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#string_value}.
	 * @param ctx the parse tree
	 */
	void enterString_value(odinParser.String_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#string_value}.
	 * @param ctx the parse tree
	 */
	void exitString_value(odinParser.String_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#string_list_value}.
	 * @param ctx the parse tree
	 */
	void enterString_list_value(odinParser.String_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#string_list_value}.
	 * @param ctx the parse tree
	 */
	void exitString_list_value(odinParser.String_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#integer_value}.
	 * @param ctx the parse tree
	 */
	void enterInteger_value(odinParser.Integer_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#integer_value}.
	 * @param ctx the parse tree
	 */
	void exitInteger_value(odinParser.Integer_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#integer_list_value}.
	 * @param ctx the parse tree
	 */
	void enterInteger_list_value(odinParser.Integer_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#integer_list_value}.
	 * @param ctx the parse tree
	 */
	void exitInteger_list_value(odinParser.Integer_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#integer_interval_value}.
	 * @param ctx the parse tree
	 */
	void enterInteger_interval_value(odinParser.Integer_interval_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#integer_interval_value}.
	 * @param ctx the parse tree
	 */
	void exitInteger_interval_value(odinParser.Integer_interval_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#integer_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void enterInteger_interval_list_value(odinParser.Integer_interval_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#integer_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void exitInteger_interval_list_value(odinParser.Integer_interval_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#real_value}.
	 * @param ctx the parse tree
	 */
	void enterReal_value(odinParser.Real_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#real_value}.
	 * @param ctx the parse tree
	 */
	void exitReal_value(odinParser.Real_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#real_list_value}.
	 * @param ctx the parse tree
	 */
	void enterReal_list_value(odinParser.Real_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#real_list_value}.
	 * @param ctx the parse tree
	 */
	void exitReal_list_value(odinParser.Real_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#real_interval_value}.
	 * @param ctx the parse tree
	 */
	void enterReal_interval_value(odinParser.Real_interval_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#real_interval_value}.
	 * @param ctx the parse tree
	 */
	void exitReal_interval_value(odinParser.Real_interval_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#real_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void enterReal_interval_list_value(odinParser.Real_interval_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#real_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void exitReal_interval_list_value(odinParser.Real_interval_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#boolean_value}.
	 * @param ctx the parse tree
	 */
	void enterBoolean_value(odinParser.Boolean_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#boolean_value}.
	 * @param ctx the parse tree
	 */
	void exitBoolean_value(odinParser.Boolean_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#boolean_list_value}.
	 * @param ctx the parse tree
	 */
	void enterBoolean_list_value(odinParser.Boolean_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#boolean_list_value}.
	 * @param ctx the parse tree
	 */
	void exitBoolean_list_value(odinParser.Boolean_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#character_value}.
	 * @param ctx the parse tree
	 */
	void enterCharacter_value(odinParser.Character_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#character_value}.
	 * @param ctx the parse tree
	 */
	void exitCharacter_value(odinParser.Character_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#character_list_value}.
	 * @param ctx the parse tree
	 */
	void enterCharacter_list_value(odinParser.Character_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#character_list_value}.
	 * @param ctx the parse tree
	 */
	void exitCharacter_list_value(odinParser.Character_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#date_value}.
	 * @param ctx the parse tree
	 */
	void enterDate_value(odinParser.Date_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#date_value}.
	 * @param ctx the parse tree
	 */
	void exitDate_value(odinParser.Date_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#date_list_value}.
	 * @param ctx the parse tree
	 */
	void enterDate_list_value(odinParser.Date_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#date_list_value}.
	 * @param ctx the parse tree
	 */
	void exitDate_list_value(odinParser.Date_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#date_interval_value}.
	 * @param ctx the parse tree
	 */
	void enterDate_interval_value(odinParser.Date_interval_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#date_interval_value}.
	 * @param ctx the parse tree
	 */
	void exitDate_interval_value(odinParser.Date_interval_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#date_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void enterDate_interval_list_value(odinParser.Date_interval_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#date_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void exitDate_interval_list_value(odinParser.Date_interval_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#time_value}.
	 * @param ctx the parse tree
	 */
	void enterTime_value(odinParser.Time_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#time_value}.
	 * @param ctx the parse tree
	 */
	void exitTime_value(odinParser.Time_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#time_list_value}.
	 * @param ctx the parse tree
	 */
	void enterTime_list_value(odinParser.Time_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#time_list_value}.
	 * @param ctx the parse tree
	 */
	void exitTime_list_value(odinParser.Time_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#time_interval_value}.
	 * @param ctx the parse tree
	 */
	void enterTime_interval_value(odinParser.Time_interval_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#time_interval_value}.
	 * @param ctx the parse tree
	 */
	void exitTime_interval_value(odinParser.Time_interval_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#time_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void enterTime_interval_list_value(odinParser.Time_interval_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#time_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void exitTime_interval_list_value(odinParser.Time_interval_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#date_time_value}.
	 * @param ctx the parse tree
	 */
	void enterDate_time_value(odinParser.Date_time_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#date_time_value}.
	 * @param ctx the parse tree
	 */
	void exitDate_time_value(odinParser.Date_time_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#date_time_list_value}.
	 * @param ctx the parse tree
	 */
	void enterDate_time_list_value(odinParser.Date_time_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#date_time_list_value}.
	 * @param ctx the parse tree
	 */
	void exitDate_time_list_value(odinParser.Date_time_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#date_time_interval_value}.
	 * @param ctx the parse tree
	 */
	void enterDate_time_interval_value(odinParser.Date_time_interval_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#date_time_interval_value}.
	 * @param ctx the parse tree
	 */
	void exitDate_time_interval_value(odinParser.Date_time_interval_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#date_time_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void enterDate_time_interval_list_value(odinParser.Date_time_interval_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#date_time_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void exitDate_time_interval_list_value(odinParser.Date_time_interval_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#duration_value}.
	 * @param ctx the parse tree
	 */
	void enterDuration_value(odinParser.Duration_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#duration_value}.
	 * @param ctx the parse tree
	 */
	void exitDuration_value(odinParser.Duration_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#duration_list_value}.
	 * @param ctx the parse tree
	 */
	void enterDuration_list_value(odinParser.Duration_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#duration_list_value}.
	 * @param ctx the parse tree
	 */
	void exitDuration_list_value(odinParser.Duration_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#duration_interval_value}.
	 * @param ctx the parse tree
	 */
	void enterDuration_interval_value(odinParser.Duration_interval_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#duration_interval_value}.
	 * @param ctx the parse tree
	 */
	void exitDuration_interval_value(odinParser.Duration_interval_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#duration_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void enterDuration_interval_list_value(odinParser.Duration_interval_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#duration_interval_list_value}.
	 * @param ctx the parse tree
	 */
	void exitDuration_interval_list_value(odinParser.Duration_interval_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#term_code_value}.
	 * @param ctx the parse tree
	 */
	void enterTerm_code_value(odinParser.Term_code_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#term_code_value}.
	 * @param ctx the parse tree
	 */
	void exitTerm_code_value(odinParser.Term_code_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#term_code_list_value}.
	 * @param ctx the parse tree
	 */
	void enterTerm_code_list_value(odinParser.Term_code_list_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#term_code_list_value}.
	 * @param ctx the parse tree
	 */
	void exitTerm_code_list_value(odinParser.Term_code_list_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#uri_value}.
	 * @param ctx the parse tree
	 */
	void enterUri_value(odinParser.Uri_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#uri_value}.
	 * @param ctx the parse tree
	 */
	void exitUri_value(odinParser.Uri_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#relop}.
	 * @param ctx the parse tree
	 */
	void enterRelop(odinParser.RelopContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#relop}.
	 * @param ctx the parse tree
	 */
	void exitRelop(odinParser.RelopContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#rm_type_id}.
	 * @param ctx the parse tree
	 */
	void enterRm_type_id(odinParser.Rm_type_idContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#rm_type_id}.
	 * @param ctx the parse tree
	 */
	void exitRm_type_id(odinParser.Rm_type_idContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#rm_attribute_id}.
	 * @param ctx the parse tree
	 */
	void enterRm_attribute_id(odinParser.Rm_attribute_idContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#rm_attribute_id}.
	 * @param ctx the parse tree
	 */
	void exitRm_attribute_id(odinParser.Rm_attribute_idContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(odinParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(odinParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link odinParser#archetype_ref}.
	 * @param ctx the parse tree
	 */
	void enterArchetype_ref(odinParser.Archetype_refContext ctx);
	/**
	 * Exit a parse tree produced by {@link odinParser#archetype_ref}.
	 * @param ctx the parse tree
	 */
	void exitArchetype_ref(odinParser.Archetype_refContext ctx);
}