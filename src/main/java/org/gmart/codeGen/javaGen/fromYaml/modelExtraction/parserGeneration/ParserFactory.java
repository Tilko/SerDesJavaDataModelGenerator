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
package org.gmart.codeGen.javaGen.fromYaml.modelExtraction.parserGeneration;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.gmart.codeGen.javaGen.fromYaml.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyLexer;
import org.gmart.codeGen.javaGen.fromYaml.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser;

public class ParserFactory {
	public static DataTypeHierarchyParser parse(String str){
		DataTypeHierarchyLexer lexer = new DataTypeHierarchyLexer(CharStreams.fromString(str));
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		return new DataTypeHierarchyParser(tokenStream);
	}
	
}
