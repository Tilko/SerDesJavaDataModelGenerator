// Generated from C:\Users\marti\workingLowLevel\codeGen\src\main\java\org\gmart\codeGen\javaGen\fromYaml\modelExtraction\parserGeneration\DataTypeHierarchy.g4 by ANTLR 4.8
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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, ArrayMarks=16, 
		WS=17, Identifier=18;
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
			null, "'is'", "'?'", "'oneOf'", "'('", "','", "')'", "'Dict'", "'['", 
			"']'", "'Map'", "'abstract'", "'enum'", "'<'", "'>'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, "ArrayMarks", "WS", "Identifier"
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
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterOnOneLineTypeDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitOnOneLineTypeDef(this);
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
		enterRule(_localctx, 8, RULE_mapTypeExpression);
		int _la;
		try {
			setState(72);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				match(T__6);
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(53);
					match(T__7);
					setState(54);
					match(Identifier);
					setState(55);
					match(T__8);
					}
				}

				setState(58);
				diamondOneArg();
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
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
				match(T__9);
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(63);
					match(T__7);
					setState(64);
					match(Identifier);
					setState(65);
					match(T__8);
					}
				}

				setState(68);
				diamondTwoArg();
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
		enterRule(_localctx, 10, RULE_typeExpression);
		int _la;
		try {
			setState(87);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				((TypeExpressionContext)_localctx).abstractFieldMark = match(T__10);
				setState(79);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__11:
					{
					setState(75);
					anonymousEnumField();
					}
					break;
				case T__4:
				case T__5:
				case T__13:
				case Identifier:
					{
					setState(77);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==Identifier) {
						{
						setState(76);
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
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				anonymousEnumField();
				}
				break;
			case T__6:
			case T__9:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				mapTypeExpression();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 4);
				{
				setState(83);
				qualifiedName();
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ArrayMarks) {
					{
					setState(84);
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
		enterRule(_localctx, 12, RULE_anonymousEnumField);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			((AnonymousEnumFieldContext)_localctx).enumMark = match(T__11);
			setState(90);
			match(T__3);
			setState(91);
			identifierList();
			setState(92);
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
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterDiamondOneArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitDiamondOneArg(this);
		}
	}

	public final DiamondOneArgContext diamondOneArg() throws RecognitionException {
		DiamondOneArgContext _localctx = new DiamondOneArgContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_diamondOneArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(T__12);
			setState(95);
			typeExpression();
			setState(96);
			match(T__13);
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
		enterRule(_localctx, 16, RULE_diamondTwoArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(T__12);
			setState(99);
			qualifiedName();
			setState(100);
			match(T__4);
			setState(101);
			typeExpression();
			setState(102);
			match(T__13);
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
		enterRule(_localctx, 18, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(Identifier);
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__14) {
				{
				{
				setState(105);
				match(T__14);
				setState(106);
				match(Identifier);
				}
				}
				setState(111);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\24s\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\3"+
		"\2\3\2\3\2\5\2\32\n\2\3\3\3\3\5\3\36\n\3\3\4\3\4\3\4\3\4\3\4\7\4%\n\4"+
		"\f\4\16\4(\13\4\3\4\3\4\3\4\5\4-\n\4\3\5\3\5\3\5\7\5\62\n\5\f\5\16\5\65"+
		"\13\5\3\6\3\6\3\6\3\6\5\6;\n\6\3\6\3\6\5\6?\n\6\3\6\3\6\3\6\3\6\5\6E\n"+
		"\6\3\6\3\6\5\6I\n\6\5\6K\n\6\3\7\3\7\3\7\5\7P\n\7\5\7R\n\7\3\7\3\7\3\7"+
		"\3\7\5\7X\n\7\5\7Z\n\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\13\3\13\3\13\7\13n\n\13\f\13\16\13q\13\13\3\13\2\2\f\2"+
		"\4\6\b\n\f\16\20\22\24\2\2\2y\2\26\3\2\2\2\4\33\3\2\2\2\6,\3\2\2\2\b."+
		"\3\2\2\2\nJ\3\2\2\2\fY\3\2\2\2\16[\3\2\2\2\20`\3\2\2\2\22d\3\2\2\2\24"+
		"j\3\2\2\2\26\31\7\24\2\2\27\30\7\3\2\2\30\32\5\24\13\2\31\27\3\2\2\2\31"+
		"\32\3\2\2\2\32\3\3\2\2\2\33\35\7\24\2\2\34\36\7\4\2\2\35\34\3\2\2\2\35"+
		"\36\3\2\2\2\36\5\3\2\2\2\37 \7\5\2\2 !\7\6\2\2!&\5\f\7\2\"#\7\7\2\2#%"+
		"\5\f\7\2$\"\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\')\3\2\2\2(&\3\2\2"+
		"\2)*\7\b\2\2*-\3\2\2\2+-\5\b\5\2,\37\3\2\2\2,+\3\2\2\2-\7\3\2\2\2.\63"+
		"\7\24\2\2/\60\7\7\2\2\60\62\7\24\2\2\61/\3\2\2\2\62\65\3\2\2\2\63\61\3"+
		"\2\2\2\63\64\3\2\2\2\64\t\3\2\2\2\65\63\3\2\2\2\66:\7\t\2\2\678\7\n\2"+
		"\289\7\24\2\29;\7\13\2\2:\67\3\2\2\2:;\3\2\2\2;<\3\2\2\2<>\5\20\t\2=?"+
		"\7\22\2\2>=\3\2\2\2>?\3\2\2\2?K\3\2\2\2@D\7\f\2\2AB\7\n\2\2BC\7\24\2\2"+
		"CE\7\13\2\2DA\3\2\2\2DE\3\2\2\2EF\3\2\2\2FH\5\22\n\2GI\7\22\2\2HG\3\2"+
		"\2\2HI\3\2\2\2IK\3\2\2\2J\66\3\2\2\2J@\3\2\2\2K\13\3\2\2\2LQ\7\r\2\2M"+
		"R\5\16\b\2NP\5\24\13\2ON\3\2\2\2OP\3\2\2\2PR\3\2\2\2QM\3\2\2\2QO\3\2\2"+
		"\2RZ\3\2\2\2SZ\5\16\b\2TZ\5\n\6\2UW\5\24\13\2VX\7\22\2\2WV\3\2\2\2WX\3"+
		"\2\2\2XZ\3\2\2\2YL\3\2\2\2YS\3\2\2\2YT\3\2\2\2YU\3\2\2\2Z\r\3\2\2\2[\\"+
		"\7\16\2\2\\]\7\6\2\2]^\5\b\5\2^_\7\b\2\2_\17\3\2\2\2`a\7\17\2\2ab\5\f"+
		"\7\2bc\7\20\2\2c\21\3\2\2\2de\7\17\2\2ef\5\24\13\2fg\7\7\2\2gh\5\f\7\2"+
		"hi\7\20\2\2i\23\3\2\2\2jo\7\24\2\2kl\7\21\2\2ln\7\24\2\2mk\3\2\2\2nq\3"+
		"\2\2\2om\3\2\2\2op\3\2\2\2p\25\3\2\2\2qo\3\2\2\2\21\31\35&,\63:>DHJOQ"+
		"WYo";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}