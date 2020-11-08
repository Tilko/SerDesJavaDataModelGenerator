// Generated from C:\Users\marti\workingLowLevel\codeGen\src\main\java\org\gmart\codeGen\javaGen\modelExtraction\parserGeneration\DataTypeHierarchy.g4 by ANTLR 4.8
package org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser;
import java.util.List;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DataTypeHierarchyParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, ArrayMarks=17, 
		WS=18, Identifier=19;
	public static final int
		RULE_typeNamePart = 0, RULE_propertyNamePart = 1, RULE_onOneLineTypeName = 2, 
		RULE_onOneLineTypeDef = 3, RULE_identifierList = 4, RULE_mapTypeExpression = 5, 
		RULE_typeExpression = 6, RULE_anonymousEnumField = 7, RULE_diamondOneArg = 8, 
		RULE_diamondTwoArg = 9, RULE_qualifiedName = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"typeNamePart", "propertyNamePart", "onOneLineTypeName", "onOneLineTypeDef", 
			"identifierList", "mapTypeExpression", "typeExpression", "anonymousEnumField", 
			"diamondOneArg", "diamondTwoArg", "qualifiedName"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'stubbed'", "'is'", "'?'", "'oneOf'", "'('", "','", "')'", "'Dict'", 
			"'['", "']'", "'Map'", "'abstract'", "'enum'", "'<'", "'>'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "ArrayMarks", "WS", "Identifier"
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
	public String getGrammarFileName() { return "DataTypeHierarchy.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DataTypeHierarchyParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class TypeNamePartContext extends ParserRuleContext {
		public Token stubbedMark;
		public TerminalNode Identifier() { return getToken(DataTypeHierarchyParser.Identifier, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TypeNamePartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeNamePart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterTypeNamePart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitTypeNamePart(this);
		}
	}

	public final TypeNamePartContext typeNamePart() throws RecognitionException {
		TypeNamePartContext _localctx = new TypeNamePartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_typeNamePart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(22);
				((TypeNamePartContext)_localctx).stubbedMark = match(T__0);
				}
			}

			setState(25);
			match(Identifier);
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(26);
				match(T__1);
				setState(27);
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
		public TerminalNode Identifier() { return getToken(DataTypeHierarchyParser.Identifier, 0); }
		public PropertyNamePartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyNamePart; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterPropertyNamePart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitPropertyNamePart(this);
		}
	}

	public final PropertyNamePartContext propertyNamePart() throws RecognitionException {
		PropertyNamePartContext _localctx = new PropertyNamePartContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_propertyNamePart);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(Identifier);
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(31);
				((PropertyNamePartContext)_localctx).optionalMark = match(T__2);
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

	public static class OnOneLineTypeNameContext extends ParserRuleContext {
		public Token stubbedMark;
		public TerminalNode Identifier() { return getToken(DataTypeHierarchyParser.Identifier, 0); }
		public OnOneLineTypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_onOneLineTypeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterOnOneLineTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitOnOneLineTypeName(this);
		}
	}

	public final OnOneLineTypeNameContext onOneLineTypeName() throws RecognitionException {
		OnOneLineTypeNameContext _localctx = new OnOneLineTypeNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_onOneLineTypeName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(34);
				((OnOneLineTypeNameContext)_localctx).stubbedMark = match(T__0);
				}
			}

			setState(37);
			match(Identifier);
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
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterOnOneLineTypeDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitOnOneLineTypeDef(this);
		}
	}

	public final OnOneLineTypeDefContext onOneLineTypeDef() throws RecognitionException {
		OnOneLineTypeDefContext _localctx = new OnOneLineTypeDefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_onOneLineTypeDef);
		int _la;
		try {
			setState(52);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(39);
				match(T__3);
				setState(40);
				match(T__4);
				setState(41);
				typeExpression();
				setState(46);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__5) {
					{
					{
					setState(42);
					match(T__5);
					setState(43);
					typeExpression();
					}
					}
					setState(48);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(49);
				match(T__6);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(51);
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
		public List<TerminalNode> Identifier() { return getTokens(DataTypeHierarchyParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(DataTypeHierarchyParser.Identifier, i);
		}
		public IdentifierListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifierList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterIdentifierList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitIdentifierList(this);
		}
	}

	public final IdentifierListContext identifierList() throws RecognitionException {
		IdentifierListContext _localctx = new IdentifierListContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_identifierList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(Identifier);
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(55);
				match(T__5);
				setState(56);
				match(Identifier);
				}
				}
				setState(61);
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
		public TerminalNode Identifier() { return getToken(DataTypeHierarchyParser.Identifier, 0); }
		public TerminalNode ArrayMarks() { return getToken(DataTypeHierarchyParser.ArrayMarks, 0); }
		public DiamondTwoArgContext diamondTwoArg() {
			return getRuleContext(DiamondTwoArgContext.class,0);
		}
		public MapTypeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapTypeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterMapTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitMapTypeExpression(this);
		}
	}

	public final MapTypeExpressionContext mapTypeExpression() throws RecognitionException {
		MapTypeExpressionContext _localctx = new MapTypeExpressionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_mapTypeExpression);
		int _la;
		try {
			setState(82);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				setState(62);
				match(T__7);
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__8) {
					{
					setState(63);
					match(T__8);
					setState(64);
					match(Identifier);
					setState(65);
					match(T__9);
					}
				}

				setState(68);
				diamondOneArg();
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ArrayMarks) {
					{
					setState(69);
					match(ArrayMarks);
					}
				}

				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 2);
				{
				setState(72);
				match(T__10);
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__8) {
					{
					setState(73);
					match(T__8);
					setState(74);
					match(Identifier);
					setState(75);
					match(T__9);
					}
				}

				setState(78);
				diamondTwoArg();
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ArrayMarks) {
					{
					setState(79);
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
		public TerminalNode ArrayMarks() { return getToken(DataTypeHierarchyParser.ArrayMarks, 0); }
		public TypeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterTypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitTypeExpression(this);
		}
	}

	public final TypeExpressionContext typeExpression() throws RecognitionException {
		TypeExpressionContext _localctx = new TypeExpressionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_typeExpression);
		int _la;
		try {
			setState(97);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				((TypeExpressionContext)_localctx).abstractFieldMark = match(T__11);
				setState(89);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__12:
					{
					setState(85);
					anonymousEnumField();
					}
					break;
				case T__5:
				case T__6:
				case T__14:
				case Identifier:
					{
					setState(87);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==Identifier) {
						{
						setState(86);
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
			case T__12:
				enterOuterAlt(_localctx, 2);
				{
				setState(91);
				anonymousEnumField();
				}
				break;
			case T__7:
			case T__10:
				enterOuterAlt(_localctx, 3);
				{
				setState(92);
				mapTypeExpression();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 4);
				{
				setState(93);
				qualifiedName();
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ArrayMarks) {
					{
					setState(94);
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
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterAnonymousEnumField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitAnonymousEnumField(this);
		}
	}

	public final AnonymousEnumFieldContext anonymousEnumField() throws RecognitionException {
		AnonymousEnumFieldContext _localctx = new AnonymousEnumFieldContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_anonymousEnumField);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			((AnonymousEnumFieldContext)_localctx).enumMark = match(T__12);
			setState(100);
			match(T__4);
			setState(101);
			identifierList();
			setState(102);
			match(T__6);
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
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterDiamondOneArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitDiamondOneArg(this);
		}
	}

	public final DiamondOneArgContext diamondOneArg() throws RecognitionException {
		DiamondOneArgContext _localctx = new DiamondOneArgContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_diamondOneArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(T__13);
			setState(105);
			typeExpression();
			setState(106);
			match(T__14);
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
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterDiamondTwoArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitDiamondTwoArg(this);
		}
	}

	public final DiamondTwoArgContext diamondTwoArg() throws RecognitionException {
		DiamondTwoArgContext _localctx = new DiamondTwoArgContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_diamondTwoArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			match(T__13);
			setState(109);
			qualifiedName();
			setState(110);
			match(T__5);
			setState(111);
			typeExpression();
			setState(112);
			match(T__14);
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
		public List<TerminalNode> Identifier() { return getTokens(DataTypeHierarchyParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(DataTypeHierarchyParser.Identifier, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterQualifiedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitQualifiedName(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(Identifier);
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__15) {
				{
				{
				setState(115);
				match(T__15);
				setState(116);
				match(Identifier);
				}
				}
				setState(121);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\25}\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\5\2\32\n\2\3\2\3\2\3\2\5\2\37\n\2\3\3\3\3\5\3#\n\3\3\4\5\4"+
		"&\n\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\7\5/\n\5\f\5\16\5\62\13\5\3\5\3\5\3"+
		"\5\5\5\67\n\5\3\6\3\6\3\6\7\6<\n\6\f\6\16\6?\13\6\3\7\3\7\3\7\3\7\5\7"+
		"E\n\7\3\7\3\7\5\7I\n\7\3\7\3\7\3\7\3\7\5\7O\n\7\3\7\3\7\5\7S\n\7\5\7U"+
		"\n\7\3\b\3\b\3\b\5\bZ\n\b\5\b\\\n\b\3\b\3\b\3\b\3\b\5\bb\n\b\5\bd\n\b"+
		"\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f"+
		"\3\f\3\f\7\fx\n\f\f\f\16\f{\13\f\3\f\2\2\r\2\4\6\b\n\f\16\20\22\24\26"+
		"\2\2\2\u0084\2\31\3\2\2\2\4 \3\2\2\2\6%\3\2\2\2\b\66\3\2\2\2\n8\3\2\2"+
		"\2\fT\3\2\2\2\16c\3\2\2\2\20e\3\2\2\2\22j\3\2\2\2\24n\3\2\2\2\26t\3\2"+
		"\2\2\30\32\7\3\2\2\31\30\3\2\2\2\31\32\3\2\2\2\32\33\3\2\2\2\33\36\7\25"+
		"\2\2\34\35\7\4\2\2\35\37\5\26\f\2\36\34\3\2\2\2\36\37\3\2\2\2\37\3\3\2"+
		"\2\2 \"\7\25\2\2!#\7\5\2\2\"!\3\2\2\2\"#\3\2\2\2#\5\3\2\2\2$&\7\3\2\2"+
		"%$\3\2\2\2%&\3\2\2\2&\'\3\2\2\2\'(\7\25\2\2(\7\3\2\2\2)*\7\6\2\2*+\7\7"+
		"\2\2+\60\5\16\b\2,-\7\b\2\2-/\5\16\b\2.,\3\2\2\2/\62\3\2\2\2\60.\3\2\2"+
		"\2\60\61\3\2\2\2\61\63\3\2\2\2\62\60\3\2\2\2\63\64\7\t\2\2\64\67\3\2\2"+
		"\2\65\67\5\n\6\2\66)\3\2\2\2\66\65\3\2\2\2\67\t\3\2\2\28=\7\25\2\29:\7"+
		"\b\2\2:<\7\25\2\2;9\3\2\2\2<?\3\2\2\2=;\3\2\2\2=>\3\2\2\2>\13\3\2\2\2"+
		"?=\3\2\2\2@D\7\n\2\2AB\7\13\2\2BC\7\25\2\2CE\7\f\2\2DA\3\2\2\2DE\3\2\2"+
		"\2EF\3\2\2\2FH\5\22\n\2GI\7\23\2\2HG\3\2\2\2HI\3\2\2\2IU\3\2\2\2JN\7\r"+
		"\2\2KL\7\13\2\2LM\7\25\2\2MO\7\f\2\2NK\3\2\2\2NO\3\2\2\2OP\3\2\2\2PR\5"+
		"\24\13\2QS\7\23\2\2RQ\3\2\2\2RS\3\2\2\2SU\3\2\2\2T@\3\2\2\2TJ\3\2\2\2"+
		"U\r\3\2\2\2V[\7\16\2\2W\\\5\20\t\2XZ\5\26\f\2YX\3\2\2\2YZ\3\2\2\2Z\\\3"+
		"\2\2\2[W\3\2\2\2[Y\3\2\2\2\\d\3\2\2\2]d\5\20\t\2^d\5\f\7\2_a\5\26\f\2"+
		"`b\7\23\2\2a`\3\2\2\2ab\3\2\2\2bd\3\2\2\2cV\3\2\2\2c]\3\2\2\2c^\3\2\2"+
		"\2c_\3\2\2\2d\17\3\2\2\2ef\7\17\2\2fg\7\7\2\2gh\5\n\6\2hi\7\t\2\2i\21"+
		"\3\2\2\2jk\7\20\2\2kl\5\16\b\2lm\7\21\2\2m\23\3\2\2\2no\7\20\2\2op\5\26"+
		"\f\2pq\7\b\2\2qr\5\16\b\2rs\7\21\2\2s\25\3\2\2\2ty\7\25\2\2uv\7\22\2\2"+
		"vx\7\25\2\2wu\3\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2z\27\3\2\2\2{y\3\2"+
		"\2\2\23\31\36\"%\60\66=DHNRTY[acy";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}