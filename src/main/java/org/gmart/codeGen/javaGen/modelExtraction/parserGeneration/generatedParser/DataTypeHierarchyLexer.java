// Generated from C:\Users\marti\workingLowLevel\codeGen\src\main\java\org\gmart\codeGen\javaGen\modelExtraction\parserGeneration\DataTypeHierarchy.g4 by ANTLR 4.8
package org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser;
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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, ArrayMarks=17, 
		WS=18, Identifier=19;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "ArrayMarks", 
			"WS", "Identifier", "LetterOrDigit", "Letter"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\25\u0084\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3"+
		"\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\20\3\20"+
		"\3\21\3\21\3\22\6\22i\n\22\r\22\16\22j\3\23\6\23n\n\23\r\23\16\23o\3\23"+
		"\3\23\3\24\3\24\7\24v\n\24\f\24\16\24y\13\24\3\25\3\25\5\25}\n\25\3\26"+
		"\3\26\3\26\3\26\5\26\u0083\n\26\2\2\27\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21"+
		"\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\2+\2\3\2"+
		"\b\5\2\13\f\16\17\"\"\3\2\62;\6\2&&C\\aac|\4\2\2\u0081\ud802\udc01\3\2"+
		"\ud802\udc01\3\2\udc02\ue001\2\u0087\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\3-\3\2"+
		"\2\2\5\65\3\2\2\2\78\3\2\2\2\t:\3\2\2\2\13@\3\2\2\2\rB\3\2\2\2\17D\3\2"+
		"\2\2\21F\3\2\2\2\23K\3\2\2\2\25M\3\2\2\2\27O\3\2\2\2\31S\3\2\2\2\33\\"+
		"\3\2\2\2\35a\3\2\2\2\37c\3\2\2\2!e\3\2\2\2#h\3\2\2\2%m\3\2\2\2\'s\3\2"+
		"\2\2)|\3\2\2\2+\u0082\3\2\2\2-.\7u\2\2./\7v\2\2/\60\7w\2\2\60\61\7d\2"+
		"\2\61\62\7d\2\2\62\63\7g\2\2\63\64\7f\2\2\64\4\3\2\2\2\65\66\7k\2\2\66"+
		"\67\7u\2\2\67\6\3\2\2\289\7A\2\29\b\3\2\2\2:;\7q\2\2;<\7p\2\2<=\7g\2\2"+
		"=>\7Q\2\2>?\7h\2\2?\n\3\2\2\2@A\7*\2\2A\f\3\2\2\2BC\7.\2\2C\16\3\2\2\2"+
		"DE\7+\2\2E\20\3\2\2\2FG\7F\2\2GH\7k\2\2HI\7e\2\2IJ\7v\2\2J\22\3\2\2\2"+
		"KL\7]\2\2L\24\3\2\2\2MN\7_\2\2N\26\3\2\2\2OP\7O\2\2PQ\7c\2\2QR\7r\2\2"+
		"R\30\3\2\2\2ST\7c\2\2TU\7d\2\2UV\7u\2\2VW\7v\2\2WX\7t\2\2XY\7c\2\2YZ\7"+
		"e\2\2Z[\7v\2\2[\32\3\2\2\2\\]\7g\2\2]^\7p\2\2^_\7w\2\2_`\7o\2\2`\34\3"+
		"\2\2\2ab\7>\2\2b\36\3\2\2\2cd\7@\2\2d \3\2\2\2ef\7\60\2\2f\"\3\2\2\2g"+
		"i\7,\2\2hg\3\2\2\2ij\3\2\2\2jh\3\2\2\2jk\3\2\2\2k$\3\2\2\2ln\t\2\2\2m"+
		"l\3\2\2\2no\3\2\2\2om\3\2\2\2op\3\2\2\2pq\3\2\2\2qr\b\23\2\2r&\3\2\2\2"+
		"sw\5+\26\2tv\5)\25\2ut\3\2\2\2vy\3\2\2\2wu\3\2\2\2wx\3\2\2\2x(\3\2\2\2"+
		"yw\3\2\2\2z}\5+\26\2{}\t\3\2\2|z\3\2\2\2|{\3\2\2\2}*\3\2\2\2~\u0083\t"+
		"\4\2\2\177\u0083\n\5\2\2\u0080\u0081\t\6\2\2\u0081\u0083\t\7\2\2\u0082"+
		"~\3\2\2\2\u0082\177\3\2\2\2\u0082\u0080\3\2\2\2\u0083,\3\2\2\2\b\2jow"+
		"|\u0082\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}