// Generated from C:\Users\marti\workingLowLevel\codeGen\src\main\java\org\gmart\codeGen\javaGen\modelExtraction\parserGeneration\DataTypeHierarchy.g4 by ANTLR 4.8
package org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser;
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
public class DataTypeHierarchyLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, ArrayMarks=20, WS=21, Identifier=22;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "ArrayMarks", "WS", "Identifier", "LetterOrDigit", 
			"Letter"
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


	public DataTypeHierarchyLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DataTypeHierarchy.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\30\u00a0\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3"+
		"\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3"+
		"\24\3\24\3\24\3\25\6\25\u0085\n\25\r\25\16\25\u0086\3\26\6\26\u008a\n"+
		"\26\r\26\16\26\u008b\3\26\3\26\3\27\3\27\7\27\u0092\n\27\f\27\16\27\u0095"+
		"\13\27\3\30\3\30\5\30\u0099\n\30\3\31\3\31\3\31\3\31\5\31\u009f\n\31\2"+
		"\2\32\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\2\61\2\3\2\b\5\2\13\f\16\17\""+
		"\"\3\2\62;\6\2&&C\\aac|\4\2\2\u0081\ud802\udc01\3\2\ud802\udc01\3\2\udc02"+
		"\ue001\2\u00a3\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3"+
		"\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2"+
		"\2\2\3\63\3\2\2\2\5;\3\2\2\2\7=\3\2\2\2\t?\3\2\2\2\13B\3\2\2\2\rD\3\2"+
		"\2\2\17J\3\2\2\2\21L\3\2\2\2\23U\3\2\2\2\25W\3\2\2\2\27Y\3\2\2\2\31^\3"+
		"\2\2\2\33`\3\2\2\2\35b\3\2\2\2\37f\3\2\2\2!o\3\2\2\2#w\3\2\2\2%|\3\2\2"+
		"\2\'~\3\2\2\2)\u0084\3\2\2\2+\u0089\3\2\2\2-\u008f\3\2\2\2/\u0098\3\2"+
		"\2\2\61\u009e\3\2\2\2\63\64\7u\2\2\64\65\7v\2\2\65\66\7w\2\2\66\67\7d"+
		"\2\2\678\7d\2\289\7g\2\29:\7f\2\2:\4\3\2\2\2;<\7*\2\2<\6\3\2\2\2=>\7+"+
		"\2\2>\b\3\2\2\2?@\7k\2\2@A\7u\2\2A\n\3\2\2\2BC\7A\2\2C\f\3\2\2\2DE\7q"+
		"\2\2EF\7p\2\2FG\7g\2\2GH\7Q\2\2HI\7h\2\2I\16\3\2\2\2JK\7.\2\2K\20\3\2"+
		"\2\2LM\7C\2\2MN\7e\2\2NO\7e\2\2OP\7g\2\2PQ\7u\2\2QR\7u\2\2RS\7q\2\2ST"+
		"\7t\2\2T\22\3\2\2\2UV\7>\2\2V\24\3\2\2\2WX\7@\2\2X\26\3\2\2\2YZ\7F\2\2"+
		"Z[\7k\2\2[\\\7e\2\2\\]\7v\2\2]\30\3\2\2\2^_\7]\2\2_\32\3\2\2\2`a\7_\2"+
		"\2a\34\3\2\2\2bc\7O\2\2cd\7c\2\2de\7r\2\2e\36\3\2\2\2fg\7c\2\2gh\7d\2"+
		"\2hi\7u\2\2ij\7v\2\2jk\7t\2\2kl\7c\2\2lm\7e\2\2mn\7v\2\2n \3\2\2\2op\7"+
		"m\2\2pq\7g\2\2qr\7{\2\2rs\7u\2\2st\7H\2\2tu\7q\2\2uv\7t\2\2v\"\3\2\2\2"+
		"wx\7v\2\2xy\7j\2\2yz\7k\2\2z{\7u\2\2{$\3\2\2\2|}\7\60\2\2}&\3\2\2\2~\177"+
		"\7g\2\2\177\u0080\7p\2\2\u0080\u0081\7w\2\2\u0081\u0082\7o\2\2\u0082("+
		"\3\2\2\2\u0083\u0085\7,\2\2\u0084\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086"+
		"\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087*\3\2\2\2\u0088\u008a\t\2\2\2"+
		"\u0089\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c"+
		"\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e\b\26\2\2\u008e,\3\2\2\2\u008f"+
		"\u0093\5\61\31\2\u0090\u0092\5/\30\2\u0091\u0090\3\2\2\2\u0092\u0095\3"+
		"\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094.\3\2\2\2\u0095\u0093"+
		"\3\2\2\2\u0096\u0099\5\61\31\2\u0097\u0099\t\3\2\2\u0098\u0096\3\2\2\2"+
		"\u0098\u0097\3\2\2\2\u0099\60\3\2\2\2\u009a\u009f\t\4\2\2\u009b\u009f"+
		"\n\5\2\2\u009c\u009d\t\6\2\2\u009d\u009f\t\7\2\2\u009e\u009a\3\2\2\2\u009e"+
		"\u009b\3\2\2\2\u009e\u009c\3\2\2\2\u009f\62\3\2\2\2\b\2\u0086\u008b\u0093"+
		"\u0098\u009e\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}