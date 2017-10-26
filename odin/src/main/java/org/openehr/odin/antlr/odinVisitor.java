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

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link odinParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface odinVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link odinParser#odin_text}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOdin_text(odinParser.Odin_textContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#attr_vals}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttr_vals(odinParser.Attr_valsContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#attr_val}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttr_val(odinParser.Attr_valContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#object_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject_block(odinParser.Object_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#object_value_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject_value_block(odinParser.Object_value_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#keyed_object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyed_object(odinParser.Keyed_objectContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#primitive_object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitive_object(odinParser.Primitive_objectContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#primitive_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitive_value(odinParser.Primitive_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#primitive_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitive_list_value(odinParser.Primitive_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#primitive_interval_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitive_interval_value(odinParser.Primitive_interval_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#object_reference_block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject_reference_block(odinParser.Object_reference_blockContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#odin_path_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOdin_path_list(odinParser.Odin_path_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#odin_path}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOdin_path(odinParser.Odin_pathContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#odin_path_segment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOdin_path_segment(odinParser.Odin_path_segmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#odin_path_element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOdin_path_element(odinParser.Odin_path_elementContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#string_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_value(odinParser.String_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#string_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_list_value(odinParser.String_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#integer_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger_value(odinParser.Integer_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#integer_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger_list_value(odinParser.Integer_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#integer_interval_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger_interval_value(odinParser.Integer_interval_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#integer_interval_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger_interval_list_value(odinParser.Integer_interval_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#real_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReal_value(odinParser.Real_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#real_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReal_list_value(odinParser.Real_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#real_interval_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReal_interval_value(odinParser.Real_interval_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#real_interval_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReal_interval_list_value(odinParser.Real_interval_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#boolean_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_value(odinParser.Boolean_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#boolean_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_list_value(odinParser.Boolean_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#character_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacter_value(odinParser.Character_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#character_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacter_list_value(odinParser.Character_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#date_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate_value(odinParser.Date_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#date_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate_list_value(odinParser.Date_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#date_interval_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate_interval_value(odinParser.Date_interval_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#date_interval_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate_interval_list_value(odinParser.Date_interval_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#time_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTime_value(odinParser.Time_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#time_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTime_list_value(odinParser.Time_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#time_interval_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTime_interval_value(odinParser.Time_interval_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#time_interval_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTime_interval_list_value(odinParser.Time_interval_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#date_time_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate_time_value(odinParser.Date_time_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#date_time_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate_time_list_value(odinParser.Date_time_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#date_time_interval_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate_time_interval_value(odinParser.Date_time_interval_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#date_time_interval_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate_time_interval_list_value(odinParser.Date_time_interval_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#duration_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDuration_value(odinParser.Duration_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#duration_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDuration_list_value(odinParser.Duration_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#duration_interval_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDuration_interval_value(odinParser.Duration_interval_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#duration_interval_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDuration_interval_list_value(odinParser.Duration_interval_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#term_code_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm_code_value(odinParser.Term_code_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#term_code_list_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm_code_list_value(odinParser.Term_code_list_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#uri_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUri_value(odinParser.Uri_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#relop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelop(odinParser.RelopContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#rm_type_id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRm_type_id(odinParser.Rm_type_idContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#rm_attribute_id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRm_attribute_id(odinParser.Rm_attribute_idContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(odinParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link odinParser#archetype_ref}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArchetype_ref(odinParser.Archetype_refContext ctx);
}