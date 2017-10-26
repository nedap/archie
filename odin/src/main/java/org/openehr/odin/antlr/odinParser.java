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

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class odinParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, SYM_GT=11, SYM_LT=12, SYM_LE=13, SYM_GE=14, SYM_NE=15, SYM_EQ=16, 
		SYM_LIST_CONTINUE=17, SYM_INTERVAL_SEP=18, WS=19, LINE=20, H_CMT_LINE=21, 
		CMT_LINE=22, ISO8601_DATE=23, ISO8601_TIME=24, ISO8601_DATE_TIME=25, ISO8601_DURATION=26, 
		SYM_TRUE=27, SYM_FALSE=28, ARCHETYPE_HRID=29, ARCHETYPE_REF=30, VERSION_ID=31, 
		TERM_CODE_REF=32, URI=33, GUID=34, ALPHA_UC_ID=35, ALPHA_LC_ID=36, INTEGER=37, 
		REAL=38, STRING=39, CHARACTER=40;
	public static final int
		RULE_odin_text = 0, RULE_attr_vals = 1, RULE_attr_val = 2, RULE_object_block = 3, 
		RULE_object_value_block = 4, RULE_keyed_object = 5, RULE_primitive_object = 6, 
		RULE_primitive_value = 7, RULE_primitive_list_value = 8, RULE_primitive_interval_value = 9, 
		RULE_object_reference_block = 10, RULE_odin_path_list = 11, RULE_odin_path = 12, 
		RULE_odin_path_segment = 13, RULE_odin_path_element = 14, RULE_string_value = 15, 
		RULE_string_list_value = 16, RULE_integer_value = 17, RULE_integer_list_value = 18, 
		RULE_integer_interval_value = 19, RULE_integer_interval_list_value = 20, 
		RULE_real_value = 21, RULE_real_list_value = 22, RULE_real_interval_value = 23, 
		RULE_real_interval_list_value = 24, RULE_boolean_value = 25, RULE_boolean_list_value = 26, 
		RULE_character_value = 27, RULE_character_list_value = 28, RULE_date_value = 29, 
		RULE_date_list_value = 30, RULE_date_interval_value = 31, RULE_date_interval_list_value = 32, 
		RULE_time_value = 33, RULE_time_list_value = 34, RULE_time_interval_value = 35, 
		RULE_time_interval_list_value = 36, RULE_date_time_value = 37, RULE_date_time_list_value = 38, 
		RULE_date_time_interval_value = 39, RULE_date_time_interval_list_value = 40, 
		RULE_duration_value = 41, RULE_duration_list_value = 42, RULE_duration_interval_value = 43, 
		RULE_duration_interval_list_value = 44, RULE_term_code_value = 45, RULE_term_code_list_value = 46, 
		RULE_uri_value = 47, RULE_relop = 48, RULE_rm_type_id = 49, RULE_rm_attribute_id = 50, 
		RULE_identifier = 51, RULE_archetype_ref = 52;
	public static final String[] ruleNames = {
		"odin_text", "attr_vals", "attr_val", "object_block", "object_value_block", 
		"keyed_object", "primitive_object", "primitive_value", "primitive_list_value", 
		"primitive_interval_value", "object_reference_block", "odin_path_list", 
		"odin_path", "odin_path_segment", "odin_path_element", "string_value", 
		"string_list_value", "integer_value", "integer_list_value", "integer_interval_value", 
		"integer_interval_list_value", "real_value", "real_list_value", "real_interval_value", 
		"real_interval_list_value", "boolean_value", "boolean_list_value", "character_value", 
		"character_list_value", "date_value", "date_list_value", "date_interval_value", 
		"date_interval_list_value", "time_value", "time_list_value", "time_interval_value", 
		"time_interval_list_value", "date_time_value", "date_time_list_value", 
		"date_time_interval_value", "date_time_interval_list_value", "duration_value", 
		"duration_list_value", "duration_interval_value", "duration_interval_list_value", 
		"term_code_value", "term_code_list_value", "uri_value", "relop", "rm_type_id", 
		"rm_attribute_id", "identifier", "archetype_ref"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'('", "')'", "'['", "']'", "','", "'/'", "'+'", "'-'", "'|'", 
		"'>'", "'<'", "'<='", "'>='", null, "'='", "'...'", "'..'", null, "'\n'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "SYM_GT", 
		"SYM_LT", "SYM_LE", "SYM_GE", "SYM_NE", "SYM_EQ", "SYM_LIST_CONTINUE", 
		"SYM_INTERVAL_SEP", "WS", "LINE", "H_CMT_LINE", "CMT_LINE", "ISO8601_DATE", 
		"ISO8601_TIME", "ISO8601_DATE_TIME", "ISO8601_DURATION", "SYM_TRUE", "SYM_FALSE", 
		"ARCHETYPE_HRID", "ARCHETYPE_REF", "VERSION_ID", "TERM_CODE_REF", "URI", 
		"GUID", "ALPHA_UC_ID", "ALPHA_LC_ID", "INTEGER", "REAL", "STRING", "CHARACTER"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "odin.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public odinParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Odin_textContext extends ParserRuleContext {
		public Attr_valsContext attr_vals() {
			return getRuleContext(Attr_valsContext.class,0);
		}
		public Object_value_blockContext object_value_block() {
			return getRuleContext(Object_value_blockContext.class,0);
		}
		public Odin_textContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_odin_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterOdin_text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitOdin_text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitOdin_text(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Odin_textContext odin_text() throws RecognitionException {
		Odin_textContext _localctx = new Odin_textContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_odin_text);
		try {
			setState(108);
			switch (_input.LA(1)) {
			case ALPHA_LC_ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				attr_vals();
				}
				break;
			case T__1:
			case SYM_LT:
				enterOuterAlt(_localctx, 2);
				{
				setState(107);
				object_value_block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Attr_valsContext extends ParserRuleContext {
		public List<Attr_valContext> attr_val() {
			return getRuleContexts(Attr_valContext.class);
		}
		public Attr_valContext attr_val(int i) {
			return getRuleContext(Attr_valContext.class,i);
		}
		public Attr_valsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attr_vals; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterAttr_vals(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitAttr_vals(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitAttr_vals(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Attr_valsContext attr_vals() throws RecognitionException {
		Attr_valsContext _localctx = new Attr_valsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_attr_vals);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(110);
				attr_val();
				setState(112);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(111);
					match(T__0);
					}
				}

				}
				}
				setState(116); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ALPHA_LC_ID );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Attr_valContext extends ParserRuleContext {
		public Rm_attribute_idContext rm_attribute_id() {
			return getRuleContext(Rm_attribute_idContext.class,0);
		}
		public Object_blockContext object_block() {
			return getRuleContext(Object_blockContext.class,0);
		}
		public Attr_valContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attr_val; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterAttr_val(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitAttr_val(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitAttr_val(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Attr_valContext attr_val() throws RecognitionException {
		Attr_valContext _localctx = new Attr_valContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_attr_val);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			rm_attribute_id();
			setState(119);
			match(SYM_EQ);
			setState(120);
			object_block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Object_blockContext extends ParserRuleContext {
		public Object_value_blockContext object_value_block() {
			return getRuleContext(Object_value_blockContext.class,0);
		}
		public Object_reference_blockContext object_reference_block() {
			return getRuleContext(Object_reference_blockContext.class,0);
		}
		public Object_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterObject_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitObject_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitObject_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Object_blockContext object_block() throws RecognitionException {
		Object_blockContext _localctx = new Object_blockContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_object_block);
		try {
			setState(124);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(122);
				object_value_block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(123);
				object_reference_block();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Object_value_blockContext extends ParserRuleContext {
		public Primitive_objectContext primitive_object() {
			return getRuleContext(Primitive_objectContext.class,0);
		}
		public Rm_type_idContext rm_type_id() {
			return getRuleContext(Rm_type_idContext.class,0);
		}
		public Attr_valsContext attr_vals() {
			return getRuleContext(Attr_valsContext.class,0);
		}
		public List<Keyed_objectContext> keyed_object() {
			return getRuleContexts(Keyed_objectContext.class);
		}
		public Keyed_objectContext keyed_object(int i) {
			return getRuleContext(Keyed_objectContext.class,i);
		}
		public Object_value_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object_value_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterObject_value_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitObject_value_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitObject_value_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Object_value_blockContext object_value_block() throws RecognitionException {
		Object_value_blockContext _localctx = new Object_value_blockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_object_value_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(126);
				match(T__1);
				setState(127);
				rm_type_id();
				setState(128);
				match(T__2);
				}
			}

			setState(132);
			match(SYM_LT);
			setState(143);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(133);
				primitive_object();
				}
				break;
			case 2:
				{
				setState(135);
				_la = _input.LA(1);
				if (_la==ALPHA_LC_ID) {
					{
					setState(134);
					attr_vals();
					}
				}

				}
				break;
			case 3:
				{
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__3) {
					{
					{
					setState(137);
					keyed_object();
					}
					}
					setState(142);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
			setState(145);
			match(SYM_GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Keyed_objectContext extends ParserRuleContext {
		public Primitive_valueContext primitive_value() {
			return getRuleContext(Primitive_valueContext.class,0);
		}
		public Object_blockContext object_block() {
			return getRuleContext(Object_blockContext.class,0);
		}
		public Keyed_objectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyed_object; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterKeyed_object(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitKeyed_object(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitKeyed_object(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Keyed_objectContext keyed_object() throws RecognitionException {
		Keyed_objectContext _localctx = new Keyed_objectContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_keyed_object);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(T__3);
			setState(148);
			primitive_value();
			setState(149);
			match(T__4);
			setState(150);
			match(SYM_EQ);
			setState(151);
			object_block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Primitive_objectContext extends ParserRuleContext {
		public Primitive_valueContext primitive_value() {
			return getRuleContext(Primitive_valueContext.class,0);
		}
		public Primitive_list_valueContext primitive_list_value() {
			return getRuleContext(Primitive_list_valueContext.class,0);
		}
		public Primitive_interval_valueContext primitive_interval_value() {
			return getRuleContext(Primitive_interval_valueContext.class,0);
		}
		public Primitive_objectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitive_object; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterPrimitive_object(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitPrimitive_object(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitPrimitive_object(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Primitive_objectContext primitive_object() throws RecognitionException {
		Primitive_objectContext _localctx = new Primitive_objectContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_primitive_object);
		try {
			setState(156);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(153);
				primitive_value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				primitive_list_value();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(155);
				primitive_interval_value();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Primitive_valueContext extends ParserRuleContext {
		public String_valueContext string_value() {
			return getRuleContext(String_valueContext.class,0);
		}
		public Integer_valueContext integer_value() {
			return getRuleContext(Integer_valueContext.class,0);
		}
		public Real_valueContext real_value() {
			return getRuleContext(Real_valueContext.class,0);
		}
		public Boolean_valueContext boolean_value() {
			return getRuleContext(Boolean_valueContext.class,0);
		}
		public Character_valueContext character_value() {
			return getRuleContext(Character_valueContext.class,0);
		}
		public Term_code_valueContext term_code_value() {
			return getRuleContext(Term_code_valueContext.class,0);
		}
		public Date_valueContext date_value() {
			return getRuleContext(Date_valueContext.class,0);
		}
		public Time_valueContext time_value() {
			return getRuleContext(Time_valueContext.class,0);
		}
		public Date_time_valueContext date_time_value() {
			return getRuleContext(Date_time_valueContext.class,0);
		}
		public Duration_valueContext duration_value() {
			return getRuleContext(Duration_valueContext.class,0);
		}
		public Uri_valueContext uri_value() {
			return getRuleContext(Uri_valueContext.class,0);
		}
		public Primitive_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitive_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterPrimitive_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitPrimitive_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitPrimitive_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Primitive_valueContext primitive_value() throws RecognitionException {
		Primitive_valueContext _localctx = new Primitive_valueContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_primitive_value);
		try {
			setState(169);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(158);
				string_value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(159);
				integer_value();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(160);
				real_value();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(161);
				boolean_value();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(162);
				character_value();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(163);
				term_code_value();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(164);
				date_value();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(165);
				time_value();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(166);
				date_time_value();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(167);
				duration_value();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(168);
				uri_value();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Primitive_list_valueContext extends ParserRuleContext {
		public String_list_valueContext string_list_value() {
			return getRuleContext(String_list_valueContext.class,0);
		}
		public Integer_list_valueContext integer_list_value() {
			return getRuleContext(Integer_list_valueContext.class,0);
		}
		public Real_list_valueContext real_list_value() {
			return getRuleContext(Real_list_valueContext.class,0);
		}
		public Boolean_list_valueContext boolean_list_value() {
			return getRuleContext(Boolean_list_valueContext.class,0);
		}
		public Character_list_valueContext character_list_value() {
			return getRuleContext(Character_list_valueContext.class,0);
		}
		public Term_code_list_valueContext term_code_list_value() {
			return getRuleContext(Term_code_list_valueContext.class,0);
		}
		public Date_list_valueContext date_list_value() {
			return getRuleContext(Date_list_valueContext.class,0);
		}
		public Time_list_valueContext time_list_value() {
			return getRuleContext(Time_list_valueContext.class,0);
		}
		public Date_time_list_valueContext date_time_list_value() {
			return getRuleContext(Date_time_list_valueContext.class,0);
		}
		public Duration_list_valueContext duration_list_value() {
			return getRuleContext(Duration_list_valueContext.class,0);
		}
		public Primitive_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitive_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterPrimitive_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitPrimitive_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitPrimitive_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Primitive_list_valueContext primitive_list_value() throws RecognitionException {
		Primitive_list_valueContext _localctx = new Primitive_list_valueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_primitive_list_value);
		try {
			setState(181);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(171);
				string_list_value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(172);
				integer_list_value();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(173);
				real_list_value();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(174);
				boolean_list_value();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(175);
				character_list_value();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(176);
				term_code_list_value();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(177);
				date_list_value();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(178);
				time_list_value();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(179);
				date_time_list_value();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(180);
				duration_list_value();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Primitive_interval_valueContext extends ParserRuleContext {
		public Integer_interval_valueContext integer_interval_value() {
			return getRuleContext(Integer_interval_valueContext.class,0);
		}
		public Real_interval_valueContext real_interval_value() {
			return getRuleContext(Real_interval_valueContext.class,0);
		}
		public Date_interval_valueContext date_interval_value() {
			return getRuleContext(Date_interval_valueContext.class,0);
		}
		public Time_interval_valueContext time_interval_value() {
			return getRuleContext(Time_interval_valueContext.class,0);
		}
		public Date_time_interval_valueContext date_time_interval_value() {
			return getRuleContext(Date_time_interval_valueContext.class,0);
		}
		public Duration_interval_valueContext duration_interval_value() {
			return getRuleContext(Duration_interval_valueContext.class,0);
		}
		public Primitive_interval_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitive_interval_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterPrimitive_interval_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitPrimitive_interval_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitPrimitive_interval_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Primitive_interval_valueContext primitive_interval_value() throws RecognitionException {
		Primitive_interval_valueContext _localctx = new Primitive_interval_valueContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_primitive_interval_value);
		try {
			setState(189);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(183);
				integer_interval_value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(184);
				real_interval_value();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(185);
				date_interval_value();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(186);
				time_interval_value();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(187);
				date_time_interval_value();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(188);
				duration_interval_value();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Object_reference_blockContext extends ParserRuleContext {
		public Odin_path_listContext odin_path_list() {
			return getRuleContext(Odin_path_listContext.class,0);
		}
		public Object_reference_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object_reference_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterObject_reference_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitObject_reference_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitObject_reference_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Object_reference_blockContext object_reference_block() throws RecognitionException {
		Object_reference_blockContext _localctx = new Object_reference_blockContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_object_reference_block);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			match(SYM_LT);
			setState(192);
			odin_path_list();
			setState(193);
			match(SYM_GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Odin_path_listContext extends ParserRuleContext {
		public List<Odin_pathContext> odin_path() {
			return getRuleContexts(Odin_pathContext.class);
		}
		public Odin_pathContext odin_path(int i) {
			return getRuleContext(Odin_pathContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Odin_path_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_odin_path_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterOdin_path_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitOdin_path_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitOdin_path_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Odin_path_listContext odin_path_list() throws RecognitionException {
		Odin_path_listContext _localctx = new Odin_path_listContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_odin_path_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			odin_path();
			setState(203);
			switch (_input.LA(1)) {
			case T__5:
				{
				setState(198); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(196);
					match(T__5);
					setState(197);
					odin_path();
					}
					}
					setState(200); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case SYM_LIST_CONTINUE:
				{
				setState(202);
				match(SYM_LIST_CONTINUE);
				}
				break;
			case SYM_GT:
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Odin_pathContext extends ParserRuleContext {
		public List<Odin_path_segmentContext> odin_path_segment() {
			return getRuleContexts(Odin_path_segmentContext.class);
		}
		public Odin_path_segmentContext odin_path_segment(int i) {
			return getRuleContext(Odin_path_segmentContext.class,i);
		}
		public Odin_pathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_odin_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterOdin_path(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitOdin_path(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitOdin_path(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Odin_pathContext odin_path() throws RecognitionException {
		Odin_pathContext _localctx = new Odin_pathContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_odin_path);
		int _la;
		try {
			setState(211);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(205);
				match(T__6);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(207); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(206);
					odin_path_segment();
					}
					}
					setState(209); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__6 );
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Odin_path_segmentContext extends ParserRuleContext {
		public Odin_path_elementContext odin_path_element() {
			return getRuleContext(Odin_path_elementContext.class,0);
		}
		public Odin_path_segmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_odin_path_segment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterOdin_path_segment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitOdin_path_segment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitOdin_path_segment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Odin_path_segmentContext odin_path_segment() throws RecognitionException {
		Odin_path_segmentContext _localctx = new Odin_path_segmentContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_odin_path_segment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(T__6);
			setState(214);
			odin_path_element();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Odin_path_elementContext extends ParserRuleContext {
		public Rm_attribute_idContext rm_attribute_id() {
			return getRuleContext(Rm_attribute_idContext.class,0);
		}
		public TerminalNode STRING() { return getToken(odinParser.STRING, 0); }
		public TerminalNode INTEGER() { return getToken(odinParser.INTEGER, 0); }
		public Odin_path_elementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_odin_path_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterOdin_path_element(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitOdin_path_element(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitOdin_path_element(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Odin_path_elementContext odin_path_element() throws RecognitionException {
		Odin_path_elementContext _localctx = new Odin_path_elementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_odin_path_element);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			rm_attribute_id();
			setState(220);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(217);
				match(T__3);
				setState(218);
				_la = _input.LA(1);
				if ( !(_la==INTEGER || _la==STRING) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(219);
				match(T__4);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class String_valueContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(odinParser.STRING, 0); }
		public String_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterString_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitString_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitString_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final String_valueContext string_value() throws RecognitionException {
		String_valueContext _localctx = new String_valueContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_string_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class String_list_valueContext extends ParserRuleContext {
		public List<String_valueContext> string_value() {
			return getRuleContexts(String_valueContext.class);
		}
		public String_valueContext string_value(int i) {
			return getRuleContext(String_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public String_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterString_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitString_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitString_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final String_list_valueContext string_list_value() throws RecognitionException {
		String_list_valueContext _localctx = new String_list_valueContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_string_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			string_value();
			setState(233);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(227); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(225);
					match(T__5);
					setState(226);
					string_value();
					}
					}
					setState(229); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(231);
				match(T__5);
				setState(232);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Integer_valueContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(odinParser.INTEGER, 0); }
		public Integer_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterInteger_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitInteger_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitInteger_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Integer_valueContext integer_value() throws RecognitionException {
		Integer_valueContext _localctx = new Integer_valueContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_integer_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236);
			_la = _input.LA(1);
			if (_la==T__7 || _la==T__8) {
				{
				setState(235);
				_la = _input.LA(1);
				if ( !(_la==T__7 || _la==T__8) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			setState(238);
			match(INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Integer_list_valueContext extends ParserRuleContext {
		public List<Integer_valueContext> integer_value() {
			return getRuleContexts(Integer_valueContext.class);
		}
		public Integer_valueContext integer_value(int i) {
			return getRuleContext(Integer_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Integer_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterInteger_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitInteger_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitInteger_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Integer_list_valueContext integer_list_value() throws RecognitionException {
		Integer_list_valueContext _localctx = new Integer_list_valueContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_integer_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			integer_value();
			setState(249);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(243); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(241);
					match(T__5);
					setState(242);
					integer_value();
					}
					}
					setState(245); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(247);
				match(T__5);
				setState(248);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Integer_interval_valueContext extends ParserRuleContext {
		public List<Integer_valueContext> integer_value() {
			return getRuleContexts(Integer_valueContext.class);
		}
		public Integer_valueContext integer_value(int i) {
			return getRuleContext(Integer_valueContext.class,i);
		}
		public TerminalNode SYM_INTERVAL_SEP() { return getToken(odinParser.SYM_INTERVAL_SEP, 0); }
		public TerminalNode SYM_GT() { return getToken(odinParser.SYM_GT, 0); }
		public TerminalNode SYM_LT() { return getToken(odinParser.SYM_LT, 0); }
		public RelopContext relop() {
			return getRuleContext(RelopContext.class,0);
		}
		public Integer_interval_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer_interval_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterInteger_interval_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitInteger_interval_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitInteger_interval_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Integer_interval_valueContext integer_interval_value() throws RecognitionException {
		Integer_interval_valueContext _localctx = new Integer_interval_valueContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_integer_interval_value);
		int _la;
		try {
			setState(270);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(251);
				match(T__9);
				setState(253);
				_la = _input.LA(1);
				if (_la==SYM_GT) {
					{
					setState(252);
					match(SYM_GT);
					}
				}

				setState(255);
				integer_value();
				setState(256);
				match(SYM_INTERVAL_SEP);
				setState(258);
				_la = _input.LA(1);
				if (_la==SYM_LT) {
					{
					setState(257);
					match(SYM_LT);
					}
				}

				setState(260);
				integer_value();
				setState(261);
				match(T__9);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(263);
				match(T__9);
				setState(265);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SYM_GT) | (1L << SYM_LT) | (1L << SYM_LE) | (1L << SYM_GE))) != 0)) {
					{
					setState(264);
					relop();
					}
				}

				setState(267);
				integer_value();
				setState(268);
				match(T__9);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Integer_interval_list_valueContext extends ParserRuleContext {
		public List<Integer_interval_valueContext> integer_interval_value() {
			return getRuleContexts(Integer_interval_valueContext.class);
		}
		public Integer_interval_valueContext integer_interval_value(int i) {
			return getRuleContext(Integer_interval_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Integer_interval_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer_interval_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterInteger_interval_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitInteger_interval_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitInteger_interval_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Integer_interval_list_valueContext integer_interval_list_value() throws RecognitionException {
		Integer_interval_list_valueContext _localctx = new Integer_interval_list_valueContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_integer_interval_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			integer_interval_value();
			setState(281);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(275); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(273);
					match(T__5);
					setState(274);
					integer_interval_value();
					}
					}
					setState(277); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(279);
				match(T__5);
				setState(280);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Real_valueContext extends ParserRuleContext {
		public TerminalNode REAL() { return getToken(odinParser.REAL, 0); }
		public Real_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_real_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterReal_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitReal_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitReal_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Real_valueContext real_value() throws RecognitionException {
		Real_valueContext _localctx = new Real_valueContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_real_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			_la = _input.LA(1);
			if (_la==T__7 || _la==T__8) {
				{
				setState(283);
				_la = _input.LA(1);
				if ( !(_la==T__7 || _la==T__8) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			setState(286);
			match(REAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Real_list_valueContext extends ParserRuleContext {
		public List<Real_valueContext> real_value() {
			return getRuleContexts(Real_valueContext.class);
		}
		public Real_valueContext real_value(int i) {
			return getRuleContext(Real_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Real_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_real_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterReal_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitReal_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitReal_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Real_list_valueContext real_list_value() throws RecognitionException {
		Real_list_valueContext _localctx = new Real_list_valueContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_real_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			real_value();
			setState(297);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(291); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(289);
					match(T__5);
					setState(290);
					real_value();
					}
					}
					setState(293); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(295);
				match(T__5);
				setState(296);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Real_interval_valueContext extends ParserRuleContext {
		public List<Real_valueContext> real_value() {
			return getRuleContexts(Real_valueContext.class);
		}
		public Real_valueContext real_value(int i) {
			return getRuleContext(Real_valueContext.class,i);
		}
		public TerminalNode SYM_INTERVAL_SEP() { return getToken(odinParser.SYM_INTERVAL_SEP, 0); }
		public TerminalNode SYM_GT() { return getToken(odinParser.SYM_GT, 0); }
		public TerminalNode SYM_LT() { return getToken(odinParser.SYM_LT, 0); }
		public RelopContext relop() {
			return getRuleContext(RelopContext.class,0);
		}
		public Real_interval_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_real_interval_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterReal_interval_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitReal_interval_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitReal_interval_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Real_interval_valueContext real_interval_value() throws RecognitionException {
		Real_interval_valueContext _localctx = new Real_interval_valueContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_real_interval_value);
		int _la;
		try {
			setState(318);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(299);
				match(T__9);
				setState(301);
				_la = _input.LA(1);
				if (_la==SYM_GT) {
					{
					setState(300);
					match(SYM_GT);
					}
				}

				setState(303);
				real_value();
				setState(304);
				match(SYM_INTERVAL_SEP);
				setState(306);
				_la = _input.LA(1);
				if (_la==SYM_LT) {
					{
					setState(305);
					match(SYM_LT);
					}
				}

				setState(308);
				real_value();
				setState(309);
				match(T__9);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(311);
				match(T__9);
				setState(313);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SYM_GT) | (1L << SYM_LT) | (1L << SYM_LE) | (1L << SYM_GE))) != 0)) {
					{
					setState(312);
					relop();
					}
				}

				setState(315);
				real_value();
				setState(316);
				match(T__9);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Real_interval_list_valueContext extends ParserRuleContext {
		public List<Real_interval_valueContext> real_interval_value() {
			return getRuleContexts(Real_interval_valueContext.class);
		}
		public Real_interval_valueContext real_interval_value(int i) {
			return getRuleContext(Real_interval_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Real_interval_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_real_interval_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterReal_interval_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitReal_interval_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitReal_interval_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Real_interval_list_valueContext real_interval_list_value() throws RecognitionException {
		Real_interval_list_valueContext _localctx = new Real_interval_list_valueContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_real_interval_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			real_interval_value();
			setState(329);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				{
				setState(323); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(321);
					match(T__5);
					setState(322);
					real_interval_value();
					}
					}
					setState(325); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(327);
				match(T__5);
				setState(328);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Boolean_valueContext extends ParserRuleContext {
		public TerminalNode SYM_TRUE() { return getToken(odinParser.SYM_TRUE, 0); }
		public TerminalNode SYM_FALSE() { return getToken(odinParser.SYM_FALSE, 0); }
		public Boolean_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterBoolean_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitBoolean_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitBoolean_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Boolean_valueContext boolean_value() throws RecognitionException {
		Boolean_valueContext _localctx = new Boolean_valueContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_boolean_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			_la = _input.LA(1);
			if ( !(_la==SYM_TRUE || _la==SYM_FALSE) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Boolean_list_valueContext extends ParserRuleContext {
		public List<Boolean_valueContext> boolean_value() {
			return getRuleContexts(Boolean_valueContext.class);
		}
		public Boolean_valueContext boolean_value(int i) {
			return getRuleContext(Boolean_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Boolean_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterBoolean_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitBoolean_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitBoolean_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Boolean_list_valueContext boolean_list_value() throws RecognitionException {
		Boolean_list_valueContext _localctx = new Boolean_list_valueContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_boolean_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			boolean_value();
			setState(342);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(336); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(334);
					match(T__5);
					setState(335);
					boolean_value();
					}
					}
					setState(338); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(340);
				match(T__5);
				setState(341);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Character_valueContext extends ParserRuleContext {
		public TerminalNode CHARACTER() { return getToken(odinParser.CHARACTER, 0); }
		public Character_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_character_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterCharacter_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitCharacter_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitCharacter_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Character_valueContext character_value() throws RecognitionException {
		Character_valueContext _localctx = new Character_valueContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_character_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344);
			match(CHARACTER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Character_list_valueContext extends ParserRuleContext {
		public List<Character_valueContext> character_value() {
			return getRuleContexts(Character_valueContext.class);
		}
		public Character_valueContext character_value(int i) {
			return getRuleContext(Character_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Character_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_character_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterCharacter_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitCharacter_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitCharacter_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Character_list_valueContext character_list_value() throws RecognitionException {
		Character_list_valueContext _localctx = new Character_list_valueContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_character_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(346);
			character_value();
			setState(355);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				{
				setState(349); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(347);
					match(T__5);
					setState(348);
					character_value();
					}
					}
					setState(351); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(353);
				match(T__5);
				setState(354);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Date_valueContext extends ParserRuleContext {
		public TerminalNode ISO8601_DATE() { return getToken(odinParser.ISO8601_DATE, 0); }
		public Date_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDate_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDate_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDate_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Date_valueContext date_value() throws RecognitionException {
		Date_valueContext _localctx = new Date_valueContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_date_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(357);
			match(ISO8601_DATE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Date_list_valueContext extends ParserRuleContext {
		public List<Date_valueContext> date_value() {
			return getRuleContexts(Date_valueContext.class);
		}
		public Date_valueContext date_value(int i) {
			return getRuleContext(Date_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Date_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDate_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDate_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDate_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Date_list_valueContext date_list_value() throws RecognitionException {
		Date_list_valueContext _localctx = new Date_list_valueContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_date_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(359);
			date_value();
			setState(368);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				{
				setState(362); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(360);
					match(T__5);
					setState(361);
					date_value();
					}
					}
					setState(364); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(366);
				match(T__5);
				setState(367);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Date_interval_valueContext extends ParserRuleContext {
		public List<Date_valueContext> date_value() {
			return getRuleContexts(Date_valueContext.class);
		}
		public Date_valueContext date_value(int i) {
			return getRuleContext(Date_valueContext.class,i);
		}
		public TerminalNode SYM_INTERVAL_SEP() { return getToken(odinParser.SYM_INTERVAL_SEP, 0); }
		public TerminalNode SYM_GT() { return getToken(odinParser.SYM_GT, 0); }
		public TerminalNode SYM_LT() { return getToken(odinParser.SYM_LT, 0); }
		public RelopContext relop() {
			return getRuleContext(RelopContext.class,0);
		}
		public Date_interval_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date_interval_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDate_interval_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDate_interval_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDate_interval_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Date_interval_valueContext date_interval_value() throws RecognitionException {
		Date_interval_valueContext _localctx = new Date_interval_valueContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_date_interval_value);
		int _la;
		try {
			setState(389);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(370);
				match(T__9);
				setState(372);
				_la = _input.LA(1);
				if (_la==SYM_GT) {
					{
					setState(371);
					match(SYM_GT);
					}
				}

				setState(374);
				date_value();
				setState(375);
				match(SYM_INTERVAL_SEP);
				setState(377);
				_la = _input.LA(1);
				if (_la==SYM_LT) {
					{
					setState(376);
					match(SYM_LT);
					}
				}

				setState(379);
				date_value();
				setState(380);
				match(T__9);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(382);
				match(T__9);
				setState(384);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SYM_GT) | (1L << SYM_LT) | (1L << SYM_LE) | (1L << SYM_GE))) != 0)) {
					{
					setState(383);
					relop();
					}
				}

				setState(386);
				date_value();
				setState(387);
				match(T__9);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Date_interval_list_valueContext extends ParserRuleContext {
		public List<Date_interval_valueContext> date_interval_value() {
			return getRuleContexts(Date_interval_valueContext.class);
		}
		public Date_interval_valueContext date_interval_value(int i) {
			return getRuleContext(Date_interval_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Date_interval_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date_interval_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDate_interval_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDate_interval_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDate_interval_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Date_interval_list_valueContext date_interval_list_value() throws RecognitionException {
		Date_interval_list_valueContext _localctx = new Date_interval_list_valueContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_date_interval_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(391);
			date_interval_value();
			setState(400);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				{
				setState(394); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(392);
					match(T__5);
					setState(393);
					date_interval_value();
					}
					}
					setState(396); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(398);
				match(T__5);
				setState(399);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Time_valueContext extends ParserRuleContext {
		public TerminalNode ISO8601_TIME() { return getToken(odinParser.ISO8601_TIME, 0); }
		public Time_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_time_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterTime_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitTime_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitTime_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Time_valueContext time_value() throws RecognitionException {
		Time_valueContext _localctx = new Time_valueContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_time_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			match(ISO8601_TIME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Time_list_valueContext extends ParserRuleContext {
		public List<Time_valueContext> time_value() {
			return getRuleContexts(Time_valueContext.class);
		}
		public Time_valueContext time_value(int i) {
			return getRuleContext(Time_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Time_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_time_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterTime_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitTime_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitTime_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Time_list_valueContext time_list_value() throws RecognitionException {
		Time_list_valueContext _localctx = new Time_list_valueContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_time_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(404);
			time_value();
			setState(413);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				{
				setState(407); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(405);
					match(T__5);
					setState(406);
					time_value();
					}
					}
					setState(409); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(411);
				match(T__5);
				setState(412);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Time_interval_valueContext extends ParserRuleContext {
		public List<Time_valueContext> time_value() {
			return getRuleContexts(Time_valueContext.class);
		}
		public Time_valueContext time_value(int i) {
			return getRuleContext(Time_valueContext.class,i);
		}
		public TerminalNode SYM_INTERVAL_SEP() { return getToken(odinParser.SYM_INTERVAL_SEP, 0); }
		public TerminalNode SYM_GT() { return getToken(odinParser.SYM_GT, 0); }
		public TerminalNode SYM_LT() { return getToken(odinParser.SYM_LT, 0); }
		public RelopContext relop() {
			return getRuleContext(RelopContext.class,0);
		}
		public Time_interval_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_time_interval_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterTime_interval_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitTime_interval_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitTime_interval_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Time_interval_valueContext time_interval_value() throws RecognitionException {
		Time_interval_valueContext _localctx = new Time_interval_valueContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_time_interval_value);
		int _la;
		try {
			setState(434);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(415);
				match(T__9);
				setState(417);
				_la = _input.LA(1);
				if (_la==SYM_GT) {
					{
					setState(416);
					match(SYM_GT);
					}
				}

				setState(419);
				time_value();
				setState(420);
				match(SYM_INTERVAL_SEP);
				setState(422);
				_la = _input.LA(1);
				if (_la==SYM_LT) {
					{
					setState(421);
					match(SYM_LT);
					}
				}

				setState(424);
				time_value();
				setState(425);
				match(T__9);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(427);
				match(T__9);
				setState(429);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SYM_GT) | (1L << SYM_LT) | (1L << SYM_LE) | (1L << SYM_GE))) != 0)) {
					{
					setState(428);
					relop();
					}
				}

				setState(431);
				time_value();
				setState(432);
				match(T__9);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Time_interval_list_valueContext extends ParserRuleContext {
		public List<Time_interval_valueContext> time_interval_value() {
			return getRuleContexts(Time_interval_valueContext.class);
		}
		public Time_interval_valueContext time_interval_value(int i) {
			return getRuleContext(Time_interval_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Time_interval_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_time_interval_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterTime_interval_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitTime_interval_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitTime_interval_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Time_interval_list_valueContext time_interval_list_value() throws RecognitionException {
		Time_interval_list_valueContext _localctx = new Time_interval_list_valueContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_time_interval_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(436);
			time_interval_value();
			setState(445);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				{
				setState(439); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(437);
					match(T__5);
					setState(438);
					time_interval_value();
					}
					}
					setState(441); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(443);
				match(T__5);
				setState(444);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Date_time_valueContext extends ParserRuleContext {
		public TerminalNode ISO8601_DATE_TIME() { return getToken(odinParser.ISO8601_DATE_TIME, 0); }
		public Date_time_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date_time_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDate_time_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDate_time_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDate_time_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Date_time_valueContext date_time_value() throws RecognitionException {
		Date_time_valueContext _localctx = new Date_time_valueContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_date_time_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(447);
			match(ISO8601_DATE_TIME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Date_time_list_valueContext extends ParserRuleContext {
		public List<Date_time_valueContext> date_time_value() {
			return getRuleContexts(Date_time_valueContext.class);
		}
		public Date_time_valueContext date_time_value(int i) {
			return getRuleContext(Date_time_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Date_time_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date_time_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDate_time_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDate_time_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDate_time_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Date_time_list_valueContext date_time_list_value() throws RecognitionException {
		Date_time_list_valueContext _localctx = new Date_time_list_valueContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_date_time_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(449);
			date_time_value();
			setState(458);
			switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
			case 1:
				{
				setState(452); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(450);
					match(T__5);
					setState(451);
					date_time_value();
					}
					}
					setState(454); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(456);
				match(T__5);
				setState(457);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Date_time_interval_valueContext extends ParserRuleContext {
		public List<Date_time_valueContext> date_time_value() {
			return getRuleContexts(Date_time_valueContext.class);
		}
		public Date_time_valueContext date_time_value(int i) {
			return getRuleContext(Date_time_valueContext.class,i);
		}
		public TerminalNode SYM_INTERVAL_SEP() { return getToken(odinParser.SYM_INTERVAL_SEP, 0); }
		public TerminalNode SYM_GT() { return getToken(odinParser.SYM_GT, 0); }
		public TerminalNode SYM_LT() { return getToken(odinParser.SYM_LT, 0); }
		public RelopContext relop() {
			return getRuleContext(RelopContext.class,0);
		}
		public Date_time_interval_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date_time_interval_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDate_time_interval_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDate_time_interval_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDate_time_interval_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Date_time_interval_valueContext date_time_interval_value() throws RecognitionException {
		Date_time_interval_valueContext _localctx = new Date_time_interval_valueContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_date_time_interval_value);
		int _la;
		try {
			setState(479);
			switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(460);
				match(T__9);
				setState(462);
				_la = _input.LA(1);
				if (_la==SYM_GT) {
					{
					setState(461);
					match(SYM_GT);
					}
				}

				setState(464);
				date_time_value();
				setState(465);
				match(SYM_INTERVAL_SEP);
				setState(467);
				_la = _input.LA(1);
				if (_la==SYM_LT) {
					{
					setState(466);
					match(SYM_LT);
					}
				}

				setState(469);
				date_time_value();
				setState(470);
				match(T__9);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(472);
				match(T__9);
				setState(474);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SYM_GT) | (1L << SYM_LT) | (1L << SYM_LE) | (1L << SYM_GE))) != 0)) {
					{
					setState(473);
					relop();
					}
				}

				setState(476);
				date_time_value();
				setState(477);
				match(T__9);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Date_time_interval_list_valueContext extends ParserRuleContext {
		public List<Date_time_interval_valueContext> date_time_interval_value() {
			return getRuleContexts(Date_time_interval_valueContext.class);
		}
		public Date_time_interval_valueContext date_time_interval_value(int i) {
			return getRuleContext(Date_time_interval_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Date_time_interval_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date_time_interval_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDate_time_interval_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDate_time_interval_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDate_time_interval_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Date_time_interval_list_valueContext date_time_interval_list_value() throws RecognitionException {
		Date_time_interval_list_valueContext _localctx = new Date_time_interval_list_valueContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_date_time_interval_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(481);
			date_time_interval_value();
			setState(490);
			switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
			case 1:
				{
				setState(484); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(482);
					match(T__5);
					setState(483);
					date_time_interval_value();
					}
					}
					setState(486); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(488);
				match(T__5);
				setState(489);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Duration_valueContext extends ParserRuleContext {
		public TerminalNode ISO8601_DURATION() { return getToken(odinParser.ISO8601_DURATION, 0); }
		public Duration_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_duration_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDuration_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDuration_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDuration_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Duration_valueContext duration_value() throws RecognitionException {
		Duration_valueContext _localctx = new Duration_valueContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_duration_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(492);
			match(ISO8601_DURATION);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Duration_list_valueContext extends ParserRuleContext {
		public List<Duration_valueContext> duration_value() {
			return getRuleContexts(Duration_valueContext.class);
		}
		public Duration_valueContext duration_value(int i) {
			return getRuleContext(Duration_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Duration_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_duration_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDuration_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDuration_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDuration_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Duration_list_valueContext duration_list_value() throws RecognitionException {
		Duration_list_valueContext _localctx = new Duration_list_valueContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_duration_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(494);
			duration_value();
			setState(503);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				{
				setState(497); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(495);
					match(T__5);
					setState(496);
					duration_value();
					}
					}
					setState(499); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(501);
				match(T__5);
				setState(502);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Duration_interval_valueContext extends ParserRuleContext {
		public List<Duration_valueContext> duration_value() {
			return getRuleContexts(Duration_valueContext.class);
		}
		public Duration_valueContext duration_value(int i) {
			return getRuleContext(Duration_valueContext.class,i);
		}
		public TerminalNode SYM_INTERVAL_SEP() { return getToken(odinParser.SYM_INTERVAL_SEP, 0); }
		public TerminalNode SYM_GT() { return getToken(odinParser.SYM_GT, 0); }
		public TerminalNode SYM_LT() { return getToken(odinParser.SYM_LT, 0); }
		public RelopContext relop() {
			return getRuleContext(RelopContext.class,0);
		}
		public Duration_interval_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_duration_interval_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDuration_interval_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDuration_interval_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDuration_interval_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Duration_interval_valueContext duration_interval_value() throws RecognitionException {
		Duration_interval_valueContext _localctx = new Duration_interval_valueContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_duration_interval_value);
		int _la;
		try {
			setState(524);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(505);
				match(T__9);
				setState(507);
				_la = _input.LA(1);
				if (_la==SYM_GT) {
					{
					setState(506);
					match(SYM_GT);
					}
				}

				setState(509);
				duration_value();
				setState(510);
				match(SYM_INTERVAL_SEP);
				setState(512);
				_la = _input.LA(1);
				if (_la==SYM_LT) {
					{
					setState(511);
					match(SYM_LT);
					}
				}

				setState(514);
				duration_value();
				setState(515);
				match(T__9);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(517);
				match(T__9);
				setState(519);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SYM_GT) | (1L << SYM_LT) | (1L << SYM_LE) | (1L << SYM_GE))) != 0)) {
					{
					setState(518);
					relop();
					}
				}

				setState(521);
				duration_value();
				setState(522);
				match(T__9);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Duration_interval_list_valueContext extends ParserRuleContext {
		public List<Duration_interval_valueContext> duration_interval_value() {
			return getRuleContexts(Duration_interval_valueContext.class);
		}
		public Duration_interval_valueContext duration_interval_value(int i) {
			return getRuleContext(Duration_interval_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Duration_interval_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_duration_interval_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterDuration_interval_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitDuration_interval_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitDuration_interval_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Duration_interval_list_valueContext duration_interval_list_value() throws RecognitionException {
		Duration_interval_list_valueContext _localctx = new Duration_interval_list_valueContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_duration_interval_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(526);
			duration_interval_value();
			setState(535);
			switch ( getInterpreter().adaptivePredict(_input,72,_ctx) ) {
			case 1:
				{
				setState(529); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(527);
					match(T__5);
					setState(528);
					duration_interval_value();
					}
					}
					setState(531); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(533);
				match(T__5);
				setState(534);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Term_code_valueContext extends ParserRuleContext {
		public TerminalNode TERM_CODE_REF() { return getToken(odinParser.TERM_CODE_REF, 0); }
		public Term_code_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term_code_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterTerm_code_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitTerm_code_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitTerm_code_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Term_code_valueContext term_code_value() throws RecognitionException {
		Term_code_valueContext _localctx = new Term_code_valueContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_term_code_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(537);
			match(TERM_CODE_REF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Term_code_list_valueContext extends ParserRuleContext {
		public List<Term_code_valueContext> term_code_value() {
			return getRuleContexts(Term_code_valueContext.class);
		}
		public Term_code_valueContext term_code_value(int i) {
			return getRuleContext(Term_code_valueContext.class,i);
		}
		public TerminalNode SYM_LIST_CONTINUE() { return getToken(odinParser.SYM_LIST_CONTINUE, 0); }
		public Term_code_list_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term_code_list_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterTerm_code_list_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitTerm_code_list_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitTerm_code_list_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Term_code_list_valueContext term_code_list_value() throws RecognitionException {
		Term_code_list_valueContext _localctx = new Term_code_list_valueContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_term_code_list_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(539);
			term_code_value();
			setState(548);
			switch ( getInterpreter().adaptivePredict(_input,74,_ctx) ) {
			case 1:
				{
				setState(542); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(540);
					match(T__5);
					setState(541);
					term_code_value();
					}
					}
					setState(544); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 );
				}
				break;
			case 2:
				{
				setState(546);
				match(T__5);
				setState(547);
				match(SYM_LIST_CONTINUE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Uri_valueContext extends ParserRuleContext {
		public TerminalNode URI() { return getToken(odinParser.URI, 0); }
		public Uri_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_uri_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterUri_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitUri_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitUri_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Uri_valueContext uri_value() throws RecognitionException {
		Uri_valueContext _localctx = new Uri_valueContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_uri_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(550);
			match(URI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelopContext extends ParserRuleContext {
		public TerminalNode SYM_GT() { return getToken(odinParser.SYM_GT, 0); }
		public TerminalNode SYM_LT() { return getToken(odinParser.SYM_LT, 0); }
		public TerminalNode SYM_LE() { return getToken(odinParser.SYM_LE, 0); }
		public TerminalNode SYM_GE() { return getToken(odinParser.SYM_GE, 0); }
		public RelopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterRelop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitRelop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitRelop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelopContext relop() throws RecognitionException {
		RelopContext _localctx = new RelopContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_relop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(552);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SYM_GT) | (1L << SYM_LT) | (1L << SYM_LE) | (1L << SYM_GE))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rm_type_idContext extends ParserRuleContext {
		public TerminalNode ALPHA_UC_ID() { return getToken(odinParser.ALPHA_UC_ID, 0); }
		public List<Rm_type_idContext> rm_type_id() {
			return getRuleContexts(Rm_type_idContext.class);
		}
		public Rm_type_idContext rm_type_id(int i) {
			return getRuleContext(Rm_type_idContext.class,i);
		}
		public Rm_type_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rm_type_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterRm_type_id(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitRm_type_id(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitRm_type_id(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Rm_type_idContext rm_type_id() throws RecognitionException {
		Rm_type_idContext _localctx = new Rm_type_idContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_rm_type_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(554);
			match(ALPHA_UC_ID);
			setState(566);
			_la = _input.LA(1);
			if (_la==SYM_LT) {
				{
				setState(555);
				match(SYM_LT);
				setState(556);
				rm_type_id();
				setState(561);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__5) {
					{
					{
					setState(557);
					match(T__5);
					setState(558);
					rm_type_id();
					}
					}
					setState(563);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(564);
				match(SYM_GT);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rm_attribute_idContext extends ParserRuleContext {
		public TerminalNode ALPHA_LC_ID() { return getToken(odinParser.ALPHA_LC_ID, 0); }
		public Rm_attribute_idContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rm_attribute_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterRm_attribute_id(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitRm_attribute_id(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitRm_attribute_id(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Rm_attribute_idContext rm_attribute_id() throws RecognitionException {
		Rm_attribute_idContext _localctx = new Rm_attribute_idContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_rm_attribute_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(568);
			match(ALPHA_LC_ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode ALPHA_UC_ID() { return getToken(odinParser.ALPHA_UC_ID, 0); }
		public TerminalNode ALPHA_LC_ID() { return getToken(odinParser.ALPHA_LC_ID, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(570);
			_la = _input.LA(1);
			if ( !(_la==ALPHA_UC_ID || _la==ALPHA_LC_ID) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Archetype_refContext extends ParserRuleContext {
		public TerminalNode ARCHETYPE_HRID() { return getToken(odinParser.ARCHETYPE_HRID, 0); }
		public TerminalNode ARCHETYPE_REF() { return getToken(odinParser.ARCHETYPE_REF, 0); }
		public Archetype_refContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_archetype_ref; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).enterArchetype_ref(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof odinListener ) ((odinListener)listener).exitArchetype_ref(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof odinVisitor ) return ((odinVisitor<? extends T>)visitor).visitArchetype_ref(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Archetype_refContext archetype_ref() throws RecognitionException {
		Archetype_refContext _localctx = new Archetype_refContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_archetype_ref);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(572);
			_la = _input.LA(1);
			if ( !(_la==ARCHETYPE_HRID || _la==ARCHETYPE_REF) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3*\u0241\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\3\2\3\2\5\2o\n\2\3\3\3\3\5\3s\n\3\6\3u\n\3\r\3"+
		"\16\3v\3\4\3\4\3\4\3\4\3\5\3\5\5\5\177\n\5\3\6\3\6\3\6\3\6\5\6\u0085\n"+
		"\6\3\6\3\6\3\6\5\6\u008a\n\6\3\6\7\6\u008d\n\6\f\6\16\6\u0090\13\6\5\6"+
		"\u0092\n\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\5\b\u009f\n\b\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00ac\n\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u00b8\n\n\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\5\13\u00c0\n\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\6\r\u00c9\n\r\r\r\16\r\u00ca"+
		"\3\r\5\r\u00ce\n\r\3\16\3\16\6\16\u00d2\n\16\r\16\16\16\u00d3\5\16\u00d6"+
		"\n\16\3\17\3\17\3\17\3\20\3\20\3\20\3\20\5\20\u00df\n\20\3\21\3\21\3\22"+
		"\3\22\3\22\6\22\u00e6\n\22\r\22\16\22\u00e7\3\22\3\22\5\22\u00ec\n\22"+
		"\3\23\5\23\u00ef\n\23\3\23\3\23\3\24\3\24\3\24\6\24\u00f6\n\24\r\24\16"+
		"\24\u00f7\3\24\3\24\5\24\u00fc\n\24\3\25\3\25\5\25\u0100\n\25\3\25\3\25"+
		"\3\25\5\25\u0105\n\25\3\25\3\25\3\25\3\25\3\25\5\25\u010c\n\25\3\25\3"+
		"\25\3\25\5\25\u0111\n\25\3\26\3\26\3\26\6\26\u0116\n\26\r\26\16\26\u0117"+
		"\3\26\3\26\5\26\u011c\n\26\3\27\5\27\u011f\n\27\3\27\3\27\3\30\3\30\3"+
		"\30\6\30\u0126\n\30\r\30\16\30\u0127\3\30\3\30\5\30\u012c\n\30\3\31\3"+
		"\31\5\31\u0130\n\31\3\31\3\31\3\31\5\31\u0135\n\31\3\31\3\31\3\31\3\31"+
		"\3\31\5\31\u013c\n\31\3\31\3\31\3\31\5\31\u0141\n\31\3\32\3\32\3\32\6"+
		"\32\u0146\n\32\r\32\16\32\u0147\3\32\3\32\5\32\u014c\n\32\3\33\3\33\3"+
		"\34\3\34\3\34\6\34\u0153\n\34\r\34\16\34\u0154\3\34\3\34\5\34\u0159\n"+
		"\34\3\35\3\35\3\36\3\36\3\36\6\36\u0160\n\36\r\36\16\36\u0161\3\36\3\36"+
		"\5\36\u0166\n\36\3\37\3\37\3 \3 \3 \6 \u016d\n \r \16 \u016e\3 \3 \5 "+
		"\u0173\n \3!\3!\5!\u0177\n!\3!\3!\3!\5!\u017c\n!\3!\3!\3!\3!\3!\5!\u0183"+
		"\n!\3!\3!\3!\5!\u0188\n!\3\"\3\"\3\"\6\"\u018d\n\"\r\"\16\"\u018e\3\""+
		"\3\"\5\"\u0193\n\"\3#\3#\3$\3$\3$\6$\u019a\n$\r$\16$\u019b\3$\3$\5$\u01a0"+
		"\n$\3%\3%\5%\u01a4\n%\3%\3%\3%\5%\u01a9\n%\3%\3%\3%\3%\3%\5%\u01b0\n%"+
		"\3%\3%\3%\5%\u01b5\n%\3&\3&\3&\6&\u01ba\n&\r&\16&\u01bb\3&\3&\5&\u01c0"+
		"\n&\3\'\3\'\3(\3(\3(\6(\u01c7\n(\r(\16(\u01c8\3(\3(\5(\u01cd\n(\3)\3)"+
		"\5)\u01d1\n)\3)\3)\3)\5)\u01d6\n)\3)\3)\3)\3)\3)\5)\u01dd\n)\3)\3)\3)"+
		"\5)\u01e2\n)\3*\3*\3*\6*\u01e7\n*\r*\16*\u01e8\3*\3*\5*\u01ed\n*\3+\3"+
		"+\3,\3,\3,\6,\u01f4\n,\r,\16,\u01f5\3,\3,\5,\u01fa\n,\3-\3-\5-\u01fe\n"+
		"-\3-\3-\3-\5-\u0203\n-\3-\3-\3-\3-\3-\5-\u020a\n-\3-\3-\3-\5-\u020f\n"+
		"-\3.\3.\3.\6.\u0214\n.\r.\16.\u0215\3.\3.\5.\u021a\n.\3/\3/\3\60\3\60"+
		"\3\60\6\60\u0221\n\60\r\60\16\60\u0222\3\60\3\60\5\60\u0227\n\60\3\61"+
		"\3\61\3\62\3\62\3\63\3\63\3\63\3\63\3\63\7\63\u0232\n\63\f\63\16\63\u0235"+
		"\13\63\3\63\3\63\5\63\u0239\n\63\3\64\3\64\3\65\3\65\3\66\3\66\3\66\2"+
		"\2\67\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>"+
		"@BDFHJLNPRTVXZ\\^`bdfhj\2\b\4\2\'\'))\3\2\n\13\3\2\35\36\3\2\r\20\3\2"+
		"%&\3\2\37 \u0270\2n\3\2\2\2\4t\3\2\2\2\6x\3\2\2\2\b~\3\2\2\2\n\u0084\3"+
		"\2\2\2\f\u0095\3\2\2\2\16\u009e\3\2\2\2\20\u00ab\3\2\2\2\22\u00b7\3\2"+
		"\2\2\24\u00bf\3\2\2\2\26\u00c1\3\2\2\2\30\u00c5\3\2\2\2\32\u00d5\3\2\2"+
		"\2\34\u00d7\3\2\2\2\36\u00da\3\2\2\2 \u00e0\3\2\2\2\"\u00e2\3\2\2\2$\u00ee"+
		"\3\2\2\2&\u00f2\3\2\2\2(\u0110\3\2\2\2*\u0112\3\2\2\2,\u011e\3\2\2\2."+
		"\u0122\3\2\2\2\60\u0140\3\2\2\2\62\u0142\3\2\2\2\64\u014d\3\2\2\2\66\u014f"+
		"\3\2\2\28\u015a\3\2\2\2:\u015c\3\2\2\2<\u0167\3\2\2\2>\u0169\3\2\2\2@"+
		"\u0187\3\2\2\2B\u0189\3\2\2\2D\u0194\3\2\2\2F\u0196\3\2\2\2H\u01b4\3\2"+
		"\2\2J\u01b6\3\2\2\2L\u01c1\3\2\2\2N\u01c3\3\2\2\2P\u01e1\3\2\2\2R\u01e3"+
		"\3\2\2\2T\u01ee\3\2\2\2V\u01f0\3\2\2\2X\u020e\3\2\2\2Z\u0210\3\2\2\2\\"+
		"\u021b\3\2\2\2^\u021d\3\2\2\2`\u0228\3\2\2\2b\u022a\3\2\2\2d\u022c\3\2"+
		"\2\2f\u023a\3\2\2\2h\u023c\3\2\2\2j\u023e\3\2\2\2lo\5\4\3\2mo\5\n\6\2"+
		"nl\3\2\2\2nm\3\2\2\2o\3\3\2\2\2pr\5\6\4\2qs\7\3\2\2rq\3\2\2\2rs\3\2\2"+
		"\2su\3\2\2\2tp\3\2\2\2uv\3\2\2\2vt\3\2\2\2vw\3\2\2\2w\5\3\2\2\2xy\5f\64"+
		"\2yz\7\22\2\2z{\5\b\5\2{\7\3\2\2\2|\177\5\n\6\2}\177\5\26\f\2~|\3\2\2"+
		"\2~}\3\2\2\2\177\t\3\2\2\2\u0080\u0081\7\4\2\2\u0081\u0082\5d\63\2\u0082"+
		"\u0083\7\5\2\2\u0083\u0085\3\2\2\2\u0084\u0080\3\2\2\2\u0084\u0085\3\2"+
		"\2\2\u0085\u0086\3\2\2\2\u0086\u0091\7\16\2\2\u0087\u0092\5\16\b\2\u0088"+
		"\u008a\5\4\3\2\u0089\u0088\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u0092\3\2"+
		"\2\2\u008b\u008d\5\f\7\2\u008c\u008b\3\2\2\2\u008d\u0090\3\2\2\2\u008e"+
		"\u008c\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3\2"+
		"\2\2\u0091\u0087\3\2\2\2\u0091\u0089\3\2\2\2\u0091\u008e\3\2\2\2\u0092"+
		"\u0093\3\2\2\2\u0093\u0094\7\r\2\2\u0094\13\3\2\2\2\u0095\u0096\7\6\2"+
		"\2\u0096\u0097\5\20\t\2\u0097\u0098\7\7\2\2\u0098\u0099\7\22\2\2\u0099"+
		"\u009a\5\b\5\2\u009a\r\3\2\2\2\u009b\u009f\5\20\t\2\u009c\u009f\5\22\n"+
		"\2\u009d\u009f\5\24\13\2\u009e\u009b\3\2\2\2\u009e\u009c\3\2\2\2\u009e"+
		"\u009d\3\2\2\2\u009f\17\3\2\2\2\u00a0\u00ac\5 \21\2\u00a1\u00ac\5$\23"+
		"\2\u00a2\u00ac\5,\27\2\u00a3\u00ac\5\64\33\2\u00a4\u00ac\58\35\2\u00a5"+
		"\u00ac\5\\/\2\u00a6\u00ac\5<\37\2\u00a7\u00ac\5D#\2\u00a8\u00ac\5L\'\2"+
		"\u00a9\u00ac\5T+\2\u00aa\u00ac\5`\61\2\u00ab\u00a0\3\2\2\2\u00ab\u00a1"+
		"\3\2\2\2\u00ab\u00a2\3\2\2\2\u00ab\u00a3\3\2\2\2\u00ab\u00a4\3\2\2\2\u00ab"+
		"\u00a5\3\2\2\2\u00ab\u00a6\3\2\2\2\u00ab\u00a7\3\2\2\2\u00ab\u00a8\3\2"+
		"\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00aa\3\2\2\2\u00ac\21\3\2\2\2\u00ad\u00b8"+
		"\5\"\22\2\u00ae\u00b8\5&\24\2\u00af\u00b8\5.\30\2\u00b0\u00b8\5\66\34"+
		"\2\u00b1\u00b8\5:\36\2\u00b2\u00b8\5^\60\2\u00b3\u00b8\5> \2\u00b4\u00b8"+
		"\5F$\2\u00b5\u00b8\5N(\2\u00b6\u00b8\5V,\2\u00b7\u00ad\3\2\2\2\u00b7\u00ae"+
		"\3\2\2\2\u00b7\u00af\3\2\2\2\u00b7\u00b0\3\2\2\2\u00b7\u00b1\3\2\2\2\u00b7"+
		"\u00b2\3\2\2\2\u00b7\u00b3\3\2\2\2\u00b7\u00b4\3\2\2\2\u00b7\u00b5\3\2"+
		"\2\2\u00b7\u00b6\3\2\2\2\u00b8\23\3\2\2\2\u00b9\u00c0\5(\25\2\u00ba\u00c0"+
		"\5\60\31\2\u00bb\u00c0\5@!\2\u00bc\u00c0\5H%\2\u00bd\u00c0\5P)\2\u00be"+
		"\u00c0\5X-\2\u00bf\u00b9\3\2\2\2\u00bf\u00ba\3\2\2\2\u00bf\u00bb\3\2\2"+
		"\2\u00bf\u00bc\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00be\3\2\2\2\u00c0\25"+
		"\3\2\2\2\u00c1\u00c2\7\16\2\2\u00c2\u00c3\5\30\r\2\u00c3\u00c4\7\r\2\2"+
		"\u00c4\27\3\2\2\2\u00c5\u00cd\5\32\16\2\u00c6\u00c7\7\b\2\2\u00c7\u00c9"+
		"\5\32\16\2\u00c8\u00c6\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00c8\3\2\2\2"+
		"\u00ca\u00cb\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00ce\7\23\2\2\u00cd\u00c8"+
		"\3\2\2\2\u00cd\u00cc\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\31\3\2\2\2\u00cf"+
		"\u00d6\7\t\2\2\u00d0\u00d2\5\34\17\2\u00d1\u00d0\3\2\2\2\u00d2\u00d3\3"+
		"\2\2\2\u00d3\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d6\3\2\2\2\u00d5"+
		"\u00cf\3\2\2\2\u00d5\u00d1\3\2\2\2\u00d6\33\3\2\2\2\u00d7\u00d8\7\t\2"+
		"\2\u00d8\u00d9\5\36\20\2\u00d9\35\3\2\2\2\u00da\u00de\5f\64\2\u00db\u00dc"+
		"\7\6\2\2\u00dc\u00dd\t\2\2\2\u00dd\u00df\7\7\2\2\u00de\u00db\3\2\2\2\u00de"+
		"\u00df\3\2\2\2\u00df\37\3\2\2\2\u00e0\u00e1\7)\2\2\u00e1!\3\2\2\2\u00e2"+
		"\u00eb\5 \21\2\u00e3\u00e4\7\b\2\2\u00e4\u00e6\5 \21\2\u00e5\u00e3\3\2"+
		"\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8"+
		"\u00ec\3\2\2\2\u00e9\u00ea\7\b\2\2\u00ea\u00ec\7\23\2\2\u00eb\u00e5\3"+
		"\2\2\2\u00eb\u00e9\3\2\2\2\u00ec#\3\2\2\2\u00ed\u00ef\t\3\2\2\u00ee\u00ed"+
		"\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f1\7\'\2\2\u00f1"+
		"%\3\2\2\2\u00f2\u00fb\5$\23\2\u00f3\u00f4\7\b\2\2\u00f4\u00f6\5$\23\2"+
		"\u00f5\u00f3\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8"+
		"\3\2\2\2\u00f8\u00fc\3\2\2\2\u00f9\u00fa\7\b\2\2\u00fa\u00fc\7\23\2\2"+
		"\u00fb\u00f5\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fc\'\3\2\2\2\u00fd\u00ff\7"+
		"\f\2\2\u00fe\u0100\7\r\2\2\u00ff\u00fe\3\2\2\2\u00ff\u0100\3\2\2\2\u0100"+
		"\u0101\3\2\2\2\u0101\u0102\5$\23\2\u0102\u0104\7\24\2\2\u0103\u0105\7"+
		"\16\2\2\u0104\u0103\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0106\3\2\2\2\u0106"+
		"\u0107\5$\23\2\u0107\u0108\7\f\2\2\u0108\u0111\3\2\2\2\u0109\u010b\7\f"+
		"\2\2\u010a\u010c\5b\62\2\u010b\u010a\3\2\2\2\u010b\u010c\3\2\2\2\u010c"+
		"\u010d\3\2\2\2\u010d\u010e\5$\23\2\u010e\u010f\7\f\2\2\u010f\u0111\3\2"+
		"\2\2\u0110\u00fd\3\2\2\2\u0110\u0109\3\2\2\2\u0111)\3\2\2\2\u0112\u011b"+
		"\5(\25\2\u0113\u0114\7\b\2\2\u0114\u0116\5(\25\2\u0115\u0113\3\2\2\2\u0116"+
		"\u0117\3\2\2\2\u0117\u0115\3\2\2\2\u0117\u0118\3\2\2\2\u0118\u011c\3\2"+
		"\2\2\u0119\u011a\7\b\2\2\u011a\u011c\7\23\2\2\u011b\u0115\3\2\2\2\u011b"+
		"\u0119\3\2\2\2\u011c+\3\2\2\2\u011d\u011f\t\3\2\2\u011e\u011d\3\2\2\2"+
		"\u011e\u011f\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u0121\7(\2\2\u0121-\3\2"+
		"\2\2\u0122\u012b\5,\27\2\u0123\u0124\7\b\2\2\u0124\u0126\5,\27\2\u0125"+
		"\u0123\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0125\3\2\2\2\u0127\u0128\3\2"+
		"\2\2\u0128\u012c\3\2\2\2\u0129\u012a\7\b\2\2\u012a\u012c\7\23\2\2\u012b"+
		"\u0125\3\2\2\2\u012b\u0129\3\2\2\2\u012c/\3\2\2\2\u012d\u012f\7\f\2\2"+
		"\u012e\u0130\7\r\2\2\u012f\u012e\3\2\2\2\u012f\u0130\3\2\2\2\u0130\u0131"+
		"\3\2\2\2\u0131\u0132\5,\27\2\u0132\u0134\7\24\2\2\u0133\u0135\7\16\2\2"+
		"\u0134\u0133\3\2\2\2\u0134\u0135\3\2\2\2\u0135\u0136\3\2\2\2\u0136\u0137"+
		"\5,\27\2\u0137\u0138\7\f\2\2\u0138\u0141\3\2\2\2\u0139\u013b\7\f\2\2\u013a"+
		"\u013c\5b\62\2\u013b\u013a\3\2\2\2\u013b\u013c\3\2\2\2\u013c\u013d\3\2"+
		"\2\2\u013d\u013e\5,\27\2\u013e\u013f\7\f\2\2\u013f\u0141\3\2\2\2\u0140"+
		"\u012d\3\2\2\2\u0140\u0139\3\2\2\2\u0141\61\3\2\2\2\u0142\u014b\5\60\31"+
		"\2\u0143\u0144\7\b\2\2\u0144\u0146\5\60\31\2\u0145\u0143\3\2\2\2\u0146"+
		"\u0147\3\2\2\2\u0147\u0145\3\2\2\2\u0147\u0148\3\2\2\2\u0148\u014c\3\2"+
		"\2\2\u0149\u014a\7\b\2\2\u014a\u014c\7\23\2\2\u014b\u0145\3\2\2\2\u014b"+
		"\u0149\3\2\2\2\u014c\63\3\2\2\2\u014d\u014e\t\4\2\2\u014e\65\3\2\2\2\u014f"+
		"\u0158\5\64\33\2\u0150\u0151\7\b\2\2\u0151\u0153\5\64\33\2\u0152\u0150"+
		"\3\2\2\2\u0153\u0154\3\2\2\2\u0154\u0152\3\2\2\2\u0154\u0155\3\2\2\2\u0155"+
		"\u0159\3\2\2\2\u0156\u0157\7\b\2\2\u0157\u0159\7\23\2\2\u0158\u0152\3"+
		"\2\2\2\u0158\u0156\3\2\2\2\u0159\67\3\2\2\2\u015a\u015b\7*\2\2\u015b9"+
		"\3\2\2\2\u015c\u0165\58\35\2\u015d\u015e\7\b\2\2\u015e\u0160\58\35\2\u015f"+
		"\u015d\3\2\2\2\u0160\u0161\3\2\2\2\u0161\u015f\3\2\2\2\u0161\u0162\3\2"+
		"\2\2\u0162\u0166\3\2\2\2\u0163\u0164\7\b\2\2\u0164\u0166\7\23\2\2\u0165"+
		"\u015f\3\2\2\2\u0165\u0163\3\2\2\2\u0166;\3\2\2\2\u0167\u0168\7\31\2\2"+
		"\u0168=\3\2\2\2\u0169\u0172\5<\37\2\u016a\u016b\7\b\2\2\u016b\u016d\5"+
		"<\37\2\u016c\u016a\3\2\2\2\u016d\u016e\3\2\2\2\u016e\u016c\3\2\2\2\u016e"+
		"\u016f\3\2\2\2\u016f\u0173\3\2\2\2\u0170\u0171\7\b\2\2\u0171\u0173\7\23"+
		"\2\2\u0172\u016c\3\2\2\2\u0172\u0170\3\2\2\2\u0173?\3\2\2\2\u0174\u0176"+
		"\7\f\2\2\u0175\u0177\7\r\2\2\u0176\u0175\3\2\2\2\u0176\u0177\3\2\2\2\u0177"+
		"\u0178\3\2\2\2\u0178\u0179\5<\37\2\u0179\u017b\7\24\2\2\u017a\u017c\7"+
		"\16\2\2\u017b\u017a\3\2\2\2\u017b\u017c\3\2\2\2\u017c\u017d\3\2\2\2\u017d"+
		"\u017e\5<\37\2\u017e\u017f\7\f\2\2\u017f\u0188\3\2\2\2\u0180\u0182\7\f"+
		"\2\2\u0181\u0183\5b\62\2\u0182\u0181\3\2\2\2\u0182\u0183\3\2\2\2\u0183"+
		"\u0184\3\2\2\2\u0184\u0185\5<\37\2\u0185\u0186\7\f\2\2\u0186\u0188\3\2"+
		"\2\2\u0187\u0174\3\2\2\2\u0187\u0180\3\2\2\2\u0188A\3\2\2\2\u0189\u0192"+
		"\5@!\2\u018a\u018b\7\b\2\2\u018b\u018d\5@!\2\u018c\u018a\3\2\2\2\u018d"+
		"\u018e\3\2\2\2\u018e\u018c\3\2\2\2\u018e\u018f\3\2\2\2\u018f\u0193\3\2"+
		"\2\2\u0190\u0191\7\b\2\2\u0191\u0193\7\23\2\2\u0192\u018c\3\2\2\2\u0192"+
		"\u0190\3\2\2\2\u0193C\3\2\2\2\u0194\u0195\7\32\2\2\u0195E\3\2\2\2\u0196"+
		"\u019f\5D#\2\u0197\u0198\7\b\2\2\u0198\u019a\5D#\2\u0199\u0197\3\2\2\2"+
		"\u019a\u019b\3\2\2\2\u019b\u0199\3\2\2\2\u019b\u019c\3\2\2\2\u019c\u01a0"+
		"\3\2\2\2\u019d\u019e\7\b\2\2\u019e\u01a0\7\23\2\2\u019f\u0199\3\2\2\2"+
		"\u019f\u019d\3\2\2\2\u01a0G\3\2\2\2\u01a1\u01a3\7\f\2\2\u01a2\u01a4\7"+
		"\r\2\2\u01a3\u01a2\3\2\2\2\u01a3\u01a4\3\2\2\2\u01a4\u01a5\3\2\2\2\u01a5"+
		"\u01a6\5D#\2\u01a6\u01a8\7\24\2\2\u01a7\u01a9\7\16\2\2\u01a8\u01a7\3\2"+
		"\2\2\u01a8\u01a9\3\2\2\2\u01a9\u01aa\3\2\2\2\u01aa\u01ab\5D#\2\u01ab\u01ac"+
		"\7\f\2\2\u01ac\u01b5\3\2\2\2\u01ad\u01af\7\f\2\2\u01ae\u01b0\5b\62\2\u01af"+
		"\u01ae\3\2\2\2\u01af\u01b0\3\2\2\2\u01b0\u01b1\3\2\2\2\u01b1\u01b2\5D"+
		"#\2\u01b2\u01b3\7\f\2\2\u01b3\u01b5\3\2\2\2\u01b4\u01a1\3\2\2\2\u01b4"+
		"\u01ad\3\2\2\2\u01b5I\3\2\2\2\u01b6\u01bf\5H%\2\u01b7\u01b8\7\b\2\2\u01b8"+
		"\u01ba\5H%\2\u01b9\u01b7\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01b9\3\2\2"+
		"\2\u01bb\u01bc\3\2\2\2\u01bc\u01c0\3\2\2\2\u01bd\u01be\7\b\2\2\u01be\u01c0"+
		"\7\23\2\2\u01bf\u01b9\3\2\2\2\u01bf\u01bd\3\2\2\2\u01c0K\3\2\2\2\u01c1"+
		"\u01c2\7\33\2\2\u01c2M\3\2\2\2\u01c3\u01cc\5L\'\2\u01c4\u01c5\7\b\2\2"+
		"\u01c5\u01c7\5L\'\2\u01c6\u01c4\3\2\2\2\u01c7\u01c8\3\2\2\2\u01c8\u01c6"+
		"\3\2\2\2\u01c8\u01c9\3\2\2\2\u01c9\u01cd\3\2\2\2\u01ca\u01cb\7\b\2\2\u01cb"+
		"\u01cd\7\23\2\2\u01cc\u01c6\3\2\2\2\u01cc\u01ca\3\2\2\2\u01cdO\3\2\2\2"+
		"\u01ce\u01d0\7\f\2\2\u01cf\u01d1\7\r\2\2\u01d0\u01cf\3\2\2\2\u01d0\u01d1"+
		"\3\2\2\2\u01d1\u01d2\3\2\2\2\u01d2\u01d3\5L\'\2\u01d3\u01d5\7\24\2\2\u01d4"+
		"\u01d6\7\16\2\2\u01d5\u01d4\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6\u01d7\3"+
		"\2\2\2\u01d7\u01d8\5L\'\2\u01d8\u01d9\7\f\2\2\u01d9\u01e2\3\2\2\2\u01da"+
		"\u01dc\7\f\2\2\u01db\u01dd\5b\62\2\u01dc\u01db\3\2\2\2\u01dc\u01dd\3\2"+
		"\2\2\u01dd\u01de\3\2\2\2\u01de\u01df\5L\'\2\u01df\u01e0\7\f\2\2\u01e0"+
		"\u01e2\3\2\2\2\u01e1\u01ce\3\2\2\2\u01e1\u01da\3\2\2\2\u01e2Q\3\2\2\2"+
		"\u01e3\u01ec\5P)\2\u01e4\u01e5\7\b\2\2\u01e5\u01e7\5P)\2\u01e6\u01e4\3"+
		"\2\2\2\u01e7\u01e8\3\2\2\2\u01e8\u01e6\3\2\2\2\u01e8\u01e9\3\2\2\2\u01e9"+
		"\u01ed\3\2\2\2\u01ea\u01eb\7\b\2\2\u01eb\u01ed\7\23\2\2\u01ec\u01e6\3"+
		"\2\2\2\u01ec\u01ea\3\2\2\2\u01edS\3\2\2\2\u01ee\u01ef\7\34\2\2\u01efU"+
		"\3\2\2\2\u01f0\u01f9\5T+\2\u01f1\u01f2\7\b\2\2\u01f2\u01f4\5T+\2\u01f3"+
		"\u01f1\3\2\2\2\u01f4\u01f5\3\2\2\2\u01f5\u01f3\3\2\2\2\u01f5\u01f6\3\2"+
		"\2\2\u01f6\u01fa\3\2\2\2\u01f7\u01f8\7\b\2\2\u01f8\u01fa\7\23\2\2\u01f9"+
		"\u01f3\3\2\2\2\u01f9\u01f7\3\2\2\2\u01faW\3\2\2\2\u01fb\u01fd\7\f\2\2"+
		"\u01fc\u01fe\7\r\2\2\u01fd\u01fc\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fe\u01ff"+
		"\3\2\2\2\u01ff\u0200\5T+\2\u0200\u0202\7\24\2\2\u0201\u0203\7\16\2\2\u0202"+
		"\u0201\3\2\2\2\u0202\u0203\3\2\2\2\u0203\u0204\3\2\2\2\u0204\u0205\5T"+
		"+\2\u0205\u0206\7\f\2\2\u0206\u020f\3\2\2\2\u0207\u0209\7\f\2\2\u0208"+
		"\u020a\5b\62\2\u0209\u0208\3\2\2\2\u0209\u020a\3\2\2\2\u020a\u020b\3\2"+
		"\2\2\u020b\u020c\5T+\2\u020c\u020d\7\f\2\2\u020d\u020f\3\2\2\2\u020e\u01fb"+
		"\3\2\2\2\u020e\u0207\3\2\2\2\u020fY\3\2\2\2\u0210\u0219\5X-\2\u0211\u0212"+
		"\7\b\2\2\u0212\u0214\5X-\2\u0213\u0211\3\2\2\2\u0214\u0215\3\2\2\2\u0215"+
		"\u0213\3\2\2\2\u0215\u0216\3\2\2\2\u0216\u021a\3\2\2\2\u0217\u0218\7\b"+
		"\2\2\u0218\u021a\7\23\2\2\u0219\u0213\3\2\2\2\u0219\u0217\3\2\2\2\u021a"+
		"[\3\2\2\2\u021b\u021c\7\"\2\2\u021c]\3\2\2\2\u021d\u0226\5\\/\2\u021e"+
		"\u021f\7\b\2\2\u021f\u0221\5\\/\2\u0220\u021e\3\2\2\2\u0221\u0222\3\2"+
		"\2\2\u0222\u0220\3\2\2\2\u0222\u0223\3\2\2\2\u0223\u0227\3\2\2\2\u0224"+
		"\u0225\7\b\2\2\u0225\u0227\7\23\2\2\u0226\u0220\3\2\2\2\u0226\u0224\3"+
		"\2\2\2\u0227_\3\2\2\2\u0228\u0229\7#\2\2\u0229a\3\2\2\2\u022a\u022b\t"+
		"\5\2\2\u022bc\3\2\2\2\u022c\u0238\7%\2\2\u022d\u022e\7\16\2\2\u022e\u0233"+
		"\5d\63\2\u022f\u0230\7\b\2\2\u0230\u0232\5d\63\2\u0231\u022f\3\2\2\2\u0232"+
		"\u0235\3\2\2\2\u0233\u0231\3\2\2\2\u0233\u0234\3\2\2\2\u0234\u0236\3\2"+
		"\2\2\u0235\u0233\3\2\2\2\u0236\u0237\7\r\2\2\u0237\u0239\3\2\2\2\u0238"+
		"\u022d\3\2\2\2\u0238\u0239\3\2\2\2\u0239e\3\2\2\2\u023a\u023b\7&\2\2\u023b"+
		"g\3\2\2\2\u023c\u023d\t\6\2\2\u023di\3\2\2\2\u023e\u023f\t\7\2\2\u023f"+
		"k\3\2\2\2Onrv~\u0084\u0089\u008e\u0091\u009e\u00ab\u00b7\u00bf\u00ca\u00cd"+
		"\u00d3\u00d5\u00de\u00e7\u00eb\u00ee\u00f7\u00fb\u00ff\u0104\u010b\u0110"+
		"\u0117\u011b\u011e\u0127\u012b\u012f\u0134\u013b\u0140\u0147\u014b\u0154"+
		"\u0158\u0161\u0165\u016e\u0172\u0176\u017b\u0182\u0187\u018e\u0192\u019b"+
		"\u019f\u01a3\u01a8\u01af\u01b4\u01bb\u01bf\u01c8\u01cc\u01d0\u01d5\u01dc"+
		"\u01e1\u01e8\u01ec\u01f5\u01f9\u01fd\u0202\u0209\u020e\u0215\u0219\u0222"+
		"\u0226\u0233\u0238";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}