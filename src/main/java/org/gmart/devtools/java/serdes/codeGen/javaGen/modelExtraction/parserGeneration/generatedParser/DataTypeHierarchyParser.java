// Generated from C:\Users\marti\workingLowLevel\codeGen\src\main\java\org\gmart\codeGen\javaGen\modelExtraction\parserGeneration\DataTypeHierarchy.g4 by ANTLR 4.8
package org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser;
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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, ArrayMarks=20, WS=21, Identifier=22;
	public static final int
		RULE_typeNamePart = 0, RULE_propertyNamePart = 1, RULE_onOneLineTypeName = 2, 
		RULE_onOneLineTypeDef = 3, RULE_constructorParameters = 4, RULE_constructorParameter = 5, 
		RULE_mapTypeExpression = 6, RULE_typeExpression = 7, RULE_constructorArguments = 8, 
		RULE_pathWithKeyHole = 9, RULE_idOrKeyHole = 10, RULE_anonymousEnumField = 11, 
		RULE_diamondOneArg = 12, RULE_diamondTwoArg = 13, RULE_identifierList = 14, 
		RULE_qualifiedName = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"typeNamePart", "propertyNamePart", "onOneLineTypeName", "onOneLineTypeDef", 
			"constructorParameters", "constructorParameter", "mapTypeExpression", 
			"typeExpression", "constructorArguments", "pathWithKeyHole", "idOrKeyHole", 
			"anonymousEnumField", "diamondOneArg", "diamondTwoArg", "identifierList", 
			"qualifiedName"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'stubbed'", "'('", "')'", "'is'", "'?'", "'oneOf'", "','", "'Accessor'", 
			"'<'", "'>'", "'Dict'", "'['", "']'", "'Map'", "'abstract'", "'keysFor'", 
			"'this'", "'.'", "'enum'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "ArrayMarks", "WS", "Identifier"
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
		public ConstructorParametersContext constructorParameters() {
			return getRuleContext(ConstructorParametersContext.class,0);
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
			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(32);
				((TypeNamePartContext)_localctx).stubbedMark = match(T__0);
				}
			}

			setState(35);
			match(Identifier);
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(36);
				match(T__1);
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(37);
					constructorParameters();
					}
				}

				setState(40);
				match(T__2);
				}
			}

			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(43);
				match(T__3);
				setState(44);
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
			setState(47);
			match(Identifier);
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(48);
				((PropertyNamePartContext)_localctx).optionalMark = match(T__4);
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
		public ConstructorParametersContext constructorParameters() {
			return getRuleContext(ConstructorParametersContext.class,0);
		}
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
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(51);
				((OnOneLineTypeNameContext)_localctx).stubbedMark = match(T__0);
				}
			}

			setState(54);
			match(Identifier);
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(55);
				match(T__1);
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(56);
					constructorParameters();
					}
				}

				setState(59);
				match(T__2);
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
		enterRule(_localctx, 6, RULE_onOneLineTypeDef);
		int _la;
		try {
			setState(75);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(62);
				match(T__5);
				setState(63);
				match(T__1);
				setState(64);
				typeExpression();
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(65);
					match(T__6);
					setState(66);
					typeExpression();
					}
					}
					setState(71);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(72);
				match(T__2);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(74);
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

	public static class ConstructorParametersContext extends ParserRuleContext {
		public List<ConstructorParameterContext> constructorParameter() {
			return getRuleContexts(ConstructorParameterContext.class);
		}
		public ConstructorParameterContext constructorParameter(int i) {
			return getRuleContext(ConstructorParameterContext.class,i);
		}
		public ConstructorParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterConstructorParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitConstructorParameters(this);
		}
	}

	public final ConstructorParametersContext constructorParameters() throws RecognitionException {
		ConstructorParametersContext _localctx = new ConstructorParametersContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_constructorParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			constructorParameter();
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(78);
				match(T__6);
				setState(79);
				constructorParameter();
				}
				}
				setState(84);
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

	public static class ConstructorParameterContext extends ParserRuleContext {
		public Token accessorMark;
		public TypeExpressionContext typeExpression() {
			return getRuleContext(TypeExpressionContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(DataTypeHierarchyParser.Identifier, 0); }
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public ConstructorParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterConstructorParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitConstructorParameter(this);
		}
	}

	public final ConstructorParameterContext constructorParameter() throws RecognitionException {
		ConstructorParameterContext _localctx = new ConstructorParameterContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_constructorParameter);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			((ConstructorParameterContext)_localctx).accessorMark = match(T__7);
			setState(86);
			match(T__8);
			setState(92);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(87);
					qualifiedName();
					setState(88);
					match(T__6);
					}
					} 
				}
				setState(94);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(95);
			typeExpression();
			setState(96);
			match(T__9);
			setState(97);
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
		enterRule(_localctx, 12, RULE_mapTypeExpression);
		int _la;
		try {
			setState(119);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(99);
				match(T__10);
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(100);
					match(T__11);
					setState(101);
					match(Identifier);
					setState(102);
					match(T__12);
					}
				}

				setState(105);
				diamondOneArg();
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ArrayMarks) {
					{
					setState(106);
					match(ArrayMarks);
					}
				}

				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 2);
				{
				setState(109);
				match(T__13);
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__11) {
					{
					setState(110);
					match(T__11);
					setState(111);
					match(Identifier);
					setState(112);
					match(T__12);
					}
				}

				setState(115);
				diamondTwoArg();
				setState(117);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ArrayMarks) {
					{
					setState(116);
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
		public PathWithKeyHoleContext pathWithKeyHole() {
			return getRuleContext(PathWithKeyHoleContext.class,0);
		}
		public ConstructorArgumentsContext constructorArguments() {
			return getRuleContext(ConstructorArgumentsContext.class,0);
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
		enterRule(_localctx, 14, RULE_typeExpression);
		int _la;
		try {
			setState(145);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__14:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				((TypeExpressionContext)_localctx).abstractFieldMark = match(T__14);
				setState(126);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__18:
					{
					setState(122);
					anonymousEnumField();
					}
					break;
				case T__2:
				case T__6:
				case T__9:
				case Identifier:
					{
					setState(124);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==Identifier) {
						{
						setState(123);
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
			case T__18:
				enterOuterAlt(_localctx, 2);
				{
				setState(128);
				anonymousEnumField();
				}
				break;
			case T__10:
			case T__13:
				enterOuterAlt(_localctx, 3);
				{
				setState(129);
				mapTypeExpression();
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 4);
				{
				setState(130);
				match(T__15);
				setState(131);
				match(T__1);
				setState(132);
				pathWithKeyHole();
				setState(133);
				match(T__2);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 5);
				{
				setState(135);
				qualifiedName();
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(136);
					match(T__1);
					setState(137);
					constructorArguments();
					setState(138);
					match(T__2);
					}
				}

				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ArrayMarks) {
					{
					setState(142);
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

	public static class ConstructorArgumentsContext extends ParserRuleContext {
		public List<PathWithKeyHoleContext> pathWithKeyHole() {
			return getRuleContexts(PathWithKeyHoleContext.class);
		}
		public PathWithKeyHoleContext pathWithKeyHole(int i) {
			return getRuleContext(PathWithKeyHoleContext.class,i);
		}
		public ConstructorArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorArguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterConstructorArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitConstructorArguments(this);
		}
	}

	public final ConstructorArgumentsContext constructorArguments() throws RecognitionException {
		ConstructorArgumentsContext _localctx = new ConstructorArgumentsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_constructorArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			pathWithKeyHole();
			setState(152);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(148);
				match(T__6);
				setState(149);
				pathWithKeyHole();
				}
				}
				setState(154);
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

	public static class PathWithKeyHoleContext extends ParserRuleContext {
		public Token thisMark;
		public TerminalNode Identifier() { return getToken(DataTypeHierarchyParser.Identifier, 0); }
		public List<IdOrKeyHoleContext> idOrKeyHole() {
			return getRuleContexts(IdOrKeyHoleContext.class);
		}
		public IdOrKeyHoleContext idOrKeyHole(int i) {
			return getRuleContext(IdOrKeyHoleContext.class,i);
		}
		public PathWithKeyHoleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathWithKeyHole; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterPathWithKeyHole(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitPathWithKeyHole(this);
		}
	}

	public final PathWithKeyHoleContext pathWithKeyHole() throws RecognitionException {
		PathWithKeyHoleContext _localctx = new PathWithKeyHoleContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_pathWithKeyHole);
		int _la;
		try {
			setState(168);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__16) {
					{
					setState(155);
					((PathWithKeyHoleContext)_localctx).thisMark = match(T__16);
					setState(156);
					match(T__17);
					}
				}

				setState(159);
				match(Identifier);
				setState(164);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(160);
					match(T__17);
					setState(161);
					idOrKeyHole();
					}
					}
					setState(166);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(167);
				((PathWithKeyHoleContext)_localctx).thisMark = match(T__16);
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

	public static class IdOrKeyHoleContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(DataTypeHierarchyParser.Identifier, 0); }
		public IdOrKeyHoleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idOrKeyHole; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).enterIdOrKeyHole(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DataTypeHierarchyListener ) ((DataTypeHierarchyListener)listener).exitIdOrKeyHole(this);
		}
	}

	public final IdOrKeyHoleContext idOrKeyHole() throws RecognitionException {
		IdOrKeyHoleContext _localctx = new IdOrKeyHoleContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_idOrKeyHole);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			_la = _input.LA(1);
			if ( !(_la==T__4 || _la==Identifier) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
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
		enterRule(_localctx, 22, RULE_anonymousEnumField);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			((AnonymousEnumFieldContext)_localctx).enumMark = match(T__18);
			setState(173);
			match(T__1);
			setState(174);
			identifierList();
			setState(175);
			match(T__2);
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
		enterRule(_localctx, 24, RULE_diamondOneArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			match(T__8);
			setState(178);
			typeExpression();
			setState(179);
			match(T__9);
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
		enterRule(_localctx, 26, RULE_diamondTwoArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			match(T__8);
			setState(182);
			qualifiedName();
			setState(183);
			match(T__6);
			setState(184);
			typeExpression();
			setState(185);
			match(T__9);
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
		enterRule(_localctx, 28, RULE_identifierList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(Identifier);
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(188);
				match(T__6);
				setState(189);
				match(Identifier);
				}
				}
				setState(194);
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
		enterRule(_localctx, 30, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			match(Identifier);
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(196);
				match(T__17);
				setState(197);
				match(Identifier);
				}
				}
				setState(202);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\30\u00ce\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\5\2"+
		"$\n\2\3\2\3\2\3\2\5\2)\n\2\3\2\5\2,\n\2\3\2\3\2\5\2\60\n\2\3\3\3\3\5\3"+
		"\64\n\3\3\4\5\4\67\n\4\3\4\3\4\3\4\5\4<\n\4\3\4\5\4?\n\4\3\5\3\5\3\5\3"+
		"\5\3\5\7\5F\n\5\f\5\16\5I\13\5\3\5\3\5\3\5\5\5N\n\5\3\6\3\6\3\6\7\6S\n"+
		"\6\f\6\16\6V\13\6\3\7\3\7\3\7\3\7\3\7\7\7]\n\7\f\7\16\7`\13\7\3\7\3\7"+
		"\3\7\3\7\3\b\3\b\3\b\3\b\5\bj\n\b\3\b\3\b\5\bn\n\b\3\b\3\b\3\b\3\b\5\b"+
		"t\n\b\3\b\3\b\5\bx\n\b\5\bz\n\b\3\t\3\t\3\t\5\t\177\n\t\5\t\u0081\n\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u008f\n\t\3\t\5\t"+
		"\u0092\n\t\5\t\u0094\n\t\3\n\3\n\3\n\7\n\u0099\n\n\f\n\16\n\u009c\13\n"+
		"\3\13\3\13\5\13\u00a0\n\13\3\13\3\13\3\13\7\13\u00a5\n\13\f\13\16\13\u00a8"+
		"\13\13\3\13\5\13\u00ab\n\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\7\20\u00c1\n\20\f\20"+
		"\16\20\u00c4\13\20\3\21\3\21\3\21\7\21\u00c9\n\21\f\21\16\21\u00cc\13"+
		"\21\3\21\2\2\22\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\3\4\2\7\7\30"+
		"\30\2\u00dc\2#\3\2\2\2\4\61\3\2\2\2\6\66\3\2\2\2\bM\3\2\2\2\nO\3\2\2\2"+
		"\fW\3\2\2\2\16y\3\2\2\2\20\u0093\3\2\2\2\22\u0095\3\2\2\2\24\u00aa\3\2"+
		"\2\2\26\u00ac\3\2\2\2\30\u00ae\3\2\2\2\32\u00b3\3\2\2\2\34\u00b7\3\2\2"+
		"\2\36\u00bd\3\2\2\2 \u00c5\3\2\2\2\"$\7\3\2\2#\"\3\2\2\2#$\3\2\2\2$%\3"+
		"\2\2\2%+\7\30\2\2&(\7\4\2\2\')\5\n\6\2(\'\3\2\2\2()\3\2\2\2)*\3\2\2\2"+
		"*,\7\5\2\2+&\3\2\2\2+,\3\2\2\2,/\3\2\2\2-.\7\6\2\2.\60\5 \21\2/-\3\2\2"+
		"\2/\60\3\2\2\2\60\3\3\2\2\2\61\63\7\30\2\2\62\64\7\7\2\2\63\62\3\2\2\2"+
		"\63\64\3\2\2\2\64\5\3\2\2\2\65\67\7\3\2\2\66\65\3\2\2\2\66\67\3\2\2\2"+
		"\678\3\2\2\28>\7\30\2\29;\7\4\2\2:<\5\n\6\2;:\3\2\2\2;<\3\2\2\2<=\3\2"+
		"\2\2=?\7\5\2\2>9\3\2\2\2>?\3\2\2\2?\7\3\2\2\2@A\7\b\2\2AB\7\4\2\2BG\5"+
		"\20\t\2CD\7\t\2\2DF\5\20\t\2EC\3\2\2\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2\2H"+
		"J\3\2\2\2IG\3\2\2\2JK\7\5\2\2KN\3\2\2\2LN\5\36\20\2M@\3\2\2\2ML\3\2\2"+
		"\2N\t\3\2\2\2OT\5\f\7\2PQ\7\t\2\2QS\5\f\7\2RP\3\2\2\2SV\3\2\2\2TR\3\2"+
		"\2\2TU\3\2\2\2U\13\3\2\2\2VT\3\2\2\2WX\7\n\2\2X^\7\13\2\2YZ\5 \21\2Z["+
		"\7\t\2\2[]\3\2\2\2\\Y\3\2\2\2]`\3\2\2\2^\\\3\2\2\2^_\3\2\2\2_a\3\2\2\2"+
		"`^\3\2\2\2ab\5\20\t\2bc\7\f\2\2cd\7\30\2\2d\r\3\2\2\2ei\7\r\2\2fg\7\16"+
		"\2\2gh\7\30\2\2hj\7\17\2\2if\3\2\2\2ij\3\2\2\2jk\3\2\2\2km\5\32\16\2l"+
		"n\7\26\2\2ml\3\2\2\2mn\3\2\2\2nz\3\2\2\2os\7\20\2\2pq\7\16\2\2qr\7\30"+
		"\2\2rt\7\17\2\2sp\3\2\2\2st\3\2\2\2tu\3\2\2\2uw\5\34\17\2vx\7\26\2\2w"+
		"v\3\2\2\2wx\3\2\2\2xz\3\2\2\2ye\3\2\2\2yo\3\2\2\2z\17\3\2\2\2{\u0080\7"+
		"\21\2\2|\u0081\5\30\r\2}\177\5 \21\2~}\3\2\2\2~\177\3\2\2\2\177\u0081"+
		"\3\2\2\2\u0080|\3\2\2\2\u0080~\3\2\2\2\u0081\u0094\3\2\2\2\u0082\u0094"+
		"\5\30\r\2\u0083\u0094\5\16\b\2\u0084\u0085\7\22\2\2\u0085\u0086\7\4\2"+
		"\2\u0086\u0087\5\24\13\2\u0087\u0088\7\5\2\2\u0088\u0094\3\2\2\2\u0089"+
		"\u008e\5 \21\2\u008a\u008b\7\4\2\2\u008b\u008c\5\22\n\2\u008c\u008d\7"+
		"\5\2\2\u008d\u008f\3\2\2\2\u008e\u008a\3\2\2\2\u008e\u008f\3\2\2\2\u008f"+
		"\u0091\3\2\2\2\u0090\u0092\7\26\2\2\u0091\u0090\3\2\2\2\u0091\u0092\3"+
		"\2\2\2\u0092\u0094\3\2\2\2\u0093{\3\2\2\2\u0093\u0082\3\2\2\2\u0093\u0083"+
		"\3\2\2\2\u0093\u0084\3\2\2\2\u0093\u0089\3\2\2\2\u0094\21\3\2\2\2\u0095"+
		"\u009a\5\24\13\2\u0096\u0097\7\t\2\2\u0097\u0099\5\24\13\2\u0098\u0096"+
		"\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b"+
		"\23\3\2\2\2\u009c\u009a\3\2\2\2\u009d\u009e\7\23\2\2\u009e\u00a0\7\24"+
		"\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1"+
		"\u00a6\7\30\2\2\u00a2\u00a3\7\24\2\2\u00a3\u00a5\5\26\f\2\u00a4\u00a2"+
		"\3\2\2\2\u00a5\u00a8\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"+
		"\u00ab\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\u00ab\7\23\2\2\u00aa\u009f\3"+
		"\2\2\2\u00aa\u00a9\3\2\2\2\u00ab\25\3\2\2\2\u00ac\u00ad\t\2\2\2\u00ad"+
		"\27\3\2\2\2\u00ae\u00af\7\25\2\2\u00af\u00b0\7\4\2\2\u00b0\u00b1\5\36"+
		"\20\2\u00b1\u00b2\7\5\2\2\u00b2\31\3\2\2\2\u00b3\u00b4\7\13\2\2\u00b4"+
		"\u00b5\5\20\t\2\u00b5\u00b6\7\f\2\2\u00b6\33\3\2\2\2\u00b7\u00b8\7\13"+
		"\2\2\u00b8\u00b9\5 \21\2\u00b9\u00ba\7\t\2\2\u00ba\u00bb\5\20\t\2\u00bb"+
		"\u00bc\7\f\2\2\u00bc\35\3\2\2\2\u00bd\u00c2\7\30\2\2\u00be\u00bf\7\t\2"+
		"\2\u00bf\u00c1\7\30\2\2\u00c0\u00be\3\2\2\2\u00c1\u00c4\3\2\2\2\u00c2"+
		"\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\37\3\2\2\2\u00c4\u00c2\3\2\2"+
		"\2\u00c5\u00ca\7\30\2\2\u00c6\u00c7\7\24\2\2\u00c7\u00c9\7\30\2\2\u00c8"+
		"\u00c6\3\2\2\2\u00c9\u00cc\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00cb\3\2"+
		"\2\2\u00cb!\3\2\2\2\u00cc\u00ca\3\2\2\2\36#(+/\63\66;>GMT^imswy~\u0080"+
		"\u008e\u0091\u0093\u009a\u009f\u00a6\u00aa\u00c2\u00ca";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}