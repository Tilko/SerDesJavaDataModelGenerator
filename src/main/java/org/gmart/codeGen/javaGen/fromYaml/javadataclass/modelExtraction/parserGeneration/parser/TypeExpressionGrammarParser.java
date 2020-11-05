// Generated from C:\Users\marti\workingLowLevel\codeGen\src\main\java\org\gmart\codeGen\javaGen\fromYaml\javadataclass\typedefsExtraction\typeExpression\TypeExpressionGrammar.g4 by ANTLR 4.8
package org.gmart.codeGen.javaGen.fromYaml.javadataclass.modelExtraction.parserGeneration.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TypeExpressionGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, ArrayMarks=14, WS=15, Identifier=16;
	public static final int
		RULE_typeNamePart = 0, RULE_propertyNamePart = 1, RULE_onOneLineTypeDef = 2, 
		RULE_identifierList = 3, RULE_mapTypeExpression = 4, RULE_typeExpression = 5, 
		RULE_anonymousEnumField = 6, RULE_diamondOneArg = 7, RULE_diamondTwoArg = 8, 
		RULE_qualifiedName = 9;
	private static String[] makeRuleNames() {
		return new String[] {
			"typeNamePart", "propertyNamePart", "onOneLineTypeDef", "identifierList", 
			"mapTypeExpression", "typeExpression", "anonymousEnumField", "diamondOneArg", 
			"diamondTwoArg", "qualifiedName"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'is'", "'?'", "'oneOf'", "'('", "','", "')'", "'Dict'", "'Map'", 
			"'abstract'", "'enum'", "'<'", "'>'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "ArrayMarks", "WS", "Identifier"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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
	public String getGrammarFileName() { return "TypeExpressionGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TypeExpressionGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class TypeNamePartContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(TypeExpressionGrammarParser.Identifier, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TypeNamePartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeNamePart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).enterTypeNamePart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).exitTypeNamePart(this);
		}
	}

	public final TypeNamePartContext typeNamePart() throws RecognitionException {
		TypeNamePartContext _localctx = new TypeNamePartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_typeNamePart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			match(Identifier);
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(21);
				match(T__0);
				setState(22);
				qualifiedName();
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

	public static class PropertyNamePartContext extends ParserRuleContext {
		public Token optionalMark;
		public TerminalNode Identifier() { return getToken(TypeExpressionGrammarParser.Identifier, 0); }
		public PropertyNamePartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyNamePart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).enterPropertyNamePart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).exitPropertyNamePart(this);
		}
	}

	public final PropertyNamePartContext propertyNamePart() throws RecognitionException {
		PropertyNamePartContext _localctx = new PropertyNamePartContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_propertyNamePart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			match(Identifier);
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(26);
				((PropertyNamePartContext)_localctx).optionalMark = match(T__1);
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

	public static class OnOneLineTypeDefContext extends ParserRuleContext {
		public List<TypeExpressionContext> typeExpression() {
			return getRuleContexts(TypeExpressionContext.class);
		}
		public TypeExpressionContext typeExpression(int i) {
			return getRuleContext(TypeExpressionContext.class,i);
		}
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public OnOneLineTypeDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_onOneLineTypeDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).enterOnOneLineTypeDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).exitOnOneLineTypeDef(this);
		}
	}

	public final OnOneLineTypeDefContext onOneLineTypeDef() throws RecognitionException {
		OnOneLineTypeDefContext _localctx = new OnOneLineTypeDefContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_onOneLineTypeDef);
		int _la;
		try {
			setState(42);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(29);
				match(T__2);
				setState(30);
				match(T__3);
				setState(31);
				typeExpression();
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(32);
					match(T__4);
					setState(33);
					typeExpression();
					}
					}
					setState(38);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(39);
				match(T__5);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				identifierList();
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

	public static class IdentifierListContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(TypeExpressionGrammarParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(TypeExpressionGrammarParser.Identifier, i);
		}
		public IdentifierListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).enterIdentifierList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).exitIdentifierList(this);
		}
	}

	public final IdentifierListContext identifierList() throws RecognitionException {
		IdentifierListContext _localctx = new IdentifierListContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_identifierList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(Identifier);
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(45);
				match(T__4);
				setState(46);
				match(Identifier);
				}
				}
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class MapTypeExpressionContext extends ParserRuleContext {
		public DiamondOneArgContext diamondOneArg() {
			return getRuleContext(DiamondOneArgContext.class,0);
		}
		public TerminalNode ArrayMarks() { return getToken(TypeExpressionGrammarParser.ArrayMarks, 0); }
		public DiamondTwoArgContext diamondTwoArg() {
			return getRuleContext(DiamondTwoArgContext.class,0);
		}
		public MapTypeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapTypeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).enterMapTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).exitMapTypeExpression(this);
		}
	}

	public final MapTypeExpressionContext mapTypeExpression() throws RecognitionException {
		MapTypeExpressionContext _localctx = new MapTypeExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_mapTypeExpression);
		int _la;
		try {
			setState(62);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				match(T__6);
				setState(53);
				diamondOneArg();
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ArrayMarks) {
					{
					setState(54);
					match(ArrayMarks);
					}
				}

				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 2);
				{
				setState(57);
				match(T__7);
				setState(58);
				diamondTwoArg();
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ArrayMarks) {
					{
					setState(59);
					match(ArrayMarks);
					}
				}

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

	public static class TypeExpressionContext extends ParserRuleContext {
		public Token abstractFieldMark;
		public AnonymousEnumFieldContext anonymousEnumField() {
			return getRuleContext(AnonymousEnumFieldContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public MapTypeExpressionContext mapTypeExpression() {
			return getRuleContext(MapTypeExpressionContext.class,0);
		}
		public TerminalNode ArrayMarks() { return getToken(TypeExpressionGrammarParser.ArrayMarks, 0); }
		public TypeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).enterTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).exitTypeExpression(this);
		}
	}

	public final TypeExpressionContext typeExpression() throws RecognitionException {
		TypeExpressionContext _localctx = new TypeExpressionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_typeExpression);
		int _la;
		try {
			setState(77);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				((TypeExpressionContext)_localctx).abstractFieldMark = match(T__8);
				setState(69);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__9:
					{
					setState(65);
					anonymousEnumField();
					}
					break;
				case T__4:
				case T__5:
				case T__11:
				case Identifier:
					{
					setState(67);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==Identifier) {
						{
						setState(66);
						qualifiedName();
						}
					}

					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(71);
				anonymousEnumField();
				}
				break;
			case T__6:
			case T__7:
				enterOuterAlt(_localctx, 3);
				{
				setState(72);
				mapTypeExpression();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 4);
				{
				setState(73);
				qualifiedName();
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ArrayMarks) {
					{
					setState(74);
					match(ArrayMarks);
					}
				}

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

	public static class AnonymousEnumFieldContext extends ParserRuleContext {
		public Token enumMark;
		public IdentifierListContext identifierList() {
			return getRuleContext(IdentifierListContext.class,0);
		}
		public AnonymousEnumFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anonymousEnumField; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).enterAnonymousEnumField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).exitAnonymousEnumField(this);
		}
	}

	public final AnonymousEnumFieldContext anonymousEnumField() throws RecognitionException {
		AnonymousEnumFieldContext _localctx = new AnonymousEnumFieldContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_anonymousEnumField);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			((AnonymousEnumFieldContext)_localctx).enumMark = match(T__9);
			setState(80);
			match(T__3);
			setState(81);
			identifierList();
			setState(82);
			match(T__5);
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

	public static class DiamondOneArgContext extends ParserRuleContext {
		public TypeExpressionContext typeExpression() {
			return getRuleContext(TypeExpressionContext.class,0);
		}
		public DiamondOneArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_diamondOneArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).enterDiamondOneArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).exitDiamondOneArg(this);
		}
	}

	public final DiamondOneArgContext diamondOneArg() throws RecognitionException {
		DiamondOneArgContext _localctx = new DiamondOneArgContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_diamondOneArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(T__10);
			setState(85);
			typeExpression();
			setState(86);
			match(T__11);
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

	public static class DiamondTwoArgContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TypeExpressionContext typeExpression() {
			return getRuleContext(TypeExpressionContext.class,0);
		}
		public DiamondTwoArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_diamondTwoArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).enterDiamondTwoArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).exitDiamondTwoArg(this);
		}
	}

	public final DiamondTwoArgContext diamondTwoArg() throws RecognitionException {
		DiamondTwoArgContext _localctx = new DiamondTwoArgContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_diamondTwoArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(T__10);
			setState(89);
			qualifiedName();
			setState(90);
			match(T__4);
			setState(91);
			typeExpression();
			setState(92);
			match(T__11);
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

	public static class QualifiedNameContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(TypeExpressionGrammarParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(TypeExpressionGrammarParser.Identifier, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).enterQualifiedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TypeExpressionGrammarListener ) ((TypeExpressionGrammarListener)listener).exitQualifiedName(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(Identifier);
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__12) {
				{
				{
				setState(95);
				match(T__12);
				setState(96);
				match(Identifier);
				}
				}
				setState(101);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\22i\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\3"+
		"\2\3\2\3\2\5\2\32\n\2\3\3\3\3\5\3\36\n\3\3\4\3\4\3\4\3\4\3\4\7\4%\n\4"+
		"\f\4\16\4(\13\4\3\4\3\4\3\4\5\4-\n\4\3\5\3\5\3\5\7\5\62\n\5\f\5\16\5\65"+
		"\13\5\3\6\3\6\3\6\5\6:\n\6\3\6\3\6\3\6\5\6?\n\6\5\6A\n\6\3\7\3\7\3\7\5"+
		"\7F\n\7\5\7H\n\7\3\7\3\7\3\7\3\7\5\7N\n\7\5\7P\n\7\3\b\3\b\3\b\3\b\3\b"+
		"\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\7\13d\n\13\f\13"+
		"\16\13g\13\13\3\13\2\2\f\2\4\6\b\n\f\16\20\22\24\2\2\2m\2\26\3\2\2\2\4"+
		"\33\3\2\2\2\6,\3\2\2\2\b.\3\2\2\2\n@\3\2\2\2\fO\3\2\2\2\16Q\3\2\2\2\20"+
		"V\3\2\2\2\22Z\3\2\2\2\24`\3\2\2\2\26\31\7\22\2\2\27\30\7\3\2\2\30\32\5"+
		"\24\13\2\31\27\3\2\2\2\31\32\3\2\2\2\32\3\3\2\2\2\33\35\7\22\2\2\34\36"+
		"\7\4\2\2\35\34\3\2\2\2\35\36\3\2\2\2\36\5\3\2\2\2\37 \7\5\2\2 !\7\6\2"+
		"\2!&\5\f\7\2\"#\7\7\2\2#%\5\f\7\2$\"\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3"+
		"\2\2\2\')\3\2\2\2(&\3\2\2\2)*\7\b\2\2*-\3\2\2\2+-\5\b\5\2,\37\3\2\2\2"+
		",+\3\2\2\2-\7\3\2\2\2.\63\7\22\2\2/\60\7\7\2\2\60\62\7\22\2\2\61/\3\2"+
		"\2\2\62\65\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\t\3\2\2\2\65\63\3\2"+
		"\2\2\66\67\7\t\2\2\679\5\20\t\28:\7\20\2\298\3\2\2\29:\3\2\2\2:A\3\2\2"+
		"\2;<\7\n\2\2<>\5\22\n\2=?\7\20\2\2>=\3\2\2\2>?\3\2\2\2?A\3\2\2\2@\66\3"+
		"\2\2\2@;\3\2\2\2A\13\3\2\2\2BG\7\13\2\2CH\5\16\b\2DF\5\24\13\2ED\3\2\2"+
		"\2EF\3\2\2\2FH\3\2\2\2GC\3\2\2\2GE\3\2\2\2HP\3\2\2\2IP\5\16\b\2JP\5\n"+
		"\6\2KM\5\24\13\2LN\7\20\2\2ML\3\2\2\2MN\3\2\2\2NP\3\2\2\2OB\3\2\2\2OI"+
		"\3\2\2\2OJ\3\2\2\2OK\3\2\2\2P\r\3\2\2\2QR\7\f\2\2RS\7\6\2\2ST\5\b\5\2"+
		"TU\7\b\2\2U\17\3\2\2\2VW\7\r\2\2WX\5\f\7\2XY\7\16\2\2Y\21\3\2\2\2Z[\7"+
		"\r\2\2[\\\5\24\13\2\\]\7\7\2\2]^\5\f\7\2^_\7\16\2\2_\23\3\2\2\2`e\7\22"+
		"\2\2ab\7\17\2\2bd\7\22\2\2ca\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2f\25"+
		"\3\2\2\2ge\3\2\2\2\17\31\35&,\639>@EGMOe";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}