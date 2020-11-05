// Generated from C:\Users\marti\workingLowLevel\codeGen\src\main\java\org\gmart\codeGen\javaGen\fromYaml\javadataclass\typedefsExtraction\typeExpression\TypeExpressionGrammar.g4 by ANTLR 4.8
package org.gmart.codeGen.javaGen.fromYaml.modelExtraction.parserGeneration.parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TypeExpressionGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, ArrayMarks=14, WS=15, Identifier=16;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "ArrayMarks", "WS", "Identifier", 
			"LetterOrDigit", "Letter"
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


	public TypeExpressionGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "TypeExpressionGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\22r\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6"+
		"\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\6\17"+
		"W\n\17\r\17\16\17X\3\20\6\20\\\n\20\r\20\16\20]\3\20\3\20\3\21\3\21\7"+
		"\21d\n\21\f\21\16\21g\13\21\3\22\3\22\5\22k\n\22\3\23\3\23\3\23\3\23\5"+
		"\23q\n\23\2\2\24\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\2%\2\3\2\b\5\2\13\f\16\17\"\"\3\2\62;\6\2&"+
		"&C\\aac|\4\2\2\u0081\ud802\udc01\3\2\ud802\udc01\3\2\udc02\ue001\2u\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\3\'\3\2"+
		"\2\2\5*\3\2\2\2\7,\3\2\2\2\t\62\3\2\2\2\13\64\3\2\2\2\r\66\3\2\2\2\17"+
		"8\3\2\2\2\21=\3\2\2\2\23A\3\2\2\2\25J\3\2\2\2\27O\3\2\2\2\31Q\3\2\2\2"+
		"\33S\3\2\2\2\35V\3\2\2\2\37[\3\2\2\2!a\3\2\2\2#j\3\2\2\2%p\3\2\2\2\'("+
		"\7k\2\2()\7u\2\2)\4\3\2\2\2*+\7A\2\2+\6\3\2\2\2,-\7q\2\2-.\7p\2\2./\7"+
		"g\2\2/\60\7Q\2\2\60\61\7h\2\2\61\b\3\2\2\2\62\63\7*\2\2\63\n\3\2\2\2\64"+
		"\65\7.\2\2\65\f\3\2\2\2\66\67\7+\2\2\67\16\3\2\2\289\7F\2\29:\7k\2\2:"+
		";\7e\2\2;<\7v\2\2<\20\3\2\2\2=>\7O\2\2>?\7c\2\2?@\7r\2\2@\22\3\2\2\2A"+
		"B\7c\2\2BC\7d\2\2CD\7u\2\2DE\7v\2\2EF\7t\2\2FG\7c\2\2GH\7e\2\2HI\7v\2"+
		"\2I\24\3\2\2\2JK\7g\2\2KL\7p\2\2LM\7w\2\2MN\7o\2\2N\26\3\2\2\2OP\7>\2"+
		"\2P\30\3\2\2\2QR\7@\2\2R\32\3\2\2\2ST\7\60\2\2T\34\3\2\2\2UW\7,\2\2VU"+
		"\3\2\2\2WX\3\2\2\2XV\3\2\2\2XY\3\2\2\2Y\36\3\2\2\2Z\\\t\2\2\2[Z\3\2\2"+
		"\2\\]\3\2\2\2][\3\2\2\2]^\3\2\2\2^_\3\2\2\2_`\b\20\2\2` \3\2\2\2ae\5%"+
		"\23\2bd\5#\22\2cb\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2f\"\3\2\2\2ge\3"+
		"\2\2\2hk\5%\23\2ik\t\3\2\2jh\3\2\2\2ji\3\2\2\2k$\3\2\2\2lq\t\4\2\2mq\n"+
		"\5\2\2no\t\6\2\2oq\t\7\2\2pl\3\2\2\2pm\3\2\2\2pn\3\2\2\2q&\3\2\2\2\b\2"+
		"X]ejp\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}