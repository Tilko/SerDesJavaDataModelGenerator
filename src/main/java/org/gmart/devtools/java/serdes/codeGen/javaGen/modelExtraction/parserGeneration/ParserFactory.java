/*******************************************************************************
 * Copyright 2020 Grégoire Martinetti
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyLexer;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser;

public class ParserFactory {
	public static DataTypeHierarchyParser parse(String str){
		return parse(str, 10);
	}
	public static DataTypeHierarchyParser parse(String str, int lineOffset){
		DataTypeHierarchyLexer lexer = new DataTypeHierarchyLexer(CharStreams.fromString(str));
		lexer.setTokenFactory(new CommonTokenFactory2(lineOffset));
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		return new DataTypeHierarchyParser(tokenStream);
	}
	public static class CommonTokenFactory2 extends CommonTokenFactory {
		private int lineOffset;
		public CommonTokenFactory2(int lineOffset){
			this.lineOffset = lineOffset;
		}
		@Override
		public CommonToken create(int type, String text) {
			return super.create(type, text);
		}
		@Override
		public CommonToken create(Pair<TokenSource, CharStream> source, int type, String text, int channel, int start, int stop, int line, int charPositionInLine) {
			return super.create(source, type, text, channel, start, stop, lineOffset + line, charPositionInLine);
		}
	}
}
//class CommonToken2 extends CommonToken {
//
//	public CommonToken2(int type, String text) {
//		super(type, text);
//	}
//
//	public CommonToken2(int type) {
//		super(type);
//	}
//
//	public CommonToken2(Pair<TokenSource, CharStream> source, int type, int channel, int start, int stop) {
//		super(source, type, channel, start, stop);
//	}
//
//	public CommonToken2(Token oldToken) {
//		super(oldToken);
//	}
//	public int getLine() {
//		return super.getLine() + lineOffset;
//	}
//}
